import { Injectable, BadRequestException, NotFoundException } from '@nestjs/common';
import { DonationStatus } from '@prisma/client';
import { PrismaService } from 'prisma/prisma.service';
import { DonationRepository } from '../repository/donation.repository';
import { CreateDonationDto } from '../dto/donations/create-donation.dto';
import { DonationResponseDto } from '../dto/donations/donation-response.dto';

@Injectable()
export class DonationService {
  constructor(
    private readonly prisma: PrismaService,
    private readonly donationRepository: DonationRepository,
  ) {}

  // Create donation request 
  public async createDonation(dto: CreateDonationDto): Promise<DonationResponseDto> {
  // Validate if student exists
  const student = await this.prisma.student.findUnique({
    where: { id: dto.studentId },
  });

  if (!student) {
    throw new NotFoundException(`Student with ID ${dto.studentId} not found`);
  }

  // Validate all products exist and have sufficient stock
  for (const item of dto.items) {
    const batches = await this.donationRepository.getAvailableStockFIFO(item.productId);
    const totalAvailable = batches.reduce((sum, batch) => sum + batch.quantity, 0);

    if (totalAvailable < item.quantity) {
      throw new BadRequestException(
        `Insufficient stock for product ID: ${item.productId}. ` +
        `Requested: ${item.quantity}, Available: ${totalAvailable}`
      );
    }
  }

  // Create donation request 
  const donation = await this.donationRepository.createDonationRequest({
    student: { connect: { id: dto.studentId } },
    status: DonationStatus.PENDING,
    notes: dto.notes,
    deliveryProducts: {
      create: dto.items.map(item => ({
        product: { connect: { id: item.productId } },
        deliveredQuantity: item.quantity,
      })),
    },
  });

  return new DonationResponseDto(donation);
  }

  // Confirm delivery and deduct stock 
  public async confirmDelivery(donationId: string): Promise<DonationResponseDto> {
    const donation = await this.donationRepository.findDonationById(donationId);

    if (!donation) {
      throw new NotFoundException(`Donation with ID ${donationId} not found`);
    }

    if (donation.status === DonationStatus.DELIVERED) {
      throw new BadRequestException('This donation has already been delivered');
    }

    if (donation.status === DonationStatus.CANCELLED) {
      throw new BadRequestException('Cannot deliver a cancelled donation');
    }

    // Process stock deduction with transaction
    return this.prisma.$transaction(async (tx) => {
      for (const deliveryProduct of donation.deliveryProducts) {
        let remainingToDeduct = deliveryProduct.deliveredQuantity;

        // Get available batches 
        const batches = await this.donationRepository.getAvailableStockFIFO(
          deliveryProduct.productId
        );

        for (const batch of batches) {
          if (remainingToDeduct <= 0) break;

          const deduction = Math.min(batch.quantity, remainingToDeduct);

          // Update stock in this batch
          await this.donationRepository.updateStockQuantity(
            batch.id,
            batch.quantity - deduction,
            tx
          );

          remainingToDeduct -= deduction;
        }

        if (remainingToDeduct > 0) {
          throw new BadRequestException(
            `Insufficient stock for product ID: ${deliveryProduct.productId}`
          );
        }
      }

      // Update donation status to DELIVERED
      const updatedDonation = await tx.donationRequest.update({
        where: { id: donationId },
        data: {
          status: DonationStatus.DELIVERED,
          deliveryDate: new Date(),
        },
        include: {
          student: true,
          deliveryProducts: {
            include: {
              product: true,
            },
          },
        },
      });

      return new DonationResponseDto(updatedDonation);
    });
  }

  // Cancel donation request
  public async cancelDonation(donationId: string): Promise<DonationResponseDto> {
    const donation = await this.donationRepository.findDonationById(donationId);

    if (!donation) {
      throw new NotFoundException(`Donation with ID ${donationId} not found`);
    }

    if (donation.status === DonationStatus.DELIVERED) {
      throw new BadRequestException('Cannot cancel a delivered donation');
    }

    const updatedDonation = await this.donationRepository.updateDonationStatus(
      donationId,
      DonationStatus.CANCELLED
    );

    return new DonationResponseDto(updatedDonation);
  }

  // Get donation history for a specific student
  public async getStudentHistory(studentId: string): Promise<DonationResponseDto[]> {
    const donations = await this.donationRepository.findDonationsByStudent(studentId);
    return donations.map(d => new DonationResponseDto(d));
  }

  // Get donation by ID
  public async getDonationById(donationId: string): Promise<DonationResponseDto> {
    const donation = await this.donationRepository.findDonationById(donationId);

    if (!donation) {
      throw new NotFoundException(`Donation with ID ${donationId} not found`);
    }

    return new DonationResponseDto(donation);
  }

  // List all donations 
  public async getAllDonations(skip?: number, take?: number): Promise<DonationResponseDto[]> {
    const donations = await this.donationRepository.findAllDonations(skip, take);
    return donations.map(d => new DonationResponseDto(d));
  }

  // Get donations by status
  public async getDonationsByStatus(status: DonationStatus): Promise<DonationResponseDto[]> {
    const donations = await this.donationRepository.findDonationsByStatus(status);
    return donations.map(d => new DonationResponseDto(d));
  }
}
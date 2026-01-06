import { Injectable, NotFoundException, BadRequestException } from '@nestjs/common';
import { PrismaService } from 'prisma/prisma.service';
import { DonationRepository } from '../repository/donation.repository';
import { CreateDonationDto } from '../dto/donations/create-donation.dto';
import { DonationResponseDto } from '../dto/donations/donation-response.dto';
import { CreateDonorDto } from '../dto/donations/create-donor.dto';
import { DonorResponseDto } from '../dto/donations/donor-response.dto';

@Injectable()
export class DonationService {
  constructor(
    private readonly prisma: PrismaService,
    private readonly donationRepository: DonationRepository,
  ) {}

  async registerDonation(dto: CreateDonationDto, userId?: string): Promise<DonationResponseDto> {
    // Verify if donor exists
    const donor = await this.prisma.donor.findUnique({
      where: { id: dto.donorId },
    });
    if (!donor) {
      throw new NotFoundException(`Donor with ID ${dto.donorId} not found`);
    }

    // Verify if campaign exists (if provided)
    if (dto.campaignId) {
      const campaign = await this.prisma.campaign.findUnique({
        where: { id: dto.campaignId },
      });
      if (!campaign) {
        throw new NotFoundException(`Campaign with ID ${dto.campaignId} not found`);
      }
    }

    // Verify if all products exist
    for (const item of dto.items) {
      const product = await this.prisma.product.findUnique({
        where: { id: item.productId },
      });
      if (!product) {
        throw new NotFoundException(`Product with ID ${item.productId} not found`);
      }
    }

    // Create donation and update stock in a transaction
    return this.prisma.$transaction(async (tx) => {
      // Create donation
      const donation = await tx.donation.create({
        data: {
          donorId: dto.donorId,
          campaignId: dto.campaignId,
          items: {
            create: dto.items.map(item => ({
              productId: item.productId,
              quantity: item.quantity,
            })),
          },
        },
        include: {
          donor: true,
          campaign: true,
          items: { include: { product: true } }
        }
      });

      // Add each item to stock has a new batch
      for (const item of dto.items) {
        await tx.stock.create({
          data: {
            productId: item.productId,
            quantity: item.quantity,
            expiryDate: item.expiryDate ? new Date(item.expiryDate) : null,
            location: item.location || 'Armazém Geral',
          }
        });
      }

      return new DonationResponseDto(donation);
    });
  }

   // List all received donations
  async getAllDonations(skip?: number, take?: number): Promise<DonationResponseDto[]> {
    const donations = await this.donationRepository.findAllDonations(skip, take);
    return donations.map(d => new DonationResponseDto(d));
  }

   // Get donation by id
  async getDonationById(id: number): Promise<DonationResponseDto> {
    const donation = await this.donationRepository.findDonationById(id);
    if (!donation) {
      throw new NotFoundException(`Donation with ID ${id} not found`);
    }
    return new DonationResponseDto(donation);
  }

   // List donations from a specific donor
  async getDonationsByDonor(donorId: number): Promise<DonationResponseDto[]> {
    const donations = await this.donationRepository.findDonationsByDonor(donorId);
    return donations.map(d => new DonationResponseDto(d));
  }

  //List donations from a specific campaign
  async getDonationsByCampaign(campaignId: number): Promise<DonationResponseDto[]> {
    const donations = await this.donationRepository.findDonationsByCampaign(campaignId);
    return donations.map(d => new DonationResponseDto(d));
  }

  // DONOR MANAGEMENT 

  // Create new donor
  async createDonor(dto: CreateDonorDto): Promise<DonorResponseDto> {
    const donor = await this.donationRepository.createDonor({
      name: dto.name,
      nif: dto.nif,
      type: dto.type,
      contact: dto.contact,
    });
    return new DonorResponseDto(donor);
  }

  // List all donors
  async getAllDonors(): Promise<DonorResponseDto[]> {
    const donors = await this.donationRepository.findAllDonors();
    return donors.map(d => new DonorResponseDto(d, d.donations.length));
  }

  // Get donor by id
  async getDonorById(id: number): Promise<DonorResponseDto> {
    const donor = await this.donationRepository.findDonorById(id);
    if (!donor) {
      throw new NotFoundException(`Donor with ID ${id} not found`);
    }
    return new DonorResponseDto(donor, donor.donations.length);
  }

  // Update donor info
  async updateDonor(id: number, dto: Partial<CreateDonorDto>): Promise<DonorResponseDto> {
    const donor = await this.donationRepository.findDonorById(id);
    if (!donor) {
      throw new NotFoundException(`Donor with ID ${id} not found`);
    }

    const updated = await this.donationRepository.updateDonor(id, dto);
    return new DonorResponseDto(updated);
  }

  // Remove donor if they have no donations
  async deleteDonor(id: number): Promise<void> {
    const donor = await this.donationRepository.findDonorById(id);
    if (!donor) {
      throw new NotFoundException(`Donor with ID ${id} not found`);
    }

    if (donor.donations.length > 0) {
      throw new BadRequestException(
        'Cannot remove a donor who has made donations'
      );
    }

    await this.donationRepository.deleteDonor(id);
  }
}
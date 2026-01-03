import { Injectable } from '@nestjs/common';
import { Prisma, DonationStatus } from '@prisma/client';
import { PrismaService } from 'prisma/prisma.service';

@Injectable()
export class DonationRepository {
  constructor(private readonly prisma: PrismaService) {}

  // Create donation request with delivery products
  public async createDonationRequest(
    data: Prisma.DonationRequestCreateInput,
  ) {
    return this.prisma.donationRequest.create({
      data,
      include: {
        student: true,
        donation: true,
        schedule: true,
        user: true,
        deliveryProducts: {
          include: {
            product: true,
          },
        },
      },
    });
  }

  // Find donation request by ID (for delivery confirmation)
  public async findDonationById(id: string) {
    return this.prisma.donationRequest.findUnique({
      where: { id },
      include: {
        student: true,
        donation: true,
        schedule: true,
        user: true,
        deliveryProducts: {
          include: {
            product: true,
          },
        },
      },
    });
  }

  // List all donation requests 
  public async findAllDonations(skip = 0, take = 50) {
    return this.prisma.donationRequest.findMany({
      skip,
      take,
      include: {
        student: true,
        donation: true,
        schedule: true,
        user: true,
        deliveryProducts: {
          include: {
            product: true,
          },
        },
      },
      orderBy: { deliveryDate: 'desc' },
    });
  }

  // Find donations by student ID
  public async findDonationsByStudent(studentId: string) {
    return this.prisma.donationRequest.findMany({
      where: { studentId },
      include: {
        donation: true,
        schedule: true,
        deliveryProducts: {
          include: {
            product: true,
          },
        },
      },
      orderBy: { deliveryDate: 'desc' },
    });
  }

  // Update donation request 
  public async updateDonationRequest(
    id: string,
    data: Prisma.DonationRequestUpdateInput,
  ) {
    return this.prisma.donationRequest.update({
      where: { id },
      data,
      include: {
        student: true,
        donation: true,
        schedule: true,
        deliveryProducts: {
          include: {
            product: true,
          },
        },
      },
    });
  }

  // Update donation status 
  public async updateDonationStatus(id: string, status: DonationStatus) {
    return this.prisma.donationRequest.update({
      where: { id },
      data: { status },
      include: {
        student: true,
      },
    });
  }

  // Find donations by status
  public async findDonationsByStatus(status: DonationStatus) {
    return this.prisma.donationRequest.findMany({
      where: { status },
      include: {
        student: true,
        schedule: true,
        deliveryProducts: {
          include: {
            product: true,
          },
        },
      },
      orderBy: { deliveryDate: 'asc' },
    });
  }

  // Get available stock batches for a product ordered by expiry date 
  public async getAvailableStockFIFO(productId: string) {
    return this.prisma.stock.findMany({
      where: {
        productId,
        quantity: { gt: 0 },
      },
      include: {
        product: true,
        user: true,
      },
      orderBy: { expiryDate: 'asc' },
    });
  }

  // Update stock quantity within a transaction
  public async updateStockQuantity(
    id: string,
    newQuantity: number,
    tx: Prisma.TransactionClient,
  ) {
    return tx.stock.update({
      where: { id },
      data: { quantity: newQuantity },
    });
  }

  // Create delivery product record within transaction
  public async createDeliveryProduct(
    data: Prisma.DeliveryProductCreateInput,
    tx: Prisma.TransactionClient,
  ) {
    return tx.deliveryProduct.create({
      data,
      include: {
        product: true,
        donationRequest: true,
      },
    });
  }

  // Delete donation request
  public async deleteDonationRequest(id: string) {
    return this.prisma.donationRequest.delete({
      where: { id },
    });
  }

  // Get donation statistics
  public async getDonationStats(startDate?: Date, endDate?: Date) {
    return this.prisma.donationRequest.groupBy({
      by: ['status'],
      _count: {
        id: true,
      },
      where: {
        deliveryDate: {
          gte: startDate,
          lte: endDate,
        },
      },
    });
  }

  // Find donations scheduled for a specific date 
  public async findDonationsByScheduleDate(date: Date) {
    return this.prisma.donationRequest.findMany({
      where: {
        schedule: {
          scheduledDate: {
            gte: new Date(date.setHours(0, 0, 0, 0)),
            lt: new Date(date.setHours(23, 59, 59, 999)),
          },
        },
      },
      include: {
        student: true,
        schedule: true,
      },
    });
  }
}
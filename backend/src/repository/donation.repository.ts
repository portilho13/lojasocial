import { Injectable } from '@nestjs/common';
import { Prisma } from '@prisma/client';
import { PrismaService } from 'prisma/prisma.service';

@Injectable()
export class DonationRepository {
  constructor(private readonly prisma: PrismaService) {}

  // Create donation
  async createDonation(data: Prisma.DonationCreateInput) {
    return this.prisma.donation.create({
      data,
      include: {
        donor: true,
        campaign: true,
        items: {
          include: { product: true }
        },
      },
    });
  }

  // Add batch to stock
  async addStockBatch(data: Prisma.StockCreateInput, tx: Prisma.TransactionClient) {
    return tx.stock.create({ data });
  }

  // List all donations
  async findAllDonations(skip = 0, take = 50) {
    return this.prisma.donation.findMany({
      skip,
      take,
      include: {
        donor: true,
        campaign: true,
        items: { include: { product: true } }
      },
      orderBy: { date: 'desc' }
    });
  }

  // Get donations by donor
  async findDonationsByDonor(donorId: string) {
    return this.prisma.donation.findMany({
      where: { donorId },
      include: {
        donor: true,
        campaign: true,
        items: { include: { product: true } }
      },
      orderBy: { date: 'desc' }
    });
  }

  // Get donations by campaign
  async findDonationsByCampaign(campaignId: string) {
    return this.prisma.donation.findMany({
      where: { campaignId },
      include: {
        donor: true,
        campaign: true,
        items: { include: { product: true } }
      },
      orderBy: { date: 'desc' }
    });
  }

  // Get donation by id
  async findDonationById(id: string) {
    return this.prisma.donation.findUnique({
      where: { id },
      include: {
        donor: true,
        campaign: true,
        items: { include: { product: true } }
      },
    });
  }

  // Donor Operations

  // Create donor
  async createDonor(data: Prisma.DonorCreateInput) {
    return this.prisma.donor.create({ data });
  } 

  // List all donors
  async findAllDonors() {
    return this.prisma.donor.findMany({
      include: {
        donations: true
      }
    });
  }

  // Get donor by id
  async findDonorById(id: string) {
    return this.prisma.donor.findUnique({
      where: { id },
      include: {
        donations: {
          include: {
            items: { include: { product: true } }
          }
        }
      }
    });
  }

  // Update donor
  async updateDonor(id: string, data: Prisma.DonorUpdateInput) {
    return this.prisma.donor.update({
      where: { id },
      data
    });
  }

  // Delete donor
  async deleteDonor(id: string) {
    return this.prisma.donor.delete({
      where: { id }
    });
  }
}
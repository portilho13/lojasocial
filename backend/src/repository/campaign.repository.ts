import { Injectable } from '@nestjs/common';
import { Prisma } from '@prisma/client';
import { PrismaService } from 'prisma/prisma.service';

@Injectable()
export class CampaignRepository {
  constructor(private readonly prisma: PrismaService) {}

  // Create campaign
  async createCampaign(data: Prisma.CampaignCreateInput) {
    return this.prisma.campaign.create({
      data,
      include: {
        donations: true
      }
    });
  }

  // List all campaigns
  async findAllCampaigns(skip = 0, take = 50) {
    return this.prisma.campaign.findMany({
      skip,
      take,
      include: {
        donations: true
      },
      orderBy: { startDate: 'desc' }
    });
  }

  // List only active campaigns
  async findActiveCampaigns() {
    const now = new Date();
    return this.prisma.campaign.findMany({
      where: {
        startDate: { lte: now },
        OR: [
          { endDate: null },
          { endDate: { gte: now } }
        ]
      },
      include: {
        donations: true
      },
      orderBy: { startDate: 'desc' }
    });
  }

  // Get campaign by id
  async findCampaignById(id: string) {
    return this.prisma.campaign.findUnique({
      where: { id },
      include: {
        donations: {
          include: {
            donor: true,
            items: { include: { product: true } }
          }
        }
      }
    });
  }

  // Update campaign
  async updateCampaign(id: string, data: Prisma.CampaignUpdateInput) {
    return this.prisma.campaign.update({
      where: { id },
      data,
      include: {
        donations: true
      }
    });
  }

  // Delete campaign
  async deleteCampaign(id: string) {
    return this.prisma.campaign.delete({
      where: { id }
    });
  }
}
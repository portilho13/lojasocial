import { Injectable, NotFoundException, BadRequestException } from '@nestjs/common';
import { PrismaService } from 'prisma/prisma.service';
import { CampaignRepository } from '../repository/campaign.repository';
import { CreateCampaignDto } from '../dto/campaigns/create-campaign.dto';
import { UpdateCampaignDto } from '../dto/campaigns/update-campaign.dto';
import { CampaignResponseDto } from '../dto/campaigns/campaign-response.dto';

@Injectable()
export class CampaignService {
  constructor(
    private readonly prisma: PrismaService,
    private readonly campaignRepository: CampaignRepository,
  ) {}

  // Create new campaign
  async createCampaign(dto: CreateCampaignDto): Promise<CampaignResponseDto> {
    // Validate dates
    const startDate = new Date(dto.startDate);
    const endDate = dto.endDate ? new Date(dto.endDate) : null;

    if (endDate && endDate <= startDate) {
      throw new BadRequestException('End date must be after start date');
    }

    const campaign = await this.campaignRepository.createCampaign({
      title: dto.title,
      description: dto.description,
      startDate,
      endDate,
    });

    return new CampaignResponseDto(campaign);
  }

  // List all campaigns (authenticated users)
  async getAllCampaigns(skip?: number, take?: number): Promise<CampaignResponseDto[]> {
    const campaigns = await this.campaignRepository.findAllCampaigns(skip, take);
    return campaigns.map(c => new CampaignResponseDto(c));
  }

  // List only active campaigns (public endpoint)
  async getActiveCampaigns(): Promise<CampaignResponseDto[]> {
    const campaigns = await this.campaignRepository.findActiveCampaigns();
    return campaigns.map(c => new CampaignResponseDto(c));
  }

  // Get campaign by id
  async getCampaignById(id: string): Promise<CampaignResponseDto> {
    const campaign = await this.campaignRepository.findCampaignById(id);
    if (!campaign) {
      throw new NotFoundException(`Campaign with ID ${id} not found`);
    }
    return new CampaignResponseDto(campaign);
  }

  // Update campaign
  async updateCampaign(id: string, dto: UpdateCampaignDto): Promise<CampaignResponseDto> {
    const campaign = await this.campaignRepository.findCampaignById(id);
    if (!campaign) {
      throw new NotFoundException(`Campaign with ID ${id} not found`);
    }

    // Validate dates if provided
    const startDate = dto.startDate ? new Date(dto.startDate) : campaign.startDate;
    const endDate = dto.endDate ? new Date(dto.endDate) : campaign.endDate;

    if (endDate && endDate <= startDate) {
      throw new BadRequestException('End date must be after start date');
    }

    const updated = await this.campaignRepository.updateCampaign(id, {
      title: dto.title,
      description: dto.description,
      startDate: dto.startDate ? new Date(dto.startDate) : undefined,
      endDate: dto.endDate ? new Date(dto.endDate) : undefined,
    });

    return new CampaignResponseDto(updated);
  }

  // Delete campaign if it has no donations
  async deleteCampaign(id: string): Promise<void> {
    const campaign = await this.campaignRepository.findCampaignById(id);
    if (!campaign) {
      throw new NotFoundException(`Campaign with ID ${id} not found`);
    }

    if (campaign.donations && campaign.donations.length > 0) {
      throw new BadRequestException(
        'Cannot delete a campaign that has associated donations'
      );
    }

    await this.campaignRepository.deleteCampaign(id);
  }
}
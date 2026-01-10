import { Campaign, Donation } from '@prisma/client';

type CampaignWithRelations = Campaign & {
  donations?: Donation[];
};

export class CampaignResponseDto {
  id: Campaign['id'];
  title: Campaign['title'];
  description: Campaign['description'];
  startDate: Campaign['startDate'];
  endDate: Campaign['endDate'];
  isActive: boolean;
  donationCount?: number;

  constructor(campaign: CampaignWithRelations) {
    this.id = campaign.id;
    this.title = campaign.title;
    this.description = campaign.description;
    this.startDate = campaign.startDate;
    this.endDate = campaign.endDate;
    
    // Check if campaign is active
    const now = new Date();
    this.isActive = campaign.startDate <= now && (!campaign.endDate || campaign.endDate >= now);
    
    this.donationCount = campaign.donations?.length;
  }
}
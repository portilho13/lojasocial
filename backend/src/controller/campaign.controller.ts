import {
  Controller, Post, Get, Put, Delete, Body, Param, Query,
  Res, HttpStatus, HttpException, ValidationPipe, UseGuards
} from '@nestjs/common';
import type { Response } from 'express';
import { UserGuard } from 'src/common/guards/user.guard';
import { CampaignService } from '../service/campaign.service';
import { CreateCampaignDto } from '../dto/campaigns/create-campaign.dto';
import { UpdateCampaignDto } from '../dto/campaigns/update-campaign.dto';
import { CampaignResponseDto } from '../dto/campaigns/campaign-response.dto';

@Controller('api/v1/campaigns')
export class CampaignController {
  constructor(private readonly campaignService: CampaignService) {}

  // PUBLIC ROUTES

  // List only active campaigns 
  // GET /api/v1/campaigns/active
  @Get('active')
  async getActiveCampaigns(
    @Res() res: Response
  ): Promise<Response<CampaignResponseDto[]>> {
    try {
      const campaigns: CampaignResponseDto[] = await this.campaignService.getActiveCampaigns();
      return res.status(HttpStatus.OK).json(campaigns);
    } catch (e) {
      return res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({
        message: 'Error fetching active campaigns'
      });
    }
  }

  // USER-ONLY ROUTES 

  // Create new campaign
  // POST /api/v1/campaigns
  @Post()
  @UseGuards(UserGuard)
  async createCampaign(
    @Body(ValidationPipe) body: CreateCampaignDto,
    @Res() res: Response
  ): Promise<Response<CampaignResponseDto>> {
    try {
      const campaign: CampaignResponseDto = await this.campaignService.createCampaign(body);
      return res.status(HttpStatus.CREATED).json(campaign);
    } catch (e) {
      if (e instanceof HttpException) {
        return res.status(e.getStatus()).json(e.getResponse());
      }
      return res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({
        message: e.message || 'Error creating campaign'
      });
    }
  }

  // List all campaigns (includes inactive campaigns)
  // GET /api/v1/campaigns?skip=0&take=50
  @Get()
  @UseGuards(UserGuard)
  async getAllCampaigns(
    @Res() res: Response,
    @Query('skip') skip?: string,
    @Query('take') take?: string,
  ): Promise<Response<CampaignResponseDto[]>> {
    try {
      const campaigns: CampaignResponseDto[] = await this.campaignService.getAllCampaigns(
        skip ? parseInt(skip) : undefined,
        take ? parseInt(take) : undefined
      );
      return res.status(HttpStatus.OK).json(campaigns);
    } catch (e) {
      return res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({
        message: 'Error fetching campaigns'
      });
    }
  }

  // Get campaign by id
  // GET /api/v1/campaigns/:id
  @Get(':id')
  @UseGuards(UserGuard)
  async getCampaignById(
    @Param('id') id: string,
    @Res() res: Response
  ): Promise<Response<CampaignResponseDto>> {
    try {
      const campaign: CampaignResponseDto = await this.campaignService.getCampaignById(id);
      return res.status(HttpStatus.OK).json(campaign);
    } catch (e) {
      if (e instanceof HttpException) {
        return res.status(e.getStatus()).json(e.getResponse());
      }
      return res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({
        message: 'Error fetching campaign'
      });
    }
  }

  // Update campaign
  // PUT /api/v1/campaigns/:id
  @Put(':id')
  @UseGuards(UserGuard)
  async updateCampaign(
    @Param('id') id: string,
    @Body(ValidationPipe) body: UpdateCampaignDto,
    @Res() res: Response
  ): Promise<Response<CampaignResponseDto>> {
    try {
      const campaign: CampaignResponseDto = await this.campaignService.updateCampaign(id, body);
      return res.status(HttpStatus.OK).json(campaign);
    } catch (e) {
      if (e instanceof HttpException) {
        return res.status(e.getStatus()).json(e.getResponse());
      }
      return res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({
        message: 'Error updating campaign'
      });
    }
  }

  // Delete campaign (only if no donations associated)
  // DELETE /api/v1/campaigns/:id
  @Delete(':id')
  @UseGuards(UserGuard)
  async deleteCampaign(
    @Param('id') id: string,
    @Res() res: Response
  ): Promise<Response<void>> {
    try {
      await this.campaignService.deleteCampaign(id);
      return res.status(HttpStatus.OK).json({
        message: 'Campaign deleted successfully'
      });
    } catch (e) {
      if (e instanceof HttpException) {
        return res.status(e.getStatus()).json(e.getResponse());
      }
      return res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({
        message: 'Error deleting campaign'
      });
    }
  }
}
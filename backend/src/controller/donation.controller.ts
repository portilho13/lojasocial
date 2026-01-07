import {
  Controller, Post, Get, Put, Delete, Body, Param, Query, 
  Res, Req, HttpStatus, HttpException, ValidationPipe, UseGuards, ParseIntPipe
} from '@nestjs/common';
import type { Request, Response } from 'express';
import { AccessTokenGuard } from 'src/common/guards/access-token.guard';
import { DonationService } from '../service/donation.service';
import { CreateDonationDto } from '../dto/donations/create-donation.dto';
import { CreateDonorDto } from '../dto/donations/create-donor.dto';
import { DonationResponseDto } from '../dto/donations/donation-response.dto';
import { DonorResponseDto } from '../dto/donations/donor-response.dto';

@Controller('api/v1/donations')
@UseGuards(AccessTokenGuard) 
export class DonationController {
  constructor(private readonly donationService: DonationService) {}

  // DONATION ROUTES

  // Register a new donation
  // POST /api/v1/donations
  @Post()
  async registerDonation(
    @Body(ValidationPipe) body: CreateDonationDto,
    @Req() req: Request, 
    @Res() res: Response
  ): Promise<Response<DonationResponseDto>> {
    try {
      const userId = (req.user as any)?.sub; 
      const donation: DonationResponseDto = await this.donationService.registerDonation(body, userId);
      return res.status(HttpStatus.CREATED).json(donation);
    } catch (e) {
      if (e instanceof HttpException) {
        return res.status(e.getStatus()).json(e.getResponse());
      }
      return res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({
        message: e.message || 'Internal server error'
      });
    }
  }

  // List all donations
  // GET /api/v1/donations?skip=0&take=50
  @Get()
  async getAllDonations(
    @Res() res: Response,
    @Query('skip') skip?: string,
    @Query('take') take?: string,
  ): Promise<Response<DonationResponseDto[]>> {
    try {
      const donations: DonationResponseDto[] = await this.donationService.getAllDonations(
        skip ? parseInt(skip) : undefined,
        take ? parseInt(take) : undefined
      );
      return res.status(HttpStatus.OK).json(donations);
    } catch (e) {
      return res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({
        message: 'Error fetching donations'
      });
    }
  }

  // Get donations by Id
  // GET /api/v1/donations/:id
  @Get(':id')
  async getDonationById(
    @Param('id', ParseIntPipe) id: number,
    @Res() res: Response
  ): Promise<Response<DonationResponseDto>> {
    try {
      const donation: DonationResponseDto = await this.donationService.getDonationById(id);
      return res.status(HttpStatus.OK).json(donation);
    } catch (e) {
      if (e instanceof HttpException) {
        return res.status(e.getStatus()).json(e.getResponse());
      }
      return res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({
        message: 'Error fetching donation'
      });
    }
  }

  // List donations from a specific donor
  // GET /api/v1/donations/donor/:donorId
  @Get('donor/:donorId')
  async getDonationsByDonor(
    @Param('donorId', ParseIntPipe) donorId: number,
    @Res() res: Response
  ): Promise<Response<DonationResponseDto[]>> {
    try {
      const donations: DonationResponseDto[] = await this.donationService.getDonationsByDonor(donorId);
      return res.status(HttpStatus.OK).json(donations);
    } catch (e) {
      return res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({
        message: 'Error fetching donations from donor'
      });
    }
  }

  // List donations for a specific campaign
  // GET /api/v1/donations/campaign/:campaignId
  @Get('campaign/:campaignId')
  async getDonationsByCampaign(
    @Param('campaignId', ParseIntPipe) campaignId: number,
    @Res() res: Response
  ): Promise<Response<DonationResponseDto[]>> {
    try {
      const donations: DonationResponseDto[] = await this.donationService.getDonationsByCampaign(campaignId);
      return res.status(HttpStatus.OK).json(donations);
    } catch (e) {
      return res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({
        message: 'Error fetching donations from campaign'
      });
    }
  }

  // DONOR ROUTES

  // Create new donor
  // POST /api/v1/donations/donors
  @Post('donors')
  async createDonor(
    @Body(ValidationPipe) body: CreateDonorDto,
    @Res() res: Response
  ): Promise<Response<DonorResponseDto>> {
    try {
      const donor: DonorResponseDto = await this.donationService.createDonor(body);
      return res.status(HttpStatus.CREATED).json(donor);
    } catch (e) {
      if (e instanceof HttpException) {
        return res.status(e.getStatus()).json(e.getResponse());
      }
      return res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({
        message: 'Error creating donor'
      });
    }
  }

  // Get all donors
  // GET /api/v1/donations/donors
  @Get('donors')
  async getAllDonors(@Res() res: Response): Promise<Response<DonorResponseDto[]>> {
    try {
      const donors: DonorResponseDto[] = await this.donationService.getAllDonors();
      return res.status(HttpStatus.OK).json(donors);
    } catch (e) {
      return res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({
        message: 'Error fetching donors'
      });
    }
  }

  // Get donor by ID
  // GET /api/v1/donations/donors/:id
  @Get('donors/:id')
  async getDonorById(
    @Param('id', ParseIntPipe) id: number,
    @Res() res: Response
  ): Promise<Response<DonorResponseDto>> {
    try {
      const donor: DonorResponseDto = await this.donationService.getDonorById(id);
      return res.status(HttpStatus.OK).json(donor);
    } catch (e) {
      if (e instanceof HttpException) {
        return res.status(e.getStatus()).json(e.getResponse());
      }
      return res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({
        message: 'Error fetching donor'
      });
    }
  }

  // Update donor info
  // PUT /api/v1/donations/donors/:id
  @Put('donors/:id')
  async updateDonor(
    @Param('id', ParseIntPipe) id: number,
    @Body(ValidationPipe) body: Partial<CreateDonorDto>,
    @Res() res: Response
  ): Promise<Response<DonorResponseDto>> {
    try {
      const donor: DonorResponseDto = await this.donationService.updateDonor(id, body);
      return res.status(HttpStatus.OK).json(donor);
    } catch (e) {
      if (e instanceof HttpException) {
        return res.status(e.getStatus()).json(e.getResponse());
      }
      return res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({
        message: 'Error updating donor'
      });
    }
  }

  // Remove donor if they have no associated donations
  // DELETE /api/v1/donations/donors/:id
  @Delete('donors/:id')
  async deleteDonor(
    @Param('id', ParseIntPipe) id: number,
    @Res() res: Response
  ): Promise<Response<void>> {
    try {
      await this.donationService.deleteDonor(id);
      return res.status(HttpStatus.OK).json({
        message: 'Donor removed successfully'
      });
    } catch (e) {
      if (e instanceof HttpException) {
        return res.status(e.getStatus()).json(e.getResponse());
      }
      return res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({
        message: 'Error removing donor'
      });
    }
  }
}
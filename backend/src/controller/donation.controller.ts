import { 
  Controller, Post, Get, Patch, Body, Param, Query, Res, 
  HttpStatus, HttpException, ValidationPipe 
} from '@nestjs/common';
import type { Response } from 'express';
import { DonationStatus } from '@prisma/client';
import { DonationService } from '../service/donation.service';
import { CreateDonationDto } from '../dto/donations/create-donation.dto';

@Controller('api/v1/donations')
export class DonationController {
  constructor(private readonly donationService: DonationService) {}

  // Create a new donation request 
  // Route: POST /api/v1/donations
  @Post()
  async createDonation(
    @Body(ValidationPipe) body: CreateDonationDto,
    @Res() res: Response
  ) {
    try {
      const donation = await this.donationService.createDonation(body);
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

  // Confirm delivery and deduct stock
  // Route: PATCH /api/v1/donations/:id/confirm
  @Patch(':id/confirm')
  async confirmDelivery(@Param('id') id: string, @Res() res: Response) {
    try {
      const donation = await this.donationService.confirmDelivery(id);
      return res.status(HttpStatus.OK).json(donation);
    } catch (e) {
      if (e instanceof HttpException) {
        return res.status(e.getStatus()).json(e.getResponse());
      }
      return res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({ 
        message: e.message || 'Internal server error' 
      });
    }
  }

  // Cancel donation request
  // Route: PATCH /api/v1/donations/:id/cancel
  @Patch(':id/cancel')
  async cancelDonation(@Param('id') id: string, @Res() res: Response) {
    try {
      const donation = await this.donationService.cancelDonation(id);
      return res.status(HttpStatus.OK).json(donation);
    } catch (e) {
      if (e instanceof HttpException) {
        return res.status(e.getStatus()).json(e.getResponse());
      }
      return res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({ 
        message: e.message || 'Internal server error' 
      });
    }
  }

  // Get all donations 
  // Route: GET /api/v1/donations
  @Get()
  async getAllDonations(
    @Res() res: Response,
    @Query('skip') skip?: string,
    @Query('take') take?: string,
    @Query('status') status?: string
  ) {
    try {
      let donations;
      
      if (status && Object.values(DonationStatus).includes(status as DonationStatus)) {
        donations = await this.donationService.getDonationsByStatus(status as DonationStatus);
      } else {
        donations = await this.donationService.getAllDonations(
          skip ? parseInt(skip) : undefined,
          take ? parseInt(take) : undefined
        );
      }
      
      return res.status(HttpStatus.OK).json(donations);
    } catch (e) {
      return res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({ 
        message: 'Internal server error' 
      });
    }
  }

  // Get donation by ID
  // Route: GET /api/v1/donations/:id
  @Get(':id')
  async getDonationById(@Param('id') id: string, @Res() res: Response) {
    try {
      const donation = await this.donationService.getDonationById(id);
      return res.status(HttpStatus.OK).json(donation);
    } catch (e) {
      if (e instanceof HttpException) {
        return res.status(e.getStatus()).json(e.getResponse());
      }
      return res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({ 
        message: 'Internal server error' 
      });
    }
  }

  // Get student donation history
  // Route: GET /api/v1/donations/student/:id
  @Get('student/:id')
  async getStudentHistory(@Param('id') id: string, @Res() res: Response) {
    try {
      const history = await this.donationService.getStudentHistory(id);
      return res.status(HttpStatus.OK).json(history);
    } catch (e) {
      return res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({ 
        message: 'Internal server error' 
      });
    }
  }
}
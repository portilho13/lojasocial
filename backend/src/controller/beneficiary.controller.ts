import { Controller, Get, Post, Patch, Delete, Param, Body, Query, Res, HttpStatus, ValidationPipe } from '@nestjs/common';
import { BeneficiaryService } from 'src/service/beneficiary.service';
import { CreateBeneficiaryDto } from 'src/dto/beneficiary/create-beneficiary.dto';
import { UpdateBeneficiaryDto } from 'src/dto/beneficiary/update-beneficiary.dto';
import { BeneficiaryResponseDto } from 'src/dto/beneficiary/beneficiary-response.dto';
import type { Response } from 'express';

@Controller('api/v1/beneficiaries')
export class BeneficiaryController {
  constructor(private readonly service: BeneficiaryService) {}

  // Register a new beneficiary
  // Route: POST /api/v1/beneficiaries
  @Post()
  async create(@Body(ValidationPipe) body: CreateBeneficiaryDto, @Res() res: Response) {
    try {
      const beneficiary = await this.service.create(body);
      return res.status(HttpStatus.CREATED).json(new BeneficiaryResponseDto(beneficiary));
    } catch (e) {
      return res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({ message: 'Internal server error' });
    }
  }

  // List all beneficiaries with optional pagination
  // Route: GET /api/v1/beneficiaries?skip=0&take=50
  @Get()
  async findAll(@Res() res: Response, @Query('skip') skip?: string, @Query('take') take?: string) {
    try {
      const beneficiaries = await this.service.findAll(
        skip ? parseInt(skip) : undefined,
        take ? parseInt(take) : undefined
      );
      const response = beneficiaries.map(b => new BeneficiaryResponseDto(b));
      return res.status(HttpStatus.OK).json(response);
    } catch (e) {
      return res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({ message: 'Internal server error' });
    }
  }

  // Get a single beneficiary by ID
  // Route: GET /api/v1/beneficiaries/:id
  @Get(':id')
  async findOne(@Param('id') id: string, @Res() res: Response) {
    try {
      const beneficiary = await this.service.findOne(id);
      return res.status(HttpStatus.OK).json(new BeneficiaryResponseDto(beneficiary));
    } catch (e) {
      return res.status(HttpStatus.NOT_FOUND).json({ message: 'Beneficiary not found' });
    }
  }

  // Update a beneficiary by ID
  // Route: PATCH /api/v1/beneficiaries/:id
  @Patch(':id')
  async update(@Param('id') id: string, @Body(ValidationPipe) body: UpdateBeneficiaryDto, @Res() res: Response) {
    try {
      const beneficiary = await this.service.update(id, body);
      return res.status(HttpStatus.OK).json(new BeneficiaryResponseDto(beneficiary));
    } catch (e) {
      if (e.status === 404) return res.status(HttpStatus.NOT_FOUND).json({ message: 'Beneficiary not found' });
      return res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({ message: 'Internal server error' });
    }
  }

  // Delete a beneficiary by ID
  // Route: DELETE /api/v1/beneficiaries/:id
  @Delete(':id')
  async delete(@Param('id') id: string, @Res() res: Response) {
    try {
      await this.service.delete(id);
      return res.status(HttpStatus.NO_CONTENT).send();
    } catch (e) {
      if (e.status === 404) return res.status(HttpStatus.NOT_FOUND).json({ message: 'Beneficiary not found' });
      return res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({ message: 'Internal server error' });
    }
  }
}

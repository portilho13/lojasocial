import { Controller, Get, Post, Patch, Delete, Param, Body, Query, Res, HttpStatus, ValidationPipe } from '@nestjs/common';
import { BeneficiaryService } from 'src/service/beneficiary.service';
import { CreateBeneficiaryDto } from 'src/dto/beneficiary/create-beneficiary.dto';
import { UpdateBeneficiaryDto } from 'src/dto/beneficiary/update-beneficiary.dto';
import { BeneficiaryResponseDto } from 'src/dto/beneficiary/beneficiary-response.dto';
import type { Response } from 'express';

@Controller('api/v1/beneficiaries')
export class BeneficiaryController {
  constructor(private readonly service: BeneficiaryService) {}

  @Post()
  async create(@Body(ValidationPipe) body: CreateBeneficiaryDto, @Res() res: Response) {
    try {
      const beneficiary = await this.service.create(body);
      return res.status(HttpStatus.CREATED).json(new BeneficiaryResponseDto(beneficiary));
    } catch (e) {
      return res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({ message: 'Internal server error' });
    }
  }

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

  @Get(':id')
  async findOne(@Param('id') id: string, @Res() res: Response) {
    try {
      const beneficiary = await this.service.findOne(id);
      return res.status(HttpStatus.OK).json(new BeneficiaryResponseDto(beneficiary));
    } catch (e) {
      return res.status(HttpStatus.NOT_FOUND).json({ message: 'Beneficiário não encontrado' });
    }
  }

  @Patch(':id')
  async update(@Param('id') id: string, @Body(ValidationPipe) body: UpdateBeneficiaryDto, @Res() res: Response) {
    try {
      const beneficiary = await this.service.update(id, body);
      return res.status(HttpStatus.OK).json(new BeneficiaryResponseDto(beneficiary));
    } catch (e) {
      if (e.status === 404) return res.status(HttpStatus.NOT_FOUND).json({ message: 'Beneficiário não encontrado' });
      return res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({ message: 'Internal server error' });
    }
  }

  @Delete(':id')
  async delete(@Param('id') id: string, @Res() res: Response) {
    try {
      await this.service.delete(id);
      return res.status(HttpStatus.NO_CONTENT).send();
    } catch (e) {
      if (e.status === 404) return res.status(HttpStatus.NOT_FOUND).json({ message: 'Beneficiário não encontrado' });
      return res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({ message: 'Internal server error' });
    }
  }
}

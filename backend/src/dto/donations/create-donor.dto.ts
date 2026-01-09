import { IsString, IsOptional, IsEnum } from 'class-validator';
import { DonorType } from '@prisma/client';

export class CreateDonorDto {
  @IsString()
  @IsOptional()
  name?: string; // Can be null if it is anonym

  @IsString()
  @IsOptional()
  nif?: string;

  @IsEnum(DonorType)
  type: DonorType;

  @IsString()
  @IsOptional()
  contact?: string;
}
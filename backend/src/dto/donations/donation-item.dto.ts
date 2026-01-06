import { IsInt, IsPositive, IsDateString, IsOptional, IsString } from 'class-validator';

export class DonationItemDto {
  @IsInt()
  @IsPositive()
  productId: number;

  @IsInt()
  @IsPositive()
  quantity: number;

  @IsDateString()
  @IsOptional()
  expiryDate?: string; 

  @IsString()
  @IsOptional()
  location?: string; 
}
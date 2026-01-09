import { IsInt, IsPositive, IsDateString, IsOptional, IsString, IsNotEmpty } from 'class-validator';

export class DonationItemDto {
  @IsString()
  @IsNotEmpty()
  productId: string;

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
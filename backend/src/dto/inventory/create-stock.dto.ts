import { IsDateString, IsInt, IsNotEmpty, IsOptional, IsString, IsUUID, Min } from 'class-validator';

export class CreateStockDto {
  @IsInt()
  @Min(1)
  @IsNotEmpty()
  quantity: number; 

  @IsDateString()
  @IsNotEmpty()
  movementDate: string; 

  @IsDateString()
  @IsNotEmpty()
  expiryDate: string; 

  @IsString()
  @IsOptional()
  notes?: string;

  @IsUUID()
  @IsNotEmpty()
  productId: string; 

  @IsUUID()
  @IsOptional()
  userId?: string; 
}
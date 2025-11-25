import { IsUUID, IsString, IsInt, IsOptional, IsDate } from 'class-validator';

export class StockResponseDto {
  @IsUUID()
  id: string;

  @IsInt()
  quantity: number;

  @IsDate()
  movementDate: Date;

  @IsDate()
  expiryDate: Date;

  @IsOptional()
  @IsString()
  notes?: string;

  @IsUUID()
  productId: string;

  @IsString()
  productName: string;

  @IsOptional()
  @IsUUID()
  userId?: string;

  @IsOptional()
  @IsString()
  userName?: string;
}

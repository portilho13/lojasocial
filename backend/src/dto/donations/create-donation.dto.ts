import { IsArray, IsNotEmpty, IsOptional, IsString, IsUUID, ValidateNested, IsInt, IsPositive } from 'class-validator';
import { Type } from 'class-transformer';

class DonationItemDto {
  @IsUUID()
  @IsNotEmpty()
  productId: string;

  @IsInt()
  @IsPositive()
  @IsNotEmpty()
  quantity: number;
}

export class CreateDonationDto {
  @IsUUID()
  @IsNotEmpty()
  studentId: string;

  @IsArray()
  @ValidateNested({ each: true })
  @Type(() => DonationItemDto)
  items: DonationItemDto[];

  @IsString()
  @IsOptional()
  notes?: string;
}
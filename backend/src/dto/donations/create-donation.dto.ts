import { IsArray, IsNotEmpty, IsOptional, IsString, ValidateNested } from 'class-validator';
import { Type } from 'class-transformer';
import { DonationItemDto } from './donation-item.dto';

export class CreateDonationDto {
  @IsString()
  @IsNotEmpty()
  donorId: string;

  @IsString()
  @IsOptional()
  campaignId?: string;

  @IsArray()
  @ValidateNested({ each: true })
  @Type(() => DonationItemDto)
  items: DonationItemDto[];
}
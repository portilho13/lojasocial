import { IsArray, IsNotEmpty, IsOptional, IsInt, ValidateNested } from 'class-validator';
import { Type } from 'class-transformer';
import { DonationItemDto } from './donation-item.dto';

export class CreateDonationDto {
  @IsInt()
  @IsNotEmpty()
  donorId: number;

  @IsInt()
  @IsOptional()
  campaignId?: number; // (optional) Campaign associated with the donation

  @IsArray()
  @ValidateNested({ each: true })
  @Type(() => DonationItemDto)
  items: DonationItemDto[];
}
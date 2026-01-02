import { IsInt, IsOptional, IsString, Min } from 'class-validator';

export class UpdateStockDto {
  @IsInt()
  @Min(0)
  @IsOptional()
  quantity?: number;

  @IsString()
  @IsOptional()
  notes?: string;
}
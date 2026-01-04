import { IsInt, IsOptional, Min } from 'class-validator';

export class UpdateStockDto {
  @IsInt()
  @Min(0)
  @IsOptional()
  quantity?: number;
}
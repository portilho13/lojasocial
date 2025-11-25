import { IsUUID, IsString } from 'class-validator';

export class ProductResponseDto {
  @IsUUID()
  id: string;

  @IsString()
  name: string;

  @IsUUID()
  productTypeId: string;

  @IsString()
  productTypeDescription: string;
}

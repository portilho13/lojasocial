import { IsInt, IsNotEmpty, IsOptional, IsString, Min } from 'class-validator';

export class CreateRequestItemDto {
    @IsInt()
    @IsNotEmpty()
    productId: number;

    @IsInt()
    @Min(1)
    @IsNotEmpty()
    qtyRequested: number;

    @IsString()
    @IsOptional()
    observation?: string;
}

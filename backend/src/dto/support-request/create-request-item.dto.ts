import { IsInt, IsNotEmpty, IsOptional, IsString, IsUUID, Min } from 'class-validator';

export class CreateRequestItemDto {
    @IsUUID()
    @IsNotEmpty()
    productId: string;

    @IsInt()
    @Min(1)
    @IsNotEmpty()
    qtyRequested: number;

    @IsString()
    @IsOptional()
    observation?: string;
}

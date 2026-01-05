import { IsArray, IsNotEmpty, IsOptional, IsString, IsUUID, ValidateNested } from 'class-validator';
import { Type } from 'class-transformer';
import { CreateRequestItemDto } from './create-request-item.dto';

export class CreateSupportRequestDto {
    @IsUUID()
    @IsNotEmpty()
    studentId: string;

    @IsArray()
    @ValidateNested({ each: true })
    @Type(() => CreateRequestItemDto)
    items: CreateRequestItemDto[];

    @IsString()
    @IsOptional()
    observation?: string;
}

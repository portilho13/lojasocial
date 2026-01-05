import { RequestStatus } from '@prisma/client';
import { IsEnum, IsNotEmpty } from 'class-validator';

export class UpdateSupportRequestStatusDto {
    @IsEnum(RequestStatus)
    @IsNotEmpty()
    status: RequestStatus;
}

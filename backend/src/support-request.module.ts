import { Module } from '@nestjs/common';
import { SupportRequestController } from './controller/support-request.controller';
import { SupportRequestService } from './service/support-request.service';
import { SupportRequestRepository } from './repository/support-request.repository';
import { PrismaModule } from 'prisma/prisma.module';

@Module({
    imports: [PrismaModule],
    controllers: [SupportRequestController],
    providers: [SupportRequestService, SupportRequestRepository],
})
export class SupportRequestModule { }

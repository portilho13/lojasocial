import {
    Body,
    Controller,
    Get,
    Param,
    Patch,
    Post,
    Req,
    UseGuards,
    ValidationPipe,
} from '@nestjs/common';
import { SupportRequestService } from '../service/support-request.service';
import { CreateSupportRequestDto } from '../dto/support-request/create-support-request.dto';
import { UpdateSupportRequestStatusDto } from '../dto/support-request/update-support-request-status.dto';
import { AccessTokenGuard } from '../common/guards/access-token.guard';
import { StudentGuard } from '../common/guards/student.guard';
import { UserGuard } from '../common/guards/user.guard';
import type { Request } from 'express';

@Controller('api/v1/support-requests')
export class SupportRequestController {
    constructor(private readonly service: SupportRequestService) { }

    @UseGuards(StudentGuard)
    @Post()
    async create(@Body(ValidationPipe) body: CreateSupportRequestDto, @Req() req: Request) {
        // Ensure student can only create for themselves if needed, or trust the DTO
        // For now, we trust the DTO but ideally we should override studentId from token if it's a student
        const user = req.user as any;
        // If we want to enforce that the logged-in student matches the DTO:
        // if (user.role === 'STUDENT') body.studentId = user.sub;

        return this.service.createRequest(body);
    }

    @UseGuards(UserGuard)
    @Get()
    async getAll(@Req() req: Request) {
        // Ideally restrict to STAFF/ADMIN
        return this.service.getAllRequests();
    }

    @UseGuards(StudentGuard)
    @Get('me')
    async getMyRequests(@Req() req: Request) {
        const user = req.user as any;
        return this.service.getRequestsByStudent(user.sub);
    }

    @UseGuards(UserGuard)
    @Patch(':id/status')
    async updateStatus(
        @Param('id') id: string,
        @Body(ValidationPipe) body: UpdateSupportRequestStatusDto,
        @Req() req: Request,
    ) {
        const user = req.user as any;
        // Ideally restrict to STAFF/ADMIN
        return this.service.updateStatus(id, body, user.sub);
    }
}

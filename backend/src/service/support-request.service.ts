import { Injectable, NotFoundException } from '@nestjs/common';
import { SupportRequestRepository } from '../repository/support-request.repository';
import { CreateSupportRequestDto } from '../dto/support-request/create-support-request.dto';
import { UpdateSupportRequestStatusDto } from '../dto/support-request/update-support-request-status.dto';
import { SupportRequestResponseDto } from '../dto/support-request/support-request-response.dto';
import { Prisma } from '@prisma/client';

@Injectable()
export class SupportRequestService {
    constructor(private readonly repository: SupportRequestRepository) { }

    public async createRequest(dto: CreateSupportRequestDto) {
        const { studentId, items, observation } = dto;

        const prismaData: Prisma.SupportRequestCreateInput = {
            student: { connect: { id: studentId } },
            observation,
            items: {
                create: items.map((item) => ({
                    qtyRequested: item.qtyRequested,
                    observation: item.observation,
                    product: { connect: { id: item.productId } },
                })),
            },
        };

        const request = await this.repository.create(prismaData);
        return new SupportRequestResponseDto(request);
    }

    public async getAllRequests() {
        const requests = await this.repository.findAll();
        return requests.map((r) => new SupportRequestResponseDto(r));
    }

    public async getRequestsByStudent(studentId: string) {
        const requests = await this.repository.findAll({ studentId });
        return requests.map((r) => new SupportRequestResponseDto(r));
    }

    public async updateStatus(id: string, dto: UpdateSupportRequestStatusDto, userId: string) {
        const request = await this.repository.findById(id);
        if (!request) {
            throw new NotFoundException('Support request not found');
        }

        const updatedRequest = await this.repository.updateStatus(id, dto.status, userId);
        return new SupportRequestResponseDto(updatedRequest);
    }
}

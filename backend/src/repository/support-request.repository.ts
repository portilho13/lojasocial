import { Injectable } from '@nestjs/common';
import { PrismaService } from 'prisma/prisma.service';
import { Prisma, RequestStatus } from '@prisma/client';

@Injectable()
export class SupportRequestRepository {
    constructor(private readonly prisma: PrismaService) { }

    public async create(data: Prisma.SupportRequestCreateInput) {
        return this.prisma.supportRequest.create({
            data,
            include: {
                items: {
                    include: {
                        product: true,
                    },
                },
            },
        });
    }

    public async findAll(where?: Prisma.SupportRequestWhereInput) {
        return this.prisma.supportRequest.findMany({
            where,
            include: {
                items: {
                    include: {
                        product: true,
                    },
                },
            },
            orderBy: {
                date: 'desc',
            },
        });
    }

    public async findById(id: string) {
        return this.prisma.supportRequest.findUnique({
            where: { id },
            include: {
                items: {
                    include: {
                        product: true,
                    },
                },
            },
        });
    }

    public async updateStatus(id: string, status: RequestStatus, userId: string) {
        return this.prisma.supportRequest.update({
            where: { id },
            data: {
                status,
                userId, // Set the staff user who processed it
            },
            include: {
                items: {
                    include: {
                        product: true,
                    },
                },
            },
        });
    }
}

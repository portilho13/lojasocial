import { Injectable } from '@nestjs/common';
import { PrismaService } from 'prisma/prisma.service';
import { Prisma, Student } from '@prisma/client';

@Injectable()
export class BeneficiaryRepository {
  constructor(private readonly prisma: PrismaService) {}

  async create(data: Prisma.StudentCreateInput): Promise<Student> {
    return this.prisma.student.create({ data });
  }

  async findAll(skip = 0, take = 50): Promise<Student[]> {
    return this.prisma.student.findMany({
      skip,
      take,
      orderBy: { name: 'asc' },
    });
  }

  async findById(id: string): Promise<Student | null> {
    return this.prisma.student.findUnique({ where: { id } });
  }

  async update(id: string, data: Prisma.StudentUpdateInput): Promise<Student> {
    return this.prisma.student.update({ where: { id }, data });
  }

  async delete(id: string): Promise<Student> {
    return this.prisma.student.delete({ where: { id } });
  }
}

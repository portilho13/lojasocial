import { Injectable } from "@nestjs/common";
import { Prisma, Student } from "@prisma/client";
import { PrismaService } from "prisma/prisma.service";

@Injectable()
export class StudentRepository {
    constructor(private readonly prisma: PrismaService) { }

    public async getStudentByEmail(email: Student["email"]) {
        return this.prisma.student.findUnique({
            where: { email }
        })
    }

    public async getStudentById(id: string) {
        return this.prisma.student.findUnique({
            where: { id }
        })
    }

    public async createStudent(data: Prisma.StudentCreateInput) {
        return this.prisma.student.create({
            data
        })
    }

    public async updateStudent(id: string, data: Prisma.StudentUpdateInput) {
        return this.prisma.student.update({
            where: { id },
            data
        })
    }
}
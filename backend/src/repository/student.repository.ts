import { Injectable } from "@nestjs/common";
import { Prisma, Student } from "generated/prisma/client";
import { PrismaService } from "prisma/prisma.service";

@Injectable()
export class StudentRepository {
    constructor (private readonly prisma: PrismaService) {}

    public async getStudentByEmail(email: Student["email"]) {
        return this.prisma.student.findUnique({
            where: {email}
        })
    }

    public async createStudent(data: Prisma.StudentCreateInput) {
        return this.prisma.create({
            data
        })
    }
}
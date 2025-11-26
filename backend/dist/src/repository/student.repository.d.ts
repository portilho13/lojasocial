import { Prisma, Student } from "@prisma/client";
import { PrismaService } from "prisma/prisma.service";
export declare class StudentRepository {
    private readonly prisma;
    constructor(prisma: PrismaService);
    getStudentByEmail(email: Student["email"]): Promise<{
        name: string;
        studentNumber: string;
        course: string;
        academicYear: number;
        socialSecurityNumber: string;
        contact: string;
        email: string;
        password: string;
        id: string;
        hashedRefreshToken: string | null;
    } | null>;
    createStudent(data: Prisma.StudentCreateInput): Promise<{
        name: string;
        studentNumber: string;
        course: string;
        academicYear: number;
        socialSecurityNumber: string;
        contact: string;
        email: string;
        password: string;
        id: string;
        hashedRefreshToken: string | null;
    }>;
    updateStudent(id: string, data: Prisma.StudentUpdateInput): Promise<{
        name: string;
        studentNumber: string;
        course: string;
        academicYear: number;
        socialSecurityNumber: string;
        contact: string;
        email: string;
        password: string;
        id: string;
        hashedRefreshToken: string | null;
    }>;
}

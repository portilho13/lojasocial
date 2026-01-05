import { Student } from '@prisma/client';

export class BeneficiaryResponseDto {
  id: string;
  name: string;
  studentNumber: string;
  course: string;
  academicYear: number;
  socialSecurityNumber?: string | null;
  contact?: string | null;
  email: string;
  status?: string | null;

  constructor(student: Student) {
    this.id = student.id;
    this.name = student.name;
    this.studentNumber = student.studentNumber;
    this.course = student.course;
    this.academicYear = student.academicYear;
    this.socialSecurityNumber = student.socialSecurityNumber ?? null;
    this.contact = student.contact ?? null;
    this.email = student.email;
    this.status = student.status ?? null;
  }
}

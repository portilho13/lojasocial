import { Student } from '@prisma/client';

export class BeneficiaryResponseDto {
  id: Student['id'];
  name: Student['name'];
  studentNumber: Student['studentNumber'];
  course: Student['course'];
  academicYear: Student['academicYear'];
  socialSecurityNumber: Student['socialSecurityNumber'];
  contact: Student['contact'];
  email: Student['email'];
  status: Student['status'];

  constructor(student: Student) {
    this.id = student.id;
    this.name = student.name;
    this.studentNumber = student.studentNumber;
    this.course = student.course;
    this.academicYear = student.academicYear;
    this.socialSecurityNumber = student.socialSecurityNumber ?? null;
    this.contact = student.contact ?? null;
    this.email = student.email;
    this.status = student.status;
  }
}

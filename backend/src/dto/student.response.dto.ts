import { Student } from "generated/prisma/client";

export class StudentResponseDto {
  id: Student['id'];
  name: Student['name'];
  studentNumber: Student['studentNumber'];
  course: Student['course'];
  academicYear: Student['academicYear'];
  socialSecurityNumber: Student['socialSecurityNumber'];
  contact: Student['contact'];
  email: Student['email'];

  constructor(student: Student) {
    this.id = student.id;
    this.name = student.name;
    this.studentNumber = student.studentNumber;
    this.course = student.course;
    this.academicYear = student.academicYear;
    this.socialSecurityNumber = student.socialSecurityNumber;
    this.contact = student.contact;
    this.email = student.email;
  }
}
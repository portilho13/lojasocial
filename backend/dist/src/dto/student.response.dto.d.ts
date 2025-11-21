import { Student } from "@prisma/client";
export declare class StudentResponseDto {
    id: Student['id'];
    name: Student['name'];
    studentNumber: Student['studentNumber'];
    course: Student['course'];
    academicYear: Student['academicYear'];
    socialSecurityNumber: Student['socialSecurityNumber'];
    contact: Student['contact'];
    email: Student['email'];
    constructor(student: Student);
}

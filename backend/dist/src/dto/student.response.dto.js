"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.StudentResponseDto = void 0;
class StudentResponseDto {
    id;
    name;
    studentNumber;
    course;
    academicYear;
    socialSecurityNumber;
    contact;
    email;
    constructor(student) {
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
exports.StudentResponseDto = StudentResponseDto;
//# sourceMappingURL=student.response.dto.js.map
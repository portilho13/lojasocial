import { StudentSignUpDto } from "src/dto/student.sign-up.dto";
import { StudentRepository } from "src/repository/student.repository";
import { StudentResponseDto } from "src/dto/student.response.dto";
export declare class StudentService {
    private readonly studentRepository;
    constructor(studentRepository: StudentRepository);
    signUp(dto: StudentSignUpDto): Promise<StudentResponseDto>;
}

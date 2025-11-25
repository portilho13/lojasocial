import { StudentSignUpDto } from "src/dto/student.sign-up.dto";
import { StudentSignInDto } from "src/dto/student.sign-in.dto";
import { StudentRepository } from "src/repository/student.repository";
import { StudentResponseDto } from "src/dto/student.response.dto";
export declare class StudentService {
    private readonly studentRepository;
    constructor(studentRepository: StudentRepository);
    signUp(dto: StudentSignUpDto): Promise<StudentResponseDto>;
    signIn(dto: StudentSignInDto): Promise<StudentResponseDto>;
}

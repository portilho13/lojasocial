import { JwtService } from "@nestjs/jwt";
import { StudentSignUpDto } from "src/dto/student.sign-up.dto";
import { StudentSignInDto } from "src/dto/student.sign-in.dto";
import { StudentRepository } from "src/repository/student.repository";
import { StudentResponseDto } from "src/dto/student.response.dto";
export declare class StudentService {
    private readonly studentRepository;
    private readonly jwtService;
    constructor(studentRepository: StudentRepository, jwtService: JwtService);
    signUp(dto: StudentSignUpDto): Promise<StudentResponseDto>;
    signIn(dto: StudentSignInDto): Promise<{
        accessToken: string;
        refreshToken: string;
    }>;
    logout(userId: string): Promise<{
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
    refreshTokens(userId: string, refreshToken: string): Promise<{
        accessToken: string;
        refreshToken: string;
    }>;
    private updateRefreshToken;
    private getTokens;
}

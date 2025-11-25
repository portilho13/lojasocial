import { ConflictException, Injectable, UnauthorizedException } from "@nestjs/common";
import { StudentSignUpDto } from "src/dto/student.sign-up.dto";
import { StudentSignInDto } from "src/dto/student.sign-in.dto";
import { StudentRepository } from "src/repository/student.repository";
import * as bcrypt from "bcrypt";
import { StudentResponseDto } from "src/dto/student.response.dto";

@Injectable()
export class StudentService {
    constructor(
        private readonly studentRepository: StudentRepository,
    ) { }

    public async signUp(dto: StudentSignUpDto) {
        const studentByEmail = await this.studentRepository.getStudentByEmail(dto.email)

        if (studentByEmail) throw new ConflictException("User already exists")

        const encryptedPassword = await bcrypt.hash(dto.password, 10);

        const userData = await this.studentRepository.createStudent({
            ...dto,
            password: encryptedPassword
        });

        return new StudentResponseDto(userData);

    }

    public async signIn(dto: StudentSignInDto) {
        const student = await this.studentRepository.getStudentByEmail(dto.email);

        if (!student) throw new UnauthorizedException("Invalid credentials");

        const isPasswordValid = await bcrypt.compare(dto.password, student.password);

        if (!isPasswordValid) throw new UnauthorizedException("Invalid credentials");

        return new StudentResponseDto(student);
    }
}
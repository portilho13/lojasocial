import { ConflictException, Injectable } from "@nestjs/common";
import { StudentSignUpDto } from "src/dto/student.sign-up.dto";
import { StudentRepository } from "src/repository/student.repository";
import * as bcrypt from "bcrypt";
import { StudentResponseDto } from "src/dto/student.response.dto";

@Injectable()
export class StudentService {
    constructor(
        private readonly studentRepository: StudentRepository,
    ) {}

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
}
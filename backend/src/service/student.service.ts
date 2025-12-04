import { ConflictException, Injectable, UnauthorizedException } from "@nestjs/common";
import { JwtService } from "@nestjs/jwt";
import { StudentSignUpDto } from "src/dto/student.sign-up.dto";
import { StudentSignInDto } from "src/dto/student.sign-in.dto";
import { StudentRepository } from "src/repository/student.repository";
import * as bcrypt from "bcrypt";
import { StudentResponseDto } from "src/dto/student.response.dto";

@Injectable()
export class StudentService {
    constructor(
        private readonly studentRepository: StudentRepository,
        private readonly jwtService: JwtService,
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

        const tokens = await this.getTokens(student.id, student.email);
        await this.updateRefreshToken(student.id, tokens.refreshToken);
        return tokens;
    }

    public async logout(userId: string) {
        return this.studentRepository.updateStudent(userId, {
            hashedRefreshToken: null,
        });
    }

    public async refreshTokens(userId: string, refreshToken: string) {
        const student = await this.studentRepository.getStudentById(userId);
        if (!student || !student.hashedRefreshToken) throw new UnauthorizedException("Access Denied");

        const refreshTokenMatches = await bcrypt.compare(refreshToken, student.hashedRefreshToken);
        if (!refreshTokenMatches) throw new UnauthorizedException("Access Denied");

        const tokens = await this.getTokens(student.id, student.email);
        await this.updateRefreshToken(student.id, tokens.refreshToken);
        return tokens;
    }

    private async updateRefreshToken(userId: string, refreshToken: string) {
        const hash = await bcrypt.hash(refreshToken, 10);
        await this.studentRepository.updateStudent(userId, {
            hashedRefreshToken: hash,
        });
    }

    private async getTokens(userId: string, email: string) {
        const [accessToken, refreshToken] = await Promise.all([
            this.jwtService.signAsync(
                {
                    sub: userId,
                    email,
                },
                {
                    secret: process.env.JWT_SECRET || 'secret',
                    expiresIn: '15m',
                },
            ),
            this.jwtService.signAsync(
                {
                    sub: userId,
                    email,
                },
                {
                    secret: process.env.JWT_SECRET || 'secret',
                    expiresIn: '7d',
                },
            ),
        ]);

        return {
            accessToken,
            refreshToken,
        };
    }
}
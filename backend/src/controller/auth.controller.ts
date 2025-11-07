import { Body, Controller, HttpException, HttpStatus, Post, Res, ValidationPipe } from "@nestjs/common";
import type { Response } from "express";
import { StudentSignUpDto } from "src/dto/student.sign-up.dto";
import { StudentService } from "src/service/student.service";

@Controller('auth')
export class AuthController {
    constructor(private readonly authService: StudentService) {}

    @Post('student/sign-up')
    async signUp(@Body(ValidationPipe) body: StudentSignUpDto, @Res() res: Response) {
        try {
            const user = await this.authService.signUp(body);
            return res.status(HttpStatus.CREATED).json(user);
        } catch (e) {
            if (e instanceof HttpException) {
                return res.status(e.getStatus()).json(e.getResponse());
            }
            return res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({ message: 'Internal server error' });
        }
    }
}
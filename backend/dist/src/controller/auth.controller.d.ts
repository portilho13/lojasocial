import type { Response } from "express";
import { StudentSignUpDto } from "src/dto/student.sign-up.dto";
import { StudentService } from "src/service/student.service";
export declare class AuthController {
    private readonly authService;
    constructor(authService: StudentService);
    signUp(body: StudentSignUpDto, res: Response): Promise<Response<any, Record<string, any>>>;
}

import type { Request, Response } from "express";
import { StudentSignUpDto } from "src/dto/auth/student.sign-up.dto";
import { StudentSignInDto } from "src/dto/auth/student.sign-in.dto";
import { StudentService } from "src/service/student.service";
import { UserService } from "src/service/user.service";
import { UserSignUpDto } from "src/dto/auth/user.sign-up.dto";
import { UserSignInDto } from "src/dto/auth/user.sign-in.dto";
export declare class AuthController {
    private readonly authService;
    private readonly userService;
    constructor(authService: StudentService, userService: UserService);
    signUp(body: StudentSignUpDto, res: Response): Promise<Response<any, Record<string, any>>>;
    signIn(body: StudentSignInDto, res: Response): Promise<Response<any, Record<string, any>>>;
    logout(req: Request, res: Response): Promise<Response<any, Record<string, any>>>;
    refreshTokens(req: Request, res: Response): Promise<Response<any, Record<string, any>>>;
    userSignUp(body: UserSignUpDto, res: Response): Promise<Response<any, Record<string, any>>>;
    userSignIn(body: UserSignInDto, res: Response): Promise<Response<any, Record<string, any>>>;
    userLogout(req: Request, res: Response): Promise<Response<any, Record<string, any>>>;
    userRefreshTokens(req: Request, res: Response): Promise<Response<any, Record<string, any>>>;
}

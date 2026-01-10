import { Body, Controller, HttpException, HttpStatus, Post, Res, ValidationPipe, UseGuards, Req, Get } from "@nestjs/common";
import type { Request, Response } from "express";
import { AccessTokenGuard } from "src/common/guards/access-token.guard";
import { RefreshTokenGuard } from "src/common/guards/refresh-token.guard";
import { StudentSignUpDto } from "src/dto/auth/student.sign-up.dto";
import { StudentSignInDto } from "src/dto/auth/student.sign-in.dto";
import { StudentService } from "src/service/student.service";
import { UserService } from "src/service/user.service";
import { UserSignUpDto } from "src/dto/auth/user.sign-up.dto";
import { UserSignInDto } from "src/dto/auth/user.sign-in.dto";

@Controller('api/v1/auth')
export class AuthController {
    constructor(
        private readonly authService: StudentService,
        private readonly userService: UserService,
    ) { }

    // Student Endpoints
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

    @Post('student/sign-in')
    async signIn(@Body(ValidationPipe) body: StudentSignInDto, @Res() res: Response) {
        try {
            const tokens = await this.authService.signIn(body);
            return res.status(HttpStatus.OK).json(tokens);
        } catch (e) {
            if (e instanceof HttpException) {
                return res.status(e.getStatus()).json(e.getResponse());
            }
            return res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({ message: 'Internal server error' });
        }
    }

    @UseGuards(RefreshTokenGuard)
    @Post('logout')
    async logout(@Req() req: Request, @Res() res: Response) {
        try {
            const userId = (req.user as any).sub;
            const refreshToken = (req.user as any).refreshToken;
            await this.authService.logout(userId, refreshToken);
            return res.status(HttpStatus.OK).json({ message: 'Logged out successfully' });
        } catch (e) {
            return res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({ message: 'Internal server error' });
        }
    }

    @UseGuards(RefreshTokenGuard)
    @Post('refresh')
    async refreshTokens(@Req() req: Request, @Res() res: Response) {
        try {
            const userId = (req.user as any).sub;
            const refreshToken = (req.user as any).refreshToken;
            const tokens = await this.authService.refreshTokens(userId, refreshToken);
            return res.status(HttpStatus.OK).json(tokens);
        } catch (e) {
            return res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({ message: 'Internal server error' });
        }
    }

    // User Endpoints
    @Post('user/sign-up')
    async userSignUp(@Body(ValidationPipe) body: UserSignUpDto, @Res() res: Response) {
        try {
            const user = await this.userService.signUp(body);
            return res.status(HttpStatus.CREATED).json(user);
        } catch (e) {
            if (e instanceof HttpException) {
                return res.status(e.getStatus()).json(e.getResponse());
            }
            return res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({ message: 'Internal server error' });
        }
    }

    @Post('user/sign-in')
    async userSignIn(@Body(ValidationPipe) body: UserSignInDto, @Res() res: Response) {
        try {
            const tokens = await this.userService.signIn(body);
            return res.status(HttpStatus.OK).json(tokens);
        } catch (e) {
            if (e instanceof HttpException) {
                return res.status(e.getStatus()).json(e.getResponse());
            }
            return res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({ message: 'Internal server error' });
        }
    }
    
    @UseGuards(RefreshTokenGuard)
    @Post('user/logout')
    async userLogout(@Req() req: Request, @Res() res: Response) {
        try {
            const userId = (req.user as any).sub;
            const refreshToken = (req.user as any).refreshToken;
            await this.userService.logout(userId, refreshToken);
            return res.status(HttpStatus.OK).json({ message: 'Logged out successfully' });
        } catch (e) {
            return res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({ message: 'Internal server error' });
        }
    }

    @UseGuards(RefreshTokenGuard)
    @Post('user/refresh')
    async userRefreshTokens(@Req() req: Request, @Res() res: Response) {
        try {
            const userId = (req.user as any).sub;
            const refreshToken = (req.user as any).refreshToken;
            const tokens = await this.userService.refreshTokens(userId, refreshToken);
            return res.status(HttpStatus.OK).json(tokens);
        } catch (e) {
            return res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({ message: 'Internal server error' });
        }
    }
}
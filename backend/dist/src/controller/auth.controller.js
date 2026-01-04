"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var __param = (this && this.__param) || function (paramIndex, decorator) {
    return function (target, key) { decorator(target, key, paramIndex); }
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.AuthController = void 0;
const common_1 = require("@nestjs/common");
const access_token_guard_1 = require("../common/guards/access-token.guard");
const refresh_token_guard_1 = require("../common/guards/refresh-token.guard");
const student_sign_up_dto_1 = require("../dto/student.sign-up.dto");
const student_sign_in_dto_1 = require("../dto/student.sign-in.dto");
const student_service_1 = require("../service/student.service");
const user_service_1 = require("../service/user.service");
const user_sign_up_dto_1 = require("../dto/user.sign-up.dto");
const user_sign_in_dto_1 = require("../dto/user.sign-in.dto");
let AuthController = class AuthController {
    authService;
    userService;
    constructor(authService, userService) {
        this.authService = authService;
        this.userService = userService;
    }
    async signUp(body, res) {
        try {
            const user = await this.authService.signUp(body);
            return res.status(common_1.HttpStatus.CREATED).json(user);
        }
        catch (e) {
            if (e instanceof common_1.HttpException) {
                return res.status(e.getStatus()).json(e.getResponse());
            }
            return res.status(common_1.HttpStatus.INTERNAL_SERVER_ERROR).json({ message: 'Internal server error' });
        }
    }
    async signIn(body, res) {
        try {
            const tokens = await this.authService.signIn(body);
            return res.status(common_1.HttpStatus.OK).json(tokens);
        }
        catch (e) {
            if (e instanceof common_1.HttpException) {
                return res.status(e.getStatus()).json(e.getResponse());
            }
            return res.status(common_1.HttpStatus.INTERNAL_SERVER_ERROR).json({ message: 'Internal server error' });
        }
    }
    async logout(req, res) {
        try {
            const userId = req.user.sub;
            const refreshToken = req.user.refreshToken;
            await this.authService.logout(userId, refreshToken);
            return res.status(common_1.HttpStatus.OK).json({ message: 'Logged out successfully' });
        }
        catch (e) {
            return res.status(common_1.HttpStatus.INTERNAL_SERVER_ERROR).json({ message: 'Internal server error' });
        }
    }
    async refreshTokens(req, res) {
        try {
            const userId = req.user.sub;
            const refreshToken = req.user.refreshToken;
            const tokens = await this.authService.refreshTokens(userId, refreshToken);
            return res.status(common_1.HttpStatus.OK).json(tokens);
        }
        catch (e) {
            return res.status(common_1.HttpStatus.INTERNAL_SERVER_ERROR).json({ message: 'Internal server error' });
        }
    }
<<<<<<< Updated upstream
=======
<<<<<<< Updated upstream
=======
>>>>>>> Stashed changes
    async userSignUp(body, res) {
        try {
            const user = await this.userService.signUp(body);
            return res.status(common_1.HttpStatus.CREATED).json(user);
        }
        catch (e) {
            if (e instanceof common_1.HttpException) {
                return res.status(e.getStatus()).json(e.getResponse());
            }
            return res.status(common_1.HttpStatus.INTERNAL_SERVER_ERROR).json({ message: 'Internal server error' });
        }
    }
    async userSignIn(body, res) {
        try {
            const tokens = await this.userService.signIn(body);
            return res.status(common_1.HttpStatus.OK).json(tokens);
        }
        catch (e) {
            if (e instanceof common_1.HttpException) {
                return res.status(e.getStatus()).json(e.getResponse());
            }
            return res.status(common_1.HttpStatus.INTERNAL_SERVER_ERROR).json({ message: 'Internal server error' });
        }
    }
    async userLogout(req, res) {
        try {
            const userId = req.user.sub;
<<<<<<< Updated upstream
            await this.userService.logout(userId);
=======
            const refreshToken = req.user.refreshToken;
            await this.userService.logout(userId, refreshToken);
>>>>>>> Stashed changes
            return res.status(common_1.HttpStatus.OK).json({ message: 'Logged out successfully' });
        }
        catch (e) {
            return res.status(common_1.HttpStatus.INTERNAL_SERVER_ERROR).json({ message: 'Internal server error' });
        }
    }
    async userRefreshTokens(req, res) {
        try {
            const userId = req.user.sub;
            const refreshToken = req.user.refreshToken;
            const tokens = await this.userService.refreshTokens(userId, refreshToken);
            return res.status(common_1.HttpStatus.OK).json(tokens);
        }
        catch (e) {
            return res.status(common_1.HttpStatus.INTERNAL_SERVER_ERROR).json({ message: 'Internal server error' });
        }
    }
<<<<<<< Updated upstream
=======
>>>>>>> Stashed changes
>>>>>>> Stashed changes
};
exports.AuthController = AuthController;
__decorate([
    (0, common_1.Post)('student/sign-up'),
    __param(0, (0, common_1.Body)(common_1.ValidationPipe)),
    __param(1, (0, common_1.Res)()),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [student_sign_up_dto_1.StudentSignUpDto, Object]),
    __metadata("design:returntype", Promise)
], AuthController.prototype, "signUp", null);
__decorate([
    (0, common_1.Post)('student/sign-in'),
    __param(0, (0, common_1.Body)(common_1.ValidationPipe)),
    __param(1, (0, common_1.Res)()),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [student_sign_in_dto_1.StudentSignInDto, Object]),
    __metadata("design:returntype", Promise)
], AuthController.prototype, "signIn", null);
__decorate([
    (0, common_1.UseGuards)(refresh_token_guard_1.RefreshTokenGuard),
    (0, common_1.Post)('logout'),
    __param(0, (0, common_1.Req)()),
    __param(1, (0, common_1.Res)()),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [Object, Object]),
    __metadata("design:returntype", Promise)
], AuthController.prototype, "logout", null);
__decorate([
    (0, common_1.UseGuards)(refresh_token_guard_1.RefreshTokenGuard),
    (0, common_1.Post)('refresh'),
    __param(0, (0, common_1.Req)()),
    __param(1, (0, common_1.Res)()),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [Object, Object]),
    __metadata("design:returntype", Promise)
], AuthController.prototype, "refreshTokens", null);
__decorate([
    (0, common_1.Post)('user/sign-up'),
    __param(0, (0, common_1.Body)(common_1.ValidationPipe)),
    __param(1, (0, common_1.Res)()),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [user_sign_up_dto_1.UserSignUpDto, Object]),
    __metadata("design:returntype", Promise)
], AuthController.prototype, "userSignUp", null);
__decorate([
    (0, common_1.Post)('user/sign-in'),
    __param(0, (0, common_1.Body)(common_1.ValidationPipe)),
    __param(1, (0, common_1.Res)()),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [user_sign_in_dto_1.UserSignInDto, Object]),
    __metadata("design:returntype", Promise)
], AuthController.prototype, "userSignIn", null);
__decorate([
    (0, common_1.UseGuards)(access_token_guard_1.AccessTokenGuard),
    (0, common_1.Post)('user/logout'),
    __param(0, (0, common_1.Req)()),
    __param(1, (0, common_1.Res)()),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [Object, Object]),
    __metadata("design:returntype", Promise)
], AuthController.prototype, "userLogout", null);
__decorate([
    (0, common_1.UseGuards)(refresh_token_guard_1.RefreshTokenGuard),
    (0, common_1.Post)('user/refresh'),
    __param(0, (0, common_1.Req)()),
    __param(1, (0, common_1.Res)()),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [Object, Object]),
    __metadata("design:returntype", Promise)
], AuthController.prototype, "userRefreshTokens", null);
exports.AuthController = AuthController = __decorate([
    (0, common_1.Controller)('api/v1/auth'),
    __metadata("design:paramtypes", [student_service_1.StudentService,
        user_service_1.UserService])
], AuthController);
//# sourceMappingURL=auth.controller.js.map
import { ConflictException, Injectable, UnauthorizedException } from "@nestjs/common";
import { JwtService } from "@nestjs/jwt";
import { UserSignUpDto } from "src/dto/user.sign-up.dto";
import { UserSignInDto } from "src/dto/user.sign-in.dto";
import { UserRepository } from "src/repository/user.repository";
import * as bcrypt from "bcrypt";
<<<<<<< Updated upstream
=======
import { UserRole } from "@prisma/client";
>>>>>>> Stashed changes

@Injectable()
export class UserService {
    constructor(
        private readonly userRepository: UserRepository,
        private readonly jwtService: JwtService,
    ) { }

    public async signUp(dto: UserSignUpDto) {
        const userByEmail = await this.userRepository.getUserByEmail(dto.email)

        if (userByEmail) throw new ConflictException("User already exists")

        const encryptedPassword = await bcrypt.hash(dto.password, 10);

        const userData = await this.userRepository.createUser({
            ...dto,
<<<<<<< Updated upstream
=======
            userType: dto.userType as UserRole,
>>>>>>> Stashed changes
            password: encryptedPassword
        });

        const { password, hashedRefreshToken, ...result } = userData;
        return result;
    }

    public async signIn(dto: UserSignInDto) {
        const user = await this.userRepository.getUserByEmail(dto.email);

        if (!user) throw new UnauthorizedException("Invalid credentials");

        const isPasswordValid = await bcrypt.compare(dto.password, user.password);

        if (!isPasswordValid) throw new UnauthorizedException("Invalid credentials");

        const tokens = await this.getTokens(user.id, user.email);
        await this.updateRefreshToken(user.id, tokens.refreshToken);
        return tokens;
    }

    public async logout(userId: string, refreshToken: string) {
        // return this.userRepository.updateUser(userId, {
        //     hashedRefreshToken: null,
        // });
        const user = await this.userRepository.getUserById(userId);
<<<<<<< Updated upstream
                if (!user || !user.hashedRefreshToken) throw new UnauthorizedException("Access Denied");
        
                const refreshTokenMatches = await bcrypt.compare(refreshToken, user.hashedRefreshToken);
                if (!refreshTokenMatches) throw new UnauthorizedException("Access Denied");
        
                await this.userRepository.updateUser(userId, {
                    hashedRefreshToken: null,
                });
=======
        if (!user || !user.hashedRefreshToken) throw new UnauthorizedException("Access Denied");

        const refreshTokenMatches = await bcrypt.compare(refreshToken, user.hashedRefreshToken);
        if (!refreshTokenMatches) throw new UnauthorizedException("Access Denied");

        await this.userRepository.updateUser(userId, {
            hashedRefreshToken: null,
        });
>>>>>>> Stashed changes
    }

    public async refreshTokens(userId: string, refreshToken: string) {
        const user = await this.userRepository.getUserById(userId);
        if (!user || !user.hashedRefreshToken) throw new UnauthorizedException("Access Denied");

        const refreshTokenMatches = await bcrypt.compare(refreshToken, user.hashedRefreshToken);
        if (!refreshTokenMatches) throw new UnauthorizedException("Access Denied");

        const tokens = await this.getTokens(user.id, user.email);
        await this.updateRefreshToken(user.id, tokens.refreshToken);
        return tokens;
    }

    private async updateRefreshToken(userId: string, refreshToken: string) {
        const hash = await bcrypt.hash(refreshToken, 10);
        await this.userRepository.updateUser(userId, {
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

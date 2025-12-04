import { Module } from '@nestjs/common';
import { AuthController } from './controller/auth.controller';
import { PrismaModule } from 'prisma/prisma.module';
import { StudentRepository } from './repository/student.repository';
import { StudentService } from './service/student.service';
import { JwtModule } from '@nestjs/jwt';
import { AccessTokenStrategy } from './auth/strategies/access-token.strategy';
import { RefreshTokenStrategy } from './auth/strategies/refresh-token.strategy';

const repositorioes = [
  StudentRepository
]

const services = [
  StudentService,
  AccessTokenStrategy,
  RefreshTokenStrategy,
]

@Module({
  imports: [
    PrismaModule,
    JwtModule.register({
      global: true,
      secret: process.env.JWT_SECRET || 'secret',
      signOptions: { expiresIn: '15m' },
    }),
  ],
  controllers: [AuthController],
  providers: [...repositorioes, ...services],
})
export class AppModule { }

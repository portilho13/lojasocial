import { Module } from '@nestjs/common';
import { AuthController } from './controller/auth.controller';
import { PrismaModule } from 'prisma/prisma.module';
import { StudentRepository } from './repository/student.repository';
import { StudentService } from './service/student.service';
import { UserService } from './service/user.service';
import { UserRepository } from './repository/user.repository';
import { JwtModule } from '@nestjs/jwt';
import { AccessTokenStrategy } from './auth/strategies/access-token.strategy';
import { RefreshTokenStrategy } from './auth/strategies/refresh-token.strategy';
import { InventoryModule } from './inventory.module';
import { SupportRequestModule } from './support-request.module';


const repositorioes = [
  StudentRepository,
  UserRepository,
]

const services = [
  StudentService,
  UserService,
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
    }), PrismaModule, InventoryModule, SupportRequestModule
  ],
  controllers: [AuthController],
  providers: [...repositorioes, ...services],
})
export class AppModule { }

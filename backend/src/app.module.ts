import { Module } from '@nestjs/common';
import { AuthController } from './controller/auth.controller';
import { PrismaModule } from 'prisma/prisma.module';
import { JwtModule } from '@nestjs/jwt';
import { InventoryModule } from './inventory.module';
import { SupportRequestModule } from './support-request.module';
import { BeneficiaryModule } from './beneficiary.module';

import { StudentRepository } from './repository/student.repository';
import { UserRepository } from './repository/user.repository';

import { StudentService } from './service/student.service';
import { UserService } from './service/user.service';
import { AccessTokenStrategy } from './auth/strategies/access-token.strategy';
import { RefreshTokenStrategy } from './auth/strategies/refresh-token.strategy';

const repositories = [
  StudentRepository,
  UserRepository,
];

const services = [
  StudentService,
  UserService,
  AccessTokenStrategy,
  RefreshTokenStrategy,
];

@Module({
  imports: [
    PrismaModule,
    JwtModule.register({
      global: true,
      secret: process.env.JWT_SECRET || 'secret',
      signOptions: { expiresIn: '15m' },
    }),
    InventoryModule,
    SupportRequestModule,
    BeneficiaryModule,
  ],
  controllers: [AuthController],
  providers: [...repositories, ...services],
})
export class AppModule {}

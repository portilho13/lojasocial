import { Module } from '@nestjs/common';
import { JwtModule } from '@nestjs/jwt';
import { PrismaModule } from 'prisma/prisma.module';
import { InventoryModule } from './inventory.module';
import { SupportRequestModule } from './support-request.module';
import { BeneficiaryModule } from './beneficiary.module';
import { DonationModule } from './donation.module';
import { CampaignModule } from './campaign.module';
import { AuthController } from './controller/auth.controller';
import { AppointmentController } from './controller/appointment.controller';
import { StudentRepository } from './repository/student.repository';
import { UserRepository } from './repository/user.repository';
import { AppointmentsRepository } from './repository/appointments.repository';
import { StudentService } from './service/student.service';
import { UserService } from './service/user.service';
import { EmailService } from './service/email.service';
import { AppointmentService } from './service/appointment.service';
import { AccessTokenStrategy } from './auth/strategies/access-token.strategy';
import { RefreshTokenStrategy } from './auth/strategies/refresh-token.strategy';
import { TestController } from './controller/test.controller';
import { ConfigModule } from '@nestjs/config';

const repositories = [
  StudentRepository,
  UserRepository,
  AppointmentsRepository,
];


const services = [
  StudentService,
  UserService,
  AccessTokenStrategy,
  RefreshTokenStrategy,
  EmailService,
  AppointmentService,
];

const controllers = [
  AuthController,
  AppointmentController,
  TestController
];

@Module({
  imports: [
    ConfigModule.forRoot({
      isGlobal: true,
    }),
    PrismaModule,
    JwtModule.register({
      global: true,
      secret: process.env.JWT_SECRET || 'secret',
      signOptions: { expiresIn: '15m' },
    }),
    InventoryModule,
    SupportRequestModule,
    DonationModule,
    BeneficiaryModule,
    CampaignModule,
  ],
  controllers: [...controllers],
  providers: [...repositories, ...services],
})
export class AppModule {}

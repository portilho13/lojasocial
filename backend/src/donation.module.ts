import { Module } from '@nestjs/common';
import { JwtModule } from '@nestjs/jwt';
import { PrismaService } from 'prisma/prisma.service';
import { DonationController } from './controller/donation.controller';
import { DonationService } from './service/donation.service';
import { DonationRepository } from './repository/donation.repository';
import { AccessTokenStrategy } from 'src/auth/strategies/access-token.strategy';
import { RefreshTokenStrategy } from 'src/auth/strategies/refresh-token.strategy';

@Module({
  imports: [JwtModule.register({})],
  controllers: [DonationController],
  providers: [
    DonationService,
    DonationRepository,
    PrismaService,
    AccessTokenStrategy,
    RefreshTokenStrategy,
  ],
  exports: [DonationService],
})
export class DonationModule {}
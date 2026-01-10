import { Module } from '@nestjs/common';
import { JwtModule } from '@nestjs/jwt';
import { PrismaService } from 'prisma/prisma.service';
import { CampaignController } from './controller/campaign.controller';
import { CampaignService } from './service/campaign.service';
import { CampaignRepository } from './repository/campaign.repository';
import { AccessTokenStrategy } from 'src/auth/strategies/access-token.strategy';
import { RefreshTokenStrategy } from 'src/auth/strategies/refresh-token.strategy';

@Module({
  imports: [JwtModule.register({})],
  controllers: [CampaignController],
  providers: [
    CampaignService,
    CampaignRepository,
    PrismaService,
    AccessTokenStrategy,
    RefreshTokenStrategy,
  ],
  exports: [CampaignService],
})
export class CampaignModule {}
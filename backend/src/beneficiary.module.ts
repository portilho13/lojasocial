import { Module } from '@nestjs/common';
import { BeneficiaryController } from './controller/beneficiary.controller';
import { BeneficiaryService } from './service/beneficiary.service';
import { BeneficiaryRepository } from './repository/beneficiary.repository';

@Module({
  controllers: [BeneficiaryController],
  providers: [BeneficiaryService, BeneficiaryRepository],
  exports: [BeneficiaryService, BeneficiaryRepository],
})
export class BeneficiaryModule {}

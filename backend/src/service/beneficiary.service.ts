import { Injectable, NotFoundException } from '@nestjs/common';
import { BeneficiaryRepository } from 'src/repository/beneficiary.repository';
import { CreateBeneficiaryDto } from 'src/dto/beneficiary/create-beneficiary.dto';
import { UpdateBeneficiaryDto } from 'src/dto/beneficiary/update-beneficiary.dto';
import * as bcrypt from 'bcrypt';

@Injectable()
export class BeneficiaryService {
  constructor(private readonly repository: BeneficiaryRepository) {}

  async create(dto: CreateBeneficiaryDto) {
    const hashedPassword = await bcrypt.hash(dto.password, 10);
    const created = await this.repository.create({ ...dto, password: hashedPassword });
    const beneficiary = await this.repository.findById(created.id);
    if (!beneficiary) throw new NotFoundException('Beneficiary not found');
    return beneficiary;
  }

  async findAll(skip?: number, take?: number) {
    return this.repository.findAll(skip ?? 0, take ?? 50);
  }

  async findOne(id: string) {
    const beneficiary = await this.repository.findById(id);
    if (!beneficiary) throw new NotFoundException('Beneficiary not found');
    return beneficiary;
  }

  async update(id: string, dto: UpdateBeneficiaryDto) {
    const beneficiary = await this.repository.findById(id);
    if (!beneficiary) throw new NotFoundException('Beneficiary not found');

    if (dto.password) {
      dto.password = await bcrypt.hash(dto.password, 10);
    }

    await this.repository.update(id, dto);
    return this.findOne(id);
  }

  async delete(id: string) {
    const beneficiary = await this.repository.findById(id);
    if (!beneficiary) throw new NotFoundException('Beneficiary not found');
    return this.repository.delete(id);
  }
}

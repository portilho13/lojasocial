import { Injectable, NotFoundException } from '@nestjs/common';
import { BeneficiaryRepository } from 'src/repository/beneficiary.repository';
import { CreateBeneficiaryDto } from 'src/dto/beneficiary/create-beneficiary.dto';
import { UpdateBeneficiaryDto } from 'src/dto/beneficiary/update-beneficiary.dto';
import * as bcrypt from 'bcrypt';

@Injectable()
export class BeneficiaryService {
  constructor(private readonly repository: BeneficiaryRepository) {}

  // Create a new beneficiary and hash their password
  public async create(dto: CreateBeneficiaryDto) {
    const hashedPassword = await bcrypt.hash(dto.password, 10);

    const created = await this.repository.create({ ...dto, password: hashedPassword });
    const beneficiary = await this.repository.findById(created.id);

    if (!beneficiary) throw new NotFoundException('Beneficiary not found');

    return beneficiary;
  }

  // List all beneficiaries with optional pagination
  public async findAll(skip?: number, take?: number) {
    return this.repository.findAll(skip ?? 0, take ?? 50);
  }

  // Find a beneficiary by ID
  public async findOne(id: string) {
    const beneficiary = await this.repository.findById(id);
    if (!beneficiary) throw new NotFoundException('Beneficiary not found');
    return beneficiary;
  }

  // Update a beneficiary by ID
  public async update(id: string, dto: UpdateBeneficiaryDto) {
    const beneficiary = await this.repository.findById(id);
    if (!beneficiary) throw new NotFoundException('Beneficiary not found');

    if (dto.password) {
      dto.password = await bcrypt.hash(dto.password, 10);
    }

    await this.repository.update(id, dto);
    return this.findOne(id);
  }

  // Delete a beneficiary by ID
  public async delete(id: string) {
    const beneficiary = await this.repository.findById(id);
    if (!beneficiary) throw new NotFoundException('Beneficiary not found');
    return this.repository.delete(id);
  }
}

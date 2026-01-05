import { IsString, IsOptional, IsInt, IsEmail } from 'class-validator';

export class UpdateBeneficiaryDto {
  @IsOptional()
  @IsString()
  name?: string;

  @IsOptional()
  @IsString()
  studentNumber?: string;

  @IsOptional()
  @IsString()
  course?: string;

  @IsOptional()
  @IsInt()
  academicYear?: number;

  @IsOptional()
  @IsString()
  socialSecurityNumber?: string;

  @IsOptional()
  @IsString()
  contact?: string;

  @IsOptional()
  @IsEmail()
  email?: string;

  @IsOptional()
  @IsString()
  password?: string;
}

import { IsString, IsOptional, IsInt, IsEmail } from 'class-validator';

export class CreateBeneficiaryDto {
  @IsString()
  name: string;

  @IsString()
  studentNumber: string;

  @IsString()
  course: string;

  @IsInt()
  academicYear: number;

  @IsOptional()
  @IsString()
  socialSecurityNumber?: string;

  @IsOptional()
  @IsString()
  contact?: string;

  @IsEmail()
  email: string;

  @IsString()
  password: string;
}

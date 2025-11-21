import { IsNotEmpty, IsNumber, IsString, Matches, MinLength } from "class-validator";

export class  StudentSignUpDto {
    @IsString()
    @IsNotEmpty()
    name: string;

    @IsString()
    @IsNotEmpty()
    studentNumber: string;

    @IsString()
    @IsNotEmpty()
    course: string;

    @IsNumber()
    @IsNotEmpty()
    academicYear: number;

    @IsString()
    @IsNotEmpty()
    socialSecurityNumber: string;

    @IsString()
    @IsNotEmpty()
    contact: string;

    @IsString()
    @IsNotEmpty()
    email: string;

    @IsString()
    @MinLength(8)
    @Matches(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)/, {
        message: 'Password must contain at least one lowercase letter, one uppercase letter, and one number',
    })
    password: string;

}
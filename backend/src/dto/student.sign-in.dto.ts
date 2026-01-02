import { IsNotEmpty, IsString } from "class-validator";

export class StudentSignInDto {
    @IsString()
    @IsNotEmpty()
    email: string;

    @IsString()
    @IsNotEmpty()
    password: string;
}

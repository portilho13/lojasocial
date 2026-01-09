import { IsNotEmpty } from "class-validator";

export class AppointmentRequestDto {
    @IsNotEmpty()
    date: Date

    @IsNotEmpty()
    studentId: string
}
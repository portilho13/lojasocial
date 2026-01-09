import { Appointment } from "@prisma/client";
import { StudentResponseDto } from "../auth/student.response.dto";

export class AppointmentResponseDto {
  id: Appointment["id"];
  date: Appointment["date"];
  notificationSent: Appointment["notificationSent"];
  student: StudentResponseDto;

  constructor(appointment: Appointment & { student: any }) {
    this.id = appointment.id;
    this.date = appointment.date;
    this.notificationSent = appointment.notificationSent;
    this.student = new StudentResponseDto(appointment.student);
  }
}

import { AppointmentsRepository } from "src/repository/appointments.repository";
import { EmailService } from "./email.service";
import { AppointmentRequestDto } from "src/dto/appointments/appointment.request.dto";
import { StudentRepository } from "src/repository/student.repository";
import { Injectable, NotFoundException, ServiceUnavailableException } from "@nestjs/common";
import { AppointmentResponseDto } from "src/dto/appointments/appointment.response.dto";

@Injectable()
export class AppointmentService {
    constructor(
        private readonly appointmentRepository: AppointmentsRepository,
        private readonly emailService: EmailService,
        private readonly studentRepository: StudentRepository
     ) {}

    public async createAppointment(dto: AppointmentRequestDto) {

        const student = await this.studentRepository.getStudentById(dto.studentId)

        if (!student) {
            throw new NotFoundException("Student Not Found")
        }

        const newAppointment = await this.appointmentRepository.createAppointment(
            dto.date,
            dto.studentId
        )


        const sendEmail = await this.emailService.sendConfirmationEmail(student.email, student.name)
        if (!sendEmail) {
            throw new ServiceUnavailableException(
                "Unavailable to send confirmation email"
            )
        }

        return new AppointmentResponseDto(newAppointment)
    }

    public async fetchAllAppointments() {
        const appointments = await this.appointmentRepository.getAllAppointments()

        return appointments.map(appointment => new AppointmentResponseDto(appointment))
    }

    public async sendConfirmationAppointmentEmail(id: string) {
        const appointment = await this.appointmentRepository.getAppointmentById(id)

        if (!appointment) {
            throw new NotFoundException("Appointment Not Found")
        }

        const student = await this.studentRepository.getStudentById(appointment.studentId)

        if (!student) {
            throw new NotFoundException("Student Not Found")
        }

        await this.appointmentRepository.markNotificationAsSent(id);

        const sendEmail = await this.emailService.sendRememberEmail(student.email, student.name)
        if (!sendEmail) {
            throw new ServiceUnavailableException(
                "Unavailable to send confirmation email"
            )
        }

    }

}
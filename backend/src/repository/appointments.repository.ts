import { Appointment, Prisma } from "@prisma/client";
import { PrismaService } from "prisma/prisma.service";

export class AppointmentsRepository {
    constructor (private readonly prisma: PrismaService) {}
    
    public async getAppointmentById(id: Appointment["id"]) {
        return this.prisma.appointment.findUnique({
            where: {id}
        })
    }

public async createAppointment(date: Date, studentId: string) {
    return this.prisma.appointment.create({
      data: {
        date,
        notificationSent: false,
        student: {
          connect: {
            id: studentId
          }
        }
      },
      include: {
        student: true
      }
    })
  }
    public async getAllAppointments() {
    return this.prisma.appointment.findMany({
        include: {
        student: true
        }
    })
    }


    public async markNotificationAsSent(appointmentId: string) {
        return this.prisma.appointment.update({
            where: { id: appointmentId },
            data: { notificationSent: true },
            include: { student: true }
        })
    }

}
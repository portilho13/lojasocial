import {
  Controller,
  Post,
  Get,
  Param,
  Body,
  Res,
  HttpStatus,
  ValidationPipe,
  HttpException,
} from '@nestjs/common';
import type { Response } from 'express';
import { AppointmentService } from 'src/service/appointment.service';
import { AppointmentRequestDto } from 'src/dto/appointments/appointment.request.dto';

@Controller('api/v1/appointments')
export class AppointmentController {
  constructor(private readonly appointmentService: AppointmentService) {}

  // Create a new appointment
  // Route: POST /api/v1/appointments
  @Post()
  async createAppointment(
    @Body(ValidationPipe) dto: AppointmentRequestDto,
    @Res() res: Response,
  ) {
    try {
      const appointment = await this.appointmentService.createAppointment(dto);
      return res.status(HttpStatus.CREATED).json(appointment);
    } catch (e) {
      if (e instanceof HttpException) {
        return res.status(e.getStatus()).json(e.getResponse());
      }
      return res
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .json({ message: 'Internal server error' });
    }
  }

  // List all appointments
  // Route: GET /api/v1/appointments
  @Get()
  async getAllAppointments(@Res() res: Response) {
    try {
      const appointments = await this.appointmentService.fetchAllAppointments();
      return res.status(HttpStatus.OK).json(appointments);
    } catch (e) {
      if (e instanceof HttpException) {
        return res.status(e.getStatus()).json(e.getResponse());
      }
      return res
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .json({ message: 'Internal server error' });
    }
  }

  // Send confirmation email for a specific appointment
  // Route: POST /api/v1/appointments/:id/send-confirmation
  @Post(':id/send-confirmation')
  async sendConfirmationEmail(
    @Param('id') id: string,
    @Res() res: Response,
  ) {
    try {
      await this.appointmentService.sendConfirmationAppointmentEmail(id);
      return res
        .status(HttpStatus.OK)
        .json({ message: 'Confirmation email sent successfully' });
    } catch (e) {
      if (e instanceof HttpException) {
        return res.status(e.getStatus()).json(e.getResponse());
      }
      return res
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .json({ message: 'Internal server error' });
    }
  }
}

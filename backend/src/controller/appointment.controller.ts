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
  UseGuards,
} from '@nestjs/common';
import type { Response } from 'express';
import { AppointmentService } from 'src/service/appointment.service';
import { AppointmentRequestDto } from 'src/dto/appointments/appointment.request.dto';
import { UserGuard } from 'src/common/guards/user.guard';

@Controller('api/v1/appointments')
export class AppointmentController {
  constructor(private readonly appointmentService: AppointmentService) { }

  @Post()
  @UseGuards(UserGuard)
  async createAppointment(
    @Body(ValidationPipe) dto: AppointmentRequestDto,
    @Res() res: Response,
  ) {
    try {
      const appointment = await this.appointmentService.createAppointment(dto);
      return res.status(HttpStatus.CREATED).json(appointment);
    } catch (e) {
      console.log(e)
      if (e instanceof HttpException) {
        return res.status(e.getStatus()).json(e.getResponse());
      }
      return res
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .json({ message: 'Internal server error' });
    }
  }


  @Get()
  @UseGuards(UserGuard)
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
  @UseGuards(UserGuard)
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

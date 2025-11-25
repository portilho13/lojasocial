import { Module } from '@nestjs/common';
import { AuthController } from './controller/auth.controller';
import { PrismaModule } from 'prisma/prisma.module';
import { StudentRepository } from './repository/student.repository';
import { StudentService } from './service/student.service';
import { InventoryModule } from './inventory.module';


const repositorioes = [
  StudentRepository
]

const services = [
  StudentService
]

@Module({
  imports: [PrismaModule, InventoryModule],
  controllers: [AuthController],
  providers: [...repositorioes, ...services],
})
export class AppModule {}

import { Module } from '@nestjs/common';
import { InventoryController } from './controller/inventory.controller';
import { InventoryService } from './service/inventory.service';
import { InventoryRepository } from './repository/inventory.repository';
import { PrismaModule } from 'prisma/prisma.module'; 

@Module({
  imports: [PrismaModule],
  controllers: [InventoryController],
  providers: [InventoryService, InventoryRepository],
})
export class InventoryModule {}
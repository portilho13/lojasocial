import {
  Body,
  Controller,
  Get,
  Post,
  Patch,
  Delete,
  Param,
  ValidationPipe,
  Query,
  Res,
  HttpStatus,
  HttpException,
} from '@nestjs/common';
import type { Response } from 'express';
import { InventoryService } from 'src/service/inventory.service';
import { CreateProductDto } from 'src/dto/inventory/create-product.dto';
import { CreateStockDto } from 'src/dto/inventory/create-stock.dto';
import { UpdateStockDto } from 'src/dto/inventory/update-stock.dto';

// Add auth later
@Controller('api/v1/inventory')
export class InventoryController {
  constructor(private readonly inventoryService: InventoryService) {}

  //Register a new type of product
  //Route: POST /api/v1/inventory/products

  @Post('products')
  async createProduct(
    @Body(ValidationPipe) body: CreateProductDto,
    @Res() res: Response,
  ) {
    try {
      const product = await this.inventoryService.createProduct(body);
      return res.status(HttpStatus.CREATED).json(product);
    } catch (e) {
      if (e instanceof HttpException) {
        return res.status(e.getStatus()).json(e.getResponse());
      }
      return res
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .json({ message: 'Internal server error' });
    }
  }

  //List all type of products
  //Route: GET /api/v1/inventory/products

  @Get('products')
  async getAllProducts(
    @Res() res: Response,
    @Query('skip') skip?: string,
    @Query('take') take?: string,
  ) {
    try {
      const products = await this.inventoryService.getAllProducts(
        skip ? parseInt(skip) : undefined,
        take ? parseInt(take) : undefined,
      );
      return res.status(HttpStatus.OK).json(products);
    } catch (e) {
      if (e instanceof HttpException) {
        return res.status(e.getStatus()).json(e.getResponse());
      }
      return res
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .json({ message: 'Internal server error' });
    }
  }

  //Register stock entry
  //Route: POST /api/v1/inventory/stocks

  @Post('stocks')
  async createStock(
    @Body(ValidationPipe) body: CreateStockDto,
    @Res() res: Response,
  ) {
    try {
      const stock = await this.inventoryService.createStock(body);
      return res.status(HttpStatus.CREATED).json(stock);
    } catch (e) {
      if (e instanceof HttpException) {
        return res.status(e.getStatus()).json(e.getResponse());
      }
      return res
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .json({ message: 'Internal server error' });
    }
  }

  //View stock history and expiry dates
  //Route: GET /api/v1/inventory/stocks

  @Get('stocks')
  async getAllStockRecords(
    @Res() res: Response,
    @Query('skip') skip?: string,
    @Query('take') take?: string,
  ) {
    try {
      const stocks = await this.inventoryService.getAllStockRecords(
        skip ? parseInt(skip) : undefined,
        take ? parseInt(take) : undefined,
      );
      return res.status(HttpStatus.OK).json(stocks);
    } catch (e) {
      if (e instanceof HttpException) {
        return res.status(e.getStatus()).json(e.getResponse());
      }
      return res
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .json({ message: 'Internal server error' });
    }
  }

  //Update stock entry
  //Route: PATCH /api/v1/inventory/stocks/:id

  @Patch('stocks/:id')
  async updateStock(
    @Param('id') id: string,
    @Body(ValidationPipe) body: UpdateStockDto,
    @Res() res: Response,
  ) {
    try {
      const stock = await this.inventoryService.updateStock(id, body);
      return res.status(HttpStatus.OK).json(stock);
    } catch (e) {
      if (e instanceof HttpException) {
        return res.status(e.getStatus()).json(e.getResponse());
      }
      return res
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .json({ message: 'Internal server error' });
    }
  }

  //Delete stock entry
  //Route: DELETE /api/v1/inventory/stocks/:id
  @Delete('stocks/:id')
  async deleteStock(@Param('id') id: string, @Res() res: Response) {
    try {
      await this.inventoryService.deleteStock(id);
      return res.status(HttpStatus.NO_CONTENT).send();
    } catch (e) {
      if (e instanceof HttpException) {
        return res.status(e.getStatus()).json(e.getResponse());
      }
      return res
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .json({ message: 'Internal server error' });
    }
  }

  //Get stock entries that are about to expire within a given number of days (default 30)
  //Route: GET /api/v1/inventory/stocks/expiring?days=30
  @Get('stocks/expiring')
  async getExpiringStock(@Query('days') days: string, @Res() res: Response) {
    try {
      const threshold = days ? parseInt(days) : 30;
      const stocks = await this.inventoryService.getExpiringStock(threshold);
      return res.status(HttpStatus.OK).json(stocks);
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

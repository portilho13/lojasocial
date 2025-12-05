import { Body, Controller, Get, Post, ValidationPipe, Query, Res, HttpStatus, HttpException } from '@nestjs/common';
import type { Response } from 'express';
import { InventoryService } from 'src/service/inventory.service';
import { CreateProductDto } from 'src/dto/inventory/create-product.dto';
import { CreateStockDto } from 'src/dto/inventory/create-stock.dto';

// Add auth later
@Controller('api/v1/inventory')
export class InventoryController {
    constructor(private readonly inventoryService: InventoryService) {}


     //Register a new type of product 
     //Route: POST /api/v1/inventory/products

    @Post('products')
    async createProduct(@Body(ValidationPipe) body: CreateProductDto, @Res() res: Response) {
        try {
            const product = await this.inventoryService.createProduct(body);
            return res.status(HttpStatus.CREATED).json(product);
        } catch (e) {
            if (e instanceof HttpException) {
                return res.status(e.getStatus()).json(e.getResponse());
            }
            return res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({ message: 'Internal server error' });
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
            return res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({ message: 'Internal server error' });
        }
    }



     //Register stock entry 
     //Route: POST /api/v1/inventory/stocks

    @Post('stocks')
    async createStock(@Body(ValidationPipe) body: CreateStockDto, @Res() res: Response) {
        try {
            const stock = await this.inventoryService.createStock(body);
            return res.status(HttpStatus.CREATED).json(stock);
        } catch (e) {
            if (e instanceof HttpException) {
                return res.status(e.getStatus()).json(e.getResponse());
            }
            return res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({ message: 'Internal server error' });
        }
    }


     //View stock history and expiry dates
     //Route: GET /api/v1/inventory/stocks

    @Get('stocks')
    async getAllStockRecords(
        @Res() res: Response,
        @Query('skip') skip?: string,
        @Query('take') take?: string
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
            return res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({ message: 'Internal server error' });
        }
    }
}
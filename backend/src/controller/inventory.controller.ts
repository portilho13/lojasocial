import { Body, Controller, Get, Post, ValidationPipe, UseGuards, Query } from '@nestjs/common';
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
    async createProduct(@Body(ValidationPipe) body: CreateProductDto) {
        return this.inventoryService.createProduct(body);
    }


     //List all type of products
     //Route: GET /api/v1/inventory/products

    @Get('products')
    async getAllProducts(
    @Query('skip') skip?: string,
    @Query('take') take?: string,
    ) {
    return this.inventoryService.getAllProducts(
        skip ? parseInt(skip) : undefined,
        take ? parseInt(take) : undefined,
    );
    }



     //Register stock entry 
     //Route: POST /api/v1/inventory/stocks

    @Post('stocks')
    async createStock(@Body(ValidationPipe) body: CreateStockDto) {
        return this.inventoryService.createStock(body);
    }


     //View stock history and expiry dates
     //Route: GET /api/v1/inventory/stocks

    @Get('stocks')
    async getAllStockRecords(
    @Query('skip') skip?: string,
    @Query('take') take?: string,
    ) {
    return this.inventoryService.getAllStockRecords(
        skip ? parseInt(skip) : undefined,
        take ? parseInt(take) : undefined,
    );
    }
}
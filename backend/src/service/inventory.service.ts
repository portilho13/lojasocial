import { Prisma } from "@prisma/client";
import { ConflictException, Injectable } from '@nestjs/common';
import { InventoryRepository } from '../repository/inventory.repository';
import { CreateProductDto } from '../dto/inventory/create-product.dto';
import { CreateStockDto } from '../dto/inventory/create-stock.dto';
import { ProductResponseDto } from '../dto/inventory/product-response.dto';
import { StockResponseDto } from '../dto/inventory/stock-response.dto';
import { UpdateStockDto } from "../dto/inventory/update-stock.dto";

@Injectable()
export class InventoryService {
    constructor(
        private readonly inventoryRepository: InventoryRepository,
    ) {}


    //Register new product type
    public async createProduct(dto: CreateProductDto) {
        const productByName = await this.inventoryRepository.getProductByName(dto.name);

        if (productByName) throw new ConflictException("There is already a product with this name.");

        const { productTypeId, ...productData } = dto;

        const newProduct = await this.inventoryRepository.createProduct({
            ...productData,
            productType: {
                connect: { id: productTypeId }, 
            },
        });

        return new ProductResponseDto(newProduct);
    }


    //Register new stock entry
    public async createStock(dto: CreateStockDto) {
        const { 
            productId, 
            userId,       
            movementDate, 
            expiryDate, 
            ...rest       
        } = dto;

        const prismaData: Prisma.StockCreateInput = {
            ...rest, 
            movementDate: new Date(movementDate),
            expiryDate: new Date(expiryDate),
            product: {
                connect: { id: productId },
            },
        };
        
        if (userId) {
            prismaData.user = { connect: { id: userId } }; 
        }

        const newStock = await this.inventoryRepository.createStock(prismaData);

        return new StockResponseDto(newStock);
    }


    //List all products
    public async getAllProducts(skip?: number, take?: number) {
        const products = await this.inventoryRepository.findAllProducts(skip, take);

        return products.map(p => new ProductResponseDto(p));
    }


    //View stock history and expiry dates
    public async getAllStockRecords(skip?: number, take?: number) {
        const stocks = await this.inventoryRepository.findAllStock(skip, take);
        return stocks.map(s => new StockResponseDto(s));
    }

    //Update stock entry
    public async updateStock(id: string, dto: UpdateStockDto) {
    const updatedStock = await this.inventoryRepository.updateStock(id, dto);
    return new StockResponseDto(updatedStock);
}
}
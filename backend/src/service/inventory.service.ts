import { Prisma } from "@prisma/client";
import { BadRequestException, Injectable } from '@nestjs/common';
import { InventoryRepository } from '../repository/inventory.repository';
import { CreateProductDto } from '../dto/inventory/create-product.dto';
import { CreateStockDto } from '../dto/inventory/create-stock.dto';

@Injectable()
export class InventoryService {
    constructor(
        private readonly inventoryRepository: InventoryRepository,
    ) {}


    //Register new product type
    public async createProduct(data: CreateProductDto) {

        const { productTypeId, name } = data;
        
        const prismaData: Prisma.ProductCreateInput = {
            name,
            productType: {
                connect: { id: productTypeId }, 
            },
        };

        try {
            return await this.inventoryRepository.createProduct(prismaData);
        } catch (error) {
            if (error.code === 'P2002') { 
                throw new BadRequestException('There is already a product with this name.');
            }
            throw error;
        }
    }


    //Register new stock entry
    public async createStock(data: CreateStockDto) {
    const { 
        productId, 
        userId,       
        movementDate, 
        expiryDate, 
        ...rest       
    } = data;

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
    return this.inventoryRepository.createStock(prismaData);
}


    //List all products
    public async getAllProducts(skip?: number, take?: number) {
    const products = await this.inventoryRepository.findAllProducts(skip, take);

    return products.map(p => ({
        id: p.id,
        name: p.name,
        productTypeId: p.productTypeId,
        productTypeDescription: p.productType.description,
    }));
}


    //View stock history and expiry dates
    public async getAllStockRecords(skip?: number, take?: number) {
    const stocks = await this.inventoryRepository.findAllStock(skip, take);

    return stocks.map(s => ({
        id: s.id,
        quantity: s.quantity,
        movementDate: s.movementDate,
        expiryDate: s.expiryDate,
        notes: s.notes,
        productId: s.productId,
        productName: s.product.name,
        userId: s.user?.id,
        userName: s.user?.name,
    }));
}
}
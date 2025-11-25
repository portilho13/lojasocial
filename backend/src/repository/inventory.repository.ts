import { Injectable } from "@nestjs/common";
import { Prisma, Product, Stock } from "@prisma/client";
import { PrismaService } from "prisma/prisma.service";

@Injectable()
export class InventoryRepository {
    constructor (private readonly prisma: PrismaService) {}

    //Register new type of product
    public async createProduct(data: Prisma.ProductCreateInput): Promise<Product> {
        return this.prisma.product.create({
            data,
        });
    }

    //Register stock entry
    public async createStock(data: Prisma.StockCreateInput): Promise<Stock> {
        return this.prisma.stock.create({
            data,
        });
    }

    //List all products
    public async findAllProducts(skip = 0, take = 50) {
    return this.prisma.product.findMany({
        skip,
        take,
        include: { productType: true },
        orderBy: { name: 'asc' },
    });
}

    //View stock history and order by expiry date
    public async findAllStock(skip = 0, take = 50) {
    return this.prisma.stock.findMany({
        skip,
        take,
        include: { 
            product: true, 
            user: { 
                select: { id: true, name: true } 
            }, 
        },
        orderBy: { expiryDate: 'asc' },
    });
}

}
import { Injectable } from '@nestjs/common';
import { Prisma, Product, Stock } from '@prisma/client';
import { PrismaService } from 'prisma/prisma.service';

@Injectable()
export class InventoryRepository {
  constructor(private readonly prisma: PrismaService) { }

  // Get product by name
  public async getProductByName(name: Product['name']) {
    return this.prisma.product.findFirst({
      where: { name },
    });
  }

  //Register new product type
  public async createProductType(data: Prisma.ProductTypeCreateInput) {
    return this.prisma.productType.create({ data });
  }

  //List all product types
  public async findAllProductTypes() {
    return this.prisma.productType.findMany();
  }

  //Register new type of product
  public async createProduct(data: Prisma.ProductCreateInput) {
    return this.prisma.product.create({
      data,
      include: { type: true },
    });
  }

  //Register stock entry
  public async createStock(data: Prisma.StockCreateInput) {
    return this.prisma.stock.create({
      data,
      include: {
        product: true,
      },
    });
  }

  //List all products
  public async findAllProducts(skip = 0, take = 50) {
    return this.prisma.product.findMany({
      skip,
      take,
      include: { type: true },
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
      },
      orderBy: { expiryDate: 'asc' },
    });
  }

  //Update stock entry
  public async updateStock(id: string, data: Prisma.StockUpdateInput) {
    return this.prisma.stock.update({
      where: { id },
      data,
      include: { product: true },
    });
  }

  //Delete stock entry
  public async deleteStock(id: string) {
    return this.prisma.stock.delete({
      where: { id },
    });
  }

  //Find stock entries that are expiring soon
  public async findExpiringStock(thresholdDate: Date) {
    return this.prisma.stock.findMany({
      where: {
        expiryDate: {
          lte: thresholdDate,
          gte: new Date(),
        },
      },
      include: { product: true },
      orderBy: { expiryDate: 'asc' },
    });
  }

  //Get stock grouped by product category
  public async getStockByCategory() {
    return this.prisma.productType.findMany({
      include: {
        products: {
          include: {
            stockBatches: true,
          },
        },
      },
    });
  }
}

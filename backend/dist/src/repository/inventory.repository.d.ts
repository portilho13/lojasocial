import { Prisma, Product, Stock } from "@prisma/client";
import { PrismaService } from "prisma/prisma.service";
export declare class InventoryRepository {
    private readonly prisma;
    constructor(prisma: PrismaService);
    createProduct(data: Prisma.ProductCreateInput): Promise<Product>;
    createStock(data: Prisma.StockCreateInput): Promise<Stock>;
    findAllProducts(): Promise<({
        productType: {
            id: string;
            description: string;
        };
    } & {
        name: string;
        id: string;
        productTypeId: string;
    })[]>;
    findAllStock(): Promise<({
        product: {
            name: string;
            id: string;
            productTypeId: string;
        };
        user: {
            name: string;
            contact: string;
            email: string;
            id: string;
            userType: string;
        } | null;
    } & {
        id: string;
        notes: string | null;
        userId: string | null;
        quantity: number;
        movementDate: Date;
        expiryDate: Date;
        productId: string;
    })[]>;
}

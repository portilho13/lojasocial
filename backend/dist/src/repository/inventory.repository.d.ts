import { Prisma, Product } from '@prisma/client';
import { PrismaService } from 'prisma/prisma.service';
export declare class InventoryRepository {
    private readonly prisma;
    constructor(prisma: PrismaService);
    getProductByName(name: Product['name']): Promise<{
        name: string;
        id: string;
        productTypeId: string;
    } | null>;
    createProduct(data: Prisma.ProductCreateInput): Promise<{
        productType: {
            id: string;
            description: string;
        };
    } & {
        name: string;
        id: string;
        productTypeId: string;
    }>;
    createStock(data: Prisma.StockCreateInput): Promise<{
        product: {
            name: string;
            id: string;
            productTypeId: string;
        };
        user: {
            name: string;
            id: string;
        } | null;
    } & {
        id: string;
        notes: string | null;
        userId: string | null;
        quantity: number;
        movementDate: Date;
        expiryDate: Date;
        productId: string;
    }>;
    findAllProducts(skip?: number, take?: number): Promise<({
        productType: {
            id: string;
            description: string;
        };
    } & {
        name: string;
        id: string;
        productTypeId: string;
    })[]>;
    findAllStock(skip?: number, take?: number): Promise<({
        product: {
            name: string;
            id: string;
            productTypeId: string;
        };
        user: {
            name: string;
            id: string;
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
    updateStock(id: string, data: Prisma.StockUpdateInput): Promise<{
        product: {
            name: string;
            id: string;
            productTypeId: string;
        };
    } & {
        id: string;
        notes: string | null;
        userId: string | null;
        quantity: number;
        movementDate: Date;
        expiryDate: Date;
        productId: string;
    }>;
    deleteStock(id: string): Promise<{
        id: string;
        notes: string | null;
        userId: string | null;
        quantity: number;
        movementDate: Date;
        expiryDate: Date;
        productId: string;
    }>;
    findExpiringStock(thresholdDate: Date): Promise<({
        product: {
            name: string;
            id: string;
            productTypeId: string;
        };
    } & {
        id: string;
        notes: string | null;
        userId: string | null;
        quantity: number;
        movementDate: Date;
        expiryDate: Date;
        productId: string;
    })[]>;
    getStockByCategory(): Promise<({
        products: ({
            stocks: {
                id: string;
                notes: string | null;
                userId: string | null;
                quantity: number;
                movementDate: Date;
                expiryDate: Date;
                productId: string;
            }[];
        } & {
            name: string;
            id: string;
            productTypeId: string;
        })[];
    } & {
        id: string;
        description: string;
    })[]>;
}

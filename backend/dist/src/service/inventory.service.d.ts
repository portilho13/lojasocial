import { InventoryRepository } from '../repository/inventory.repository';
import { CreateProductDto } from '../dto/inventory/create-product.dto';
import { CreateStockDto } from '../dto/inventory/create-stock.dto';
export declare class InventoryService {
    private readonly inventoryRepository;
    constructor(inventoryRepository: InventoryRepository);
    createProduct(data: CreateProductDto): Promise<{
        name: string;
        id: string;
        productTypeId: string;
    }>;
    createStock(data: CreateStockDto): Promise<{
        id: string;
        notes: string | null;
        userId: string | null;
        quantity: number;
        movementDate: Date;
        expiryDate: Date;
        productId: string;
    }>;
    getAllProducts(): Promise<({
        productType: {
            id: string;
            description: string;
        };
    } & {
        name: string;
        id: string;
        productTypeId: string;
    })[]>;
    getAllStockRecords(): Promise<({
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

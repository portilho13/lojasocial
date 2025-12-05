import { InventoryService } from 'src/service/inventory.service';
import { CreateProductDto } from 'src/dto/inventory/create-product.dto';
import { CreateStockDto } from 'src/dto/inventory/create-stock.dto';
export declare class InventoryController {
    private readonly inventoryService;
    constructor(inventoryService: InventoryService);
    createProduct(body: CreateProductDto): Promise<{
        name: string;
        id: string;
        productTypeId: string;
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
    createStock(body: CreateStockDto): Promise<{
        id: string;
        notes: string | null;
        userId: string | null;
        quantity: number;
        movementDate: Date;
        expiryDate: Date;
        productId: string;
    }>;
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

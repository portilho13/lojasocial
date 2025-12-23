import type { Response } from 'express';
import { InventoryService } from 'src/service/inventory.service';
import { CreateProductDto } from 'src/dto/inventory/create-product.dto';
import { CreateStockDto } from 'src/dto/inventory/create-stock.dto';
import { UpdateStockDto } from 'src/dto/inventory/update-stock.dto';
export declare class InventoryController {
    private readonly inventoryService;
    constructor(inventoryService: InventoryService);
    createProduct(body: CreateProductDto, res: Response): Promise<Response<any, Record<string, any>>>;
    getAllProducts(res: Response, skip?: string, take?: string): Promise<Response<any, Record<string, any>>>;
    createStock(body: CreateStockDto, res: Response): Promise<Response<any, Record<string, any>>>;
    getAllStockRecords(res: Response, skip?: string, take?: string): Promise<Response<any, Record<string, any>>>;
    updateStock(id: string, body: UpdateStockDto, res: Response): Promise<Response<any, Record<string, any>>>;
    deleteStock(id: string, res: Response): Promise<Response<any, Record<string, any>>>;
    getExpiringStock(days: string, res: Response): Promise<Response<any, Record<string, any>>>;
    getStockSummary(res: Response): Promise<Response<any, Record<string, any>>>;
}

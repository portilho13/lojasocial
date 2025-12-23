import { InventoryRepository } from '../repository/inventory.repository';
import { CreateProductDto } from '../dto/inventory/create-product.dto';
import { CreateStockDto } from '../dto/inventory/create-stock.dto';
import { ProductResponseDto } from '../dto/inventory/product-response.dto';
import { StockResponseDto } from '../dto/inventory/stock-response.dto';
import { UpdateStockDto } from '../dto/inventory/update-stock.dto';
import { StockSummaryDto } from 'src/dto/inventory/stock-summary.dto';
export declare class InventoryService {
    private readonly inventoryRepository;
    constructor(inventoryRepository: InventoryRepository);
    createProduct(dto: CreateProductDto): Promise<ProductResponseDto>;
    createStock(dto: CreateStockDto): Promise<StockResponseDto>;
    getAllProducts(skip?: number, take?: number): Promise<ProductResponseDto[]>;
    getAllStockRecords(skip?: number, take?: number): Promise<StockResponseDto[]>;
    updateStock(id: string, dto: UpdateStockDto): Promise<StockResponseDto>;
    deleteStock(id: string): Promise<void>;
    getExpiringStock(days?: number): Promise<StockResponseDto[]>;
    getStockSummary(): Promise<StockSummaryDto[]>;
}

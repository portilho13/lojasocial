"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.InventoryService = void 0;
const common_1 = require("@nestjs/common");
const inventory_repository_1 = require("../repository/inventory.repository");
const product_response_dto_1 = require("../dto/inventory/product-response.dto");
const stock_response_dto_1 = require("../dto/inventory/stock-response.dto");
let InventoryService = class InventoryService {
    inventoryRepository;
    constructor(inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }
    async createProduct(dto) {
        const productByName = await this.inventoryRepository.getProductByName(dto.name);
        if (productByName)
            throw new common_1.ConflictException('There is already a product with this name.');
        const { productTypeId, ...productData } = dto;
        const newProduct = await this.inventoryRepository.createProduct({
            ...productData,
            productType: {
                connect: { id: productTypeId },
            },
        });
        return new product_response_dto_1.ProductResponseDto(newProduct);
    }
    async createStock(dto) {
        const { productId, userId, movementDate, expiryDate, ...rest } = dto;
        const prismaData = {
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
        return new stock_response_dto_1.StockResponseDto(newStock);
    }
    async getAllProducts(skip, take) {
        const products = await this.inventoryRepository.findAllProducts(skip, take);
        return products.map((p) => new product_response_dto_1.ProductResponseDto(p));
    }
    async getAllStockRecords(skip, take) {
        const stocks = await this.inventoryRepository.findAllStock(skip, take);
        return stocks.map((s) => new stock_response_dto_1.StockResponseDto(s));
    }
    async updateStock(id, dto) {
        const updatedStock = await this.inventoryRepository.updateStock(id, dto);
        return new stock_response_dto_1.StockResponseDto(updatedStock);
    }
    async deleteStock(id) {
        await this.inventoryRepository.deleteStock(id);
    }
    async getExpiringStock(days = 30) {
        const thresholdDate = new Date();
        thresholdDate.setDate(thresholdDate.getDate() + days);
        const stocks = await this.inventoryRepository.findExpiringStock(thresholdDate);
        return stocks.map((s) => new stock_response_dto_1.StockResponseDto(s));
    }
    async getStockSummary() {
        const categories = await this.inventoryRepository.getStockByCategory();
        return categories.map((cat) => {
            const total = cat.products.reduce((sum, product) => {
                const productTotal = product.stocks.reduce((s, stock) => s + stock.quantity, 0);
                return sum + productTotal;
            }, 0);
            return {
                category: cat.description,
                totalQuantity: total,
            };
        });
    }
};
exports.InventoryService = InventoryService;
exports.InventoryService = InventoryService = __decorate([
    (0, common_1.Injectable)(),
    __metadata("design:paramtypes", [inventory_repository_1.InventoryRepository])
], InventoryService);
//# sourceMappingURL=inventory.service.js.map
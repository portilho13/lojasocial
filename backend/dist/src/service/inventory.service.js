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
let InventoryService = class InventoryService {
    inventoryRepository;
    constructor(inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }
    async createProduct(data) {
        const { productTypeId, name } = data;
        const prismaData = {
            name,
            productType: {
                connect: { id: productTypeId },
            },
        };
        try {
            return await this.inventoryRepository.createProduct(prismaData);
        }
        catch (error) {
            if (error.code === 'P2002') {
                throw new common_1.BadRequestException('O produto com este nome já existe.');
            }
            throw error;
        }
    }
    async createStock(data) {
        const { productId, userId, movementDate, expiryDate, ...rest } = data;
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
        return this.inventoryRepository.createStock(prismaData);
    }
    async getAllProducts() {
        return this.inventoryRepository.findAllProducts();
    }
    async getAllStockRecords() {
        return this.inventoryRepository.findAllStock();
    }
};
exports.InventoryService = InventoryService;
exports.InventoryService = InventoryService = __decorate([
    (0, common_1.Injectable)(),
    __metadata("design:paramtypes", [inventory_repository_1.InventoryRepository])
], InventoryService);
//# sourceMappingURL=inventory.service.js.map
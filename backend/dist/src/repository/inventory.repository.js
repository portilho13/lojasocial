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
exports.InventoryRepository = void 0;
const common_1 = require("@nestjs/common");
const prisma_service_1 = require("../../prisma/prisma.service");
let InventoryRepository = class InventoryRepository {
    prisma;
    constructor(prisma) {
        this.prisma = prisma;
    }
    async getProductByName(name) {
        return this.prisma.product.findFirst({
            where: { name },
        });
    }
    async createProduct(data) {
        return this.prisma.product.create({
            data,
            include: { productType: true },
        });
    }
    async createStock(data) {
        return this.prisma.stock.create({
            data,
            include: {
                product: true,
                user: {
                    select: { id: true, name: true },
                },
            },
        });
    }
    async findAllProducts(skip = 0, take = 50) {
        return this.prisma.product.findMany({
            skip,
            take,
            include: { productType: true },
            orderBy: { name: 'asc' },
        });
    }
    async findAllStock(skip = 0, take = 50) {
        return this.prisma.stock.findMany({
            skip,
            take,
            include: {
                product: true,
                user: {
                    select: { id: true, name: true },
                },
            },
            orderBy: { expiryDate: 'asc' },
        });
    }
    async updateStock(id, data) {
        return this.prisma.stock.update({
            where: { id },
            data,
            include: { product: true },
        });
    }
    async deleteStock(id) {
        return this.prisma.stock.delete({
            where: { id },
        });
    }
    async findExpiringStock(thresholdDate) {
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
    async getStockByCategory() {
        return this.prisma.productType.findMany({
            include: {
                products: {
                    include: {
                        stocks: true,
                    },
                },
            },
        });
    }
};
exports.InventoryRepository = InventoryRepository;
exports.InventoryRepository = InventoryRepository = __decorate([
    (0, common_1.Injectable)(),
    __metadata("design:paramtypes", [prisma_service_1.PrismaService])
], InventoryRepository);
//# sourceMappingURL=inventory.repository.js.map
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
    async createProduct(data) {
        return this.prisma.product.create({
            data,
        });
    }
    async createStock(data) {
        return this.prisma.stock.create({
            data,
        });
    }
    async findAllProducts() {
        return this.prisma.product.findMany({
            include: { productType: true }
        });
    }
    async findAllStock() {
        return this.prisma.stock.findMany({
            include: {
                product: true,
                user: {
                    select: {
                        id: true,
                        name: true,
                        userType: true,
                        contact: true,
                        email: true,
                    }
                },
            },
            orderBy: { expiryDate: 'asc' }
        });
    }
};
exports.InventoryRepository = InventoryRepository;
exports.InventoryRepository = InventoryRepository = __decorate([
    (0, common_1.Injectable)(),
    __metadata("design:paramtypes", [prisma_service_1.PrismaService])
], InventoryRepository);
//# sourceMappingURL=inventory.repository.js.map
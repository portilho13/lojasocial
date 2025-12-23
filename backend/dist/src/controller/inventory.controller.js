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
var __param = (this && this.__param) || function (paramIndex, decorator) {
    return function (target, key) { decorator(target, key, paramIndex); }
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.InventoryController = void 0;
const common_1 = require("@nestjs/common");
const inventory_service_1 = require("../service/inventory.service");
const create_product_dto_1 = require("../dto/inventory/create-product.dto");
const create_stock_dto_1 = require("../dto/inventory/create-stock.dto");
const update_stock_dto_1 = require("../dto/inventory/update-stock.dto");
let InventoryController = class InventoryController {
    inventoryService;
    constructor(inventoryService) {
        this.inventoryService = inventoryService;
    }
    async createProduct(body, res) {
        try {
            const product = await this.inventoryService.createProduct(body);
            return res.status(common_1.HttpStatus.CREATED).json(product);
        }
        catch (e) {
            if (e instanceof common_1.HttpException) {
                return res.status(e.getStatus()).json(e.getResponse());
            }
            return res
                .status(common_1.HttpStatus.INTERNAL_SERVER_ERROR)
                .json({ message: 'Internal server error' });
        }
    }
    async getAllProducts(res, skip, take) {
        try {
            const products = await this.inventoryService.getAllProducts(skip ? parseInt(skip) : undefined, take ? parseInt(take) : undefined);
            return res.status(common_1.HttpStatus.OK).json(products);
        }
        catch (e) {
            if (e instanceof common_1.HttpException) {
                return res.status(e.getStatus()).json(e.getResponse());
            }
            return res
                .status(common_1.HttpStatus.INTERNAL_SERVER_ERROR)
                .json({ message: 'Internal server error' });
        }
    }
    async createStock(body, res) {
        try {
            const stock = await this.inventoryService.createStock(body);
            return res.status(common_1.HttpStatus.CREATED).json(stock);
        }
        catch (e) {
            if (e instanceof common_1.HttpException) {
                return res.status(e.getStatus()).json(e.getResponse());
            }
            return res
                .status(common_1.HttpStatus.INTERNAL_SERVER_ERROR)
                .json({ message: 'Internal server error' });
        }
    }
    async getAllStockRecords(res, skip, take) {
        try {
            const stocks = await this.inventoryService.getAllStockRecords(skip ? parseInt(skip) : undefined, take ? parseInt(take) : undefined);
            return res.status(common_1.HttpStatus.OK).json(stocks);
        }
        catch (e) {
            if (e instanceof common_1.HttpException) {
                return res.status(e.getStatus()).json(e.getResponse());
            }
            return res
                .status(common_1.HttpStatus.INTERNAL_SERVER_ERROR)
                .json({ message: 'Internal server error' });
        }
    }
    async updateStock(id, body, res) {
        try {
            const stock = await this.inventoryService.updateStock(id, body);
            return res.status(common_1.HttpStatus.OK).json(stock);
        }
        catch (e) {
            if (e instanceof common_1.HttpException) {
                return res.status(e.getStatus()).json(e.getResponse());
            }
            return res
                .status(common_1.HttpStatus.INTERNAL_SERVER_ERROR)
                .json({ message: 'Internal server error' });
        }
    }
    async deleteStock(id, res) {
        try {
            await this.inventoryService.deleteStock(id);
            return res.status(common_1.HttpStatus.NO_CONTENT).send();
        }
        catch (e) {
            if (e instanceof common_1.HttpException) {
                return res.status(e.getStatus()).json(e.getResponse());
            }
            return res
                .status(common_1.HttpStatus.INTERNAL_SERVER_ERROR)
                .json({ message: 'Internal server error' });
        }
    }
    async getExpiringStock(days, res) {
        try {
            const threshold = days ? parseInt(days) : 30;
            const stocks = await this.inventoryService.getExpiringStock(threshold);
            return res.status(common_1.HttpStatus.OK).json(stocks);
        }
        catch (e) {
            if (e instanceof common_1.HttpException) {
                return res.status(e.getStatus()).json(e.getResponse());
            }
            return res
                .status(common_1.HttpStatus.INTERNAL_SERVER_ERROR)
                .json({ message: 'Internal server error' });
        }
    }
    async getStockSummary(res) {
        try {
            const summary = await this.inventoryService.getStockSummary();
            return res.status(common_1.HttpStatus.OK).json(summary);
        }
        catch (e) {
            return res
                .status(common_1.HttpStatus.INTERNAL_SERVER_ERROR)
                .json({ message: 'Internal server error' });
        }
    }
};
exports.InventoryController = InventoryController;
__decorate([
    (0, common_1.Post)('products'),
    __param(0, (0, common_1.Body)(common_1.ValidationPipe)),
    __param(1, (0, common_1.Res)()),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [create_product_dto_1.CreateProductDto, Object]),
    __metadata("design:returntype", Promise)
], InventoryController.prototype, "createProduct", null);
__decorate([
    (0, common_1.Get)('products'),
    __param(0, (0, common_1.Res)()),
    __param(1, (0, common_1.Query)('skip')),
    __param(2, (0, common_1.Query)('take')),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [Object, String, String]),
    __metadata("design:returntype", Promise)
], InventoryController.prototype, "getAllProducts", null);
__decorate([
    (0, common_1.Post)('stocks'),
    __param(0, (0, common_1.Body)(common_1.ValidationPipe)),
    __param(1, (0, common_1.Res)()),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [create_stock_dto_1.CreateStockDto, Object]),
    __metadata("design:returntype", Promise)
], InventoryController.prototype, "createStock", null);
__decorate([
    (0, common_1.Get)('stocks'),
    __param(0, (0, common_1.Res)()),
    __param(1, (0, common_1.Query)('skip')),
    __param(2, (0, common_1.Query)('take')),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [Object, String, String]),
    __metadata("design:returntype", Promise)
], InventoryController.prototype, "getAllStockRecords", null);
__decorate([
    (0, common_1.Patch)('stocks/:id'),
    __param(0, (0, common_1.Param)('id')),
    __param(1, (0, common_1.Body)(common_1.ValidationPipe)),
    __param(2, (0, common_1.Res)()),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [String, update_stock_dto_1.UpdateStockDto, Object]),
    __metadata("design:returntype", Promise)
], InventoryController.prototype, "updateStock", null);
__decorate([
    (0, common_1.Delete)('stocks/:id'),
    __param(0, (0, common_1.Param)('id')),
    __param(1, (0, common_1.Res)()),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [String, Object]),
    __metadata("design:returntype", Promise)
], InventoryController.prototype, "deleteStock", null);
__decorate([
    (0, common_1.Get)('stocks/expiring'),
    __param(0, (0, common_1.Query)('days')),
    __param(1, (0, common_1.Res)()),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [String, Object]),
    __metadata("design:returntype", Promise)
], InventoryController.prototype, "getExpiringStock", null);
__decorate([
    (0, common_1.Get)('stocks/summary'),
    __param(0, (0, common_1.Res)()),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [Object]),
    __metadata("design:returntype", Promise)
], InventoryController.prototype, "getStockSummary", null);
exports.InventoryController = InventoryController = __decorate([
    (0, common_1.Controller)('api/v1/inventory'),
    __metadata("design:paramtypes", [inventory_service_1.InventoryService])
], InventoryController);
//# sourceMappingURL=inventory.controller.js.map
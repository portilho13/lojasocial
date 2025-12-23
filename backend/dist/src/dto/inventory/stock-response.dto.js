"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.StockResponseDto = void 0;
class StockResponseDto {
    id;
    quantity;
    movementDate;
    expiryDate;
    notes;
    productId;
    productName;
    userId;
    userName;
    constructor(stock) {
        this.id = stock.id;
        this.quantity = stock.quantity;
        this.movementDate = stock.movementDate;
        this.expiryDate = stock.expiryDate;
        this.notes = stock.notes;
        this.productId = stock.productId;
        this.productName = stock.product.name;
        this.userId = stock.user?.id;
        this.userName = stock.user?.name;
    }
}
exports.StockResponseDto = StockResponseDto;
//# sourceMappingURL=stock-response.dto.js.map
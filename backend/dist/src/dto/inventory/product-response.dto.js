"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.ProductResponseDto = void 0;
class ProductResponseDto {
    id;
    name;
    productTypeId;
    productTypeDescription;
    constructor(product) {
        this.id = product.id;
        this.name = product.name;
        this.productTypeId = product.productTypeId;
        this.productTypeDescription = product.productType.description;
    }
}
exports.ProductResponseDto = ProductResponseDto;
//# sourceMappingURL=product-response.dto.js.map
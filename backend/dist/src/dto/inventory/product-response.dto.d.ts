import { Product, ProductType } from "@prisma/client";
export declare class ProductResponseDto {
    id: Product['id'];
    name: Product['name'];
    productTypeId: Product['productTypeId'];
    productTypeDescription: ProductType['description'];
    constructor(product: Product & {
        productType: ProductType;
    });
}

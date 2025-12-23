import { Stock, Product, User } from "@prisma/client";
export declare class StockResponseDto {
    id: Stock['id'];
    quantity: Stock['quantity'];
    movementDate: Stock['movementDate'];
    expiryDate: Stock['expiryDate'];
    notes: Stock['notes'];
    productId: Stock['productId'];
    productName: Product['name'];
    userId?: Stock['userId'];
    userName?: User['name'];
    constructor(stock: Stock & {
        product: Product;
        user?: {
            id: string;
            name: string;
        } | null;
    });
}

import { Stock, Product } from "@prisma/client";

export class StockResponseDto {
  id: Stock['id'];
  quantity: Stock['quantity'];
  expiryDate: Stock['expiryDate'];
  location: Stock['location'];
  productId: Stock['productId'];
  productName: Product['name'];

  constructor(stock: Stock & {
    product: Product;
  }) {
    this.id = stock.id;
    this.quantity = stock.quantity;
    this.expiryDate = stock.expiryDate;
    this.location = stock.location;
    this.productId = stock.productId;
    this.productName = stock.product.name;
  }
}
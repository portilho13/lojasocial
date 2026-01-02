import { Stock, Product, User } from "@prisma/client";

export class StockResponseDto {
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
      user?: { id: string; name: string } | null
     }) {
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
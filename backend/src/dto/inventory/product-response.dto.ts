import { Product, ProductType } from "@prisma/client";

export class ProductResponseDto {
  id: Product['id'];
  name: Product['name'];
  typeId: Product['typeId'];
  typeDescription: ProductType['description'];

  constructor(product: Product & { type: ProductType }) {
    this.id = product.id;
    this.name = product.name;
    this.typeId = product.typeId;
    this.typeDescription = product.type.description;
  }
}
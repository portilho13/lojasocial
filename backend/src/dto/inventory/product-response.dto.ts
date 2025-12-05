import { Product, ProductType } from "@prisma/client";

export class ProductResponseDto {
  id: Product['id'];
  name: Product['name'];
  productTypeId: Product['productTypeId'];
  productTypeDescription: ProductType['description'];

  constructor(product: Product & { productType: ProductType }) {
    this.id = product.id;
    this.name = product.name;
    this.productTypeId = product.productTypeId;
    this.productTypeDescription = product.productType.description;
  }
}
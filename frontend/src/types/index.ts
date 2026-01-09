export interface Campaign {
  id: string;
  title: string;
  description: string | null;
  startDate: string;
  endDate: string | null;
}

export interface ProductType {
  id: string;
  description: string;
}

export interface StockSummary {
  typeId: string;
  typeDescription: string;
  totalQuantity: number;
}

export interface Product {
  id: string;
  name: string;
  typeId: string;
  type: ProductType;
}

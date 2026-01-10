import { Campaign, StockSummary, ProductType } from "@/types";

const API_BASE_URL = process.env.NEXT_PUBLIC_API_URL || "http://localhost:3000/api/v1";

export async function getStockSummary(): Promise<StockSummary[]> {
  try {
    const response = await fetch(`${API_BASE_URL}/inventory/stocks/summary`, {
      cache: 'no-store',
    });
    if (!response.ok) {
      throw new Error("Failed to fetch stock summary");
    }
    return response.json();
  } catch (error) {
    console.error("Error fetching stock summary:", error);
    return [];
  }
}

export async function getProductTypes(): Promise<ProductType[]> {
  try {
    const response = await fetch(`${API_BASE_URL}/inventory/types`, {
      next: { revalidate: 60 },
    });
    if (!response.ok) {
      throw new Error("Failed to fetch product types");
    }
    return response.json();
  } catch (error) {
    console.error("Error fetching product types:", error);
    return [];
  }
}

export async function getCampaigns(): Promise<Campaign[]> {
  try {
    const response = await fetch(`${API_BASE_URL}/campaigns/active`, {
      next: { revalidate: 60 },
    });
    if (!response.ok) {
      throw new Error("Failed to fetch campaigns");
    }
    return response.json();
  } catch (error) {
    console.error("Error fetching campaigns:", error);
    return [];
  }
}

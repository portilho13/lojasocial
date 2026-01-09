import { Campaign, StockSummary, ProductType } from "@/types";

const API_BASE_URL = process.env.NEXT_PUBLIC_API_URL || "http://localhost:3000/api/v1";

// Mock campaigns data until backend endpoint is ready
const MOCK_CAMPAIGNS: Campaign[] = [
  {
    id: "1",
    title: "Campanha de Natal 2025",
    description: "Recolha de alimentos e produtos de higiene para apoiar os estudantes durante a época natalícia. Ajude-nos a garantir que ninguém fica sem apoio nesta quadra festiva.",
    startDate: "2025-12-01T00:00:00.000Z",
    endDate: "2025-12-23T00:00:00.000Z",
  },
  {
    id: "2",
    title: "Regresso às Aulas 2026",
    description: "Campanha de recolha de material escolar e produtos essenciais para o início do segundo semestre letivo.",
    startDate: "2026-01-15T00:00:00.000Z",
    endDate: "2026-02-15T00:00:00.000Z",
  },
  {
    id: "3",
    title: "Campanha Solidária de Primavera",
    description: "Recolha contínua de bens alimentares não perecíveis e produtos de higiene pessoal.",
    startDate: "2026-03-01T00:00:00.000Z",
    endDate: null,
  },
];

export async function getStockSummary(): Promise<StockSummary[]> {
  try {
    const response = await fetch(`${API_BASE_URL}/inventory/stocks/summary`, {
      next: { revalidate: 60 },
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
  // TODO: Replace with actual API call when backend endpoint is ready
  // try {
  //   const response = await fetch(`${API_BASE_URL}/campaigns`, {
  //     next: { revalidate: 60 },
  //   });
  //   if (!response.ok) {
  //     throw new Error("Failed to fetch campaigns");
  //   }
  //   return response.json();
  // } catch (error) {
  //   console.error("Error fetching campaigns:", error);
  //   return [];
  // }
  
  // Return mock data for now
  return MOCK_CAMPAIGNS;
}

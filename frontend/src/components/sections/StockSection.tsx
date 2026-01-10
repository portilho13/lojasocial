"use client";

import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Package } from "lucide-react";
import { StockSummary } from "@/types";
import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  ResponsiveContainer,
  Cell,
} from "recharts";

interface StockSectionProps {
  stockSummary: StockSummary[];
}

// Generate colors dynamically based on index using actual hex values
const getBarColor = (index: number) => {
  const colors = [
    "#1a3a2f", // Dark green (primary)
    "#c4a35a", // Gold (secondary)
    "#2d5a47", // Medium green
    "#d4b86a", // Light gold
    "#3d7a5f", // Light green
  ];
  return colors[index % colors.length];
};

export function StockSection({ stockSummary }: StockSectionProps) {
  const chartData = stockSummary.map((item) => ({
    name: item.category || item.typeDescription,
    quantidade: item.totalQuantity,
  }));

  const totalItems = stockSummary.reduce((acc, item) => acc + item.totalQuantity, 0);
  const categories = stockSummary.length;

  return (
    <section id="stock" className="py-20 bg-background">
      <div className="container mx-auto px-4">
        <div className="max-w-3xl mx-auto text-center mb-12">
          <h2 className="text-3xl md:text-4xl font-bold mb-4">
            Stock Disponível
          </h2>
          <p className="text-lg text-muted-foreground">
            Consulte em tempo real a disponibilidade de produtos na Loja Social 
            e saiba quais as categorias com maior necessidade de doações.
          </p>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
          <Card>
            <CardHeader className="pb-2">
              <CardTitle className="text-sm font-medium text-muted-foreground">
                Total de Itens
              </CardTitle>
            </CardHeader>
            <CardContent>
              <div className="text-3xl font-bold">{totalItems}</div>
              <p className="text-xs text-muted-foreground">unidades em stock</p>
            </CardContent>
          </Card>
          <Card>
            <CardHeader className="pb-2">
              <CardTitle className="text-sm font-medium text-muted-foreground">
                Categorias
              </CardTitle>
            </CardHeader>
            <CardContent>
              <div className="text-3xl font-bold">{categories}</div>
              <p className="text-xs text-muted-foreground">tipos de produtos</p>
            </CardContent>
          </Card>
          <Card>
            <CardHeader className="pb-2">
              <CardTitle className="text-sm font-medium text-muted-foreground">
                Última Atualização
              </CardTitle>
            </CardHeader>
            <CardContent>
              <div className="text-3xl font-bold">Agora</div>
              <p className="text-xs text-muted-foreground">dados em tempo real</p>
            </CardContent>
          </Card>
        </div>

        {stockSummary.length > 0 ? (
          <Card>
            <CardHeader>
              <CardTitle>Stock por Categoria</CardTitle>
            </CardHeader>
            <CardContent>
              <div className="h-[400px] w-full">
                <ResponsiveContainer width="100%" height="100%">
                  <BarChart
                    data={chartData}
                    margin={{ top: 20, right: 30, left: 20, bottom: 60 }}
                  >
                    <CartesianGrid strokeDasharray="3 3" className="stroke-muted" />
                    <XAxis
                      dataKey="name"
                      angle={-45}
                      textAnchor="end"
                      height={80}
                      tick={{ fontSize: 12 }}
                      className="fill-muted-foreground"
                    />
                    <YAxis
                      tick={{ fontSize: 12 }}
                      className="fill-muted-foreground"
                    />
                    <Tooltip
                      contentStyle={{
                        backgroundColor: "#ffffff",
                        border: "1px solid #e4e4e7",
                        borderRadius: "8px",
                      }}
                      labelStyle={{ color: "#1a3a2f" }}
                    />
                    <Bar dataKey="quantidade" radius={[4, 4, 0, 0]}>
                      {chartData.map((_, index) => (
                        <Cell
                          key={`cell-${index}`}
                          fill={getBarColor(index)}
                        />
                      ))}
                    </Bar>
                  </BarChart>
                </ResponsiveContainer>
              </div>
            </CardContent>
          </Card>
        ) : (
          <Card className="max-w-md mx-auto">
            <CardContent className="pt-6 text-center">
              <Package className="h-12 w-12 text-muted-foreground mx-auto mb-4" />
              <h3 className="font-semibold text-lg mb-2">Sem Dados de Stock</h3>
              <p className="text-sm text-muted-foreground">
                Os dados de stock não estão disponíveis de momento. 
                Por favor, tente novamente mais tarde.
              </p>
            </CardContent>
          </Card>
        )}
      </div>
    </section>
  );
}

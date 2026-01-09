"use client";

import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { Calendar, Clock } from "lucide-react";
import { Campaign } from "@/types";

interface CampaignsSectionProps {
  campaigns: Campaign[];
}

function formatDate(dateString: string): string {
  return new Date(dateString).toLocaleDateString("pt-PT", {
    day: "numeric",
    month: "long",
    year: "numeric",
  });
}

function getCampaignStatus(campaign: Campaign): { label: string; variant: "default" | "secondary" | "destructive" } {
  const now = new Date();
  const start = new Date(campaign.startDate);
  const end = campaign.endDate ? new Date(campaign.endDate) : null;

  if (now < start) {
    return { label: "Em Breve", variant: "secondary" };
  }
  if (end && now > end) {
    return { label: "Terminada", variant: "destructive" };
  }
  return { label: "A Decorrer", variant: "default" };
}

export function CampaignsSection({ campaigns }: CampaignsSectionProps) {
  const activeCampaigns = campaigns.filter((c) => {
    const now = new Date();
    const end = c.endDate ? new Date(c.endDate) : null;
    return !end || now <= end;
  });

  return (
    <section id="campanhas" className="py-20 bg-muted/50">
      <div className="container mx-auto px-4">
        <div className="max-w-3xl mx-auto text-center mb-12">
          <h2 className="text-3xl md:text-4xl font-bold mb-4">
            Campanhas de Recolha
          </h2>
          <p className="text-lg text-muted-foreground">
            Acompanhe as campanhas de recolha em curso e saiba como pode contribuir 
            para ajudar os estudantes do IPCA.
          </p>
        </div>

        {activeCampaigns.length > 0 ? (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {activeCampaigns.map((campaign) => {
              const status = getCampaignStatus(campaign);
              return (
                <Card key={campaign.id} className="hover:shadow-lg transition-shadow">
                  <CardHeader>
                    <div className="flex items-start justify-between gap-2">
                      <CardTitle className="text-xl">{campaign.title}</CardTitle>
                      <Badge variant={status.variant}>{status.label}</Badge>
                    </div>
                  </CardHeader>
                  <CardContent className="space-y-4">
                    {campaign.description && (
                      <p className="text-sm text-muted-foreground">
                        {campaign.description}
                      </p>
                    )}
                    <div className="space-y-2 text-sm">
                      <div className="flex items-center gap-2 text-muted-foreground">
                        <Calendar className="h-4 w-4" />
                        <span>Início: {formatDate(campaign.startDate)}</span>
                      </div>
                      {campaign.endDate && (
                        <div className="flex items-center gap-2 text-muted-foreground">
                          <Clock className="h-4 w-4" />
                          <span>Fim: {formatDate(campaign.endDate)}</span>
                        </div>
                      )}
                    </div>
                  </CardContent>
                </Card>
              );
            })}
          </div>
        ) : (
          <Card className="max-w-md mx-auto">
            <CardContent className="pt-6 text-center">
              <Calendar className="h-12 w-12 text-muted-foreground mx-auto mb-4" />
              <h3 className="font-semibold text-lg mb-2">Sem Campanhas Ativas</h3>
              <p className="text-sm text-muted-foreground">
                De momento não existem campanhas de recolha ativas. 
                Consulte esta página regularmente para novas campanhas.
              </p>
            </CardContent>
          </Card>
        )}
      </div>
    </section>
  );
}

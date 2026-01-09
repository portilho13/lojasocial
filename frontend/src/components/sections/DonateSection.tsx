import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { 
  MapPin, 
  Clock, 
  Package, 
  Heart,
  Mail,
  Phone,
  Building2
} from "lucide-react";
import Link from "next/link";

const donationItems = [
  "Alimentos não perecíveis (arroz, massa, conservas, cereais)",
  "Produtos de higiene pessoal (sabonete, pasta de dentes, champô)",
  "Produtos de limpeza doméstica",
  "Material escolar",
  "Roupa em bom estado",
];

const donationSteps = [
  {
    icon: Package,
    title: "Prepare a Doação",
    description: "Reúna os produtos que deseja doar. Verifique as datas de validade dos alimentos.",
  },
  {
    icon: MapPin,
    title: "Entregue no IPCA",
    description: "Dirija-se aos Serviços de Ação Social no Campus do IPCA em Barcelos.",
  },
  {
    icon: Heart,
    title: "Faça a Diferença",
    description: "A sua doação será distribuída a estudantes que mais precisam.",
  },
];

export function DonateSection() {
  return (
    <section id="doar" className="py-20 bg-muted/50">
      <div className="container mx-auto px-4">
        <div className="max-w-3xl mx-auto text-center mb-12">
          <h2 className="text-3xl md:text-4xl font-bold mb-4">
            Como Doar
          </h2>
          <p className="text-lg text-muted-foreground">
            A sua contribuição faz a diferença na vida dos estudantes do IPCA. 
            Saiba como pode ajudar através de doações de bens ou apoio às nossas campanhas.
          </p>
        </div>

        {/* Donation Steps */}
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-12">
          {donationSteps.map((step, index) => (
            <Card key={index} className="text-center border-none shadow-lg">
              <CardContent className="pt-6">
                <div className="rounded-full bg-primary/10 w-16 h-16 flex items-center justify-center mx-auto mb-4">
                  <step.icon className="h-8 w-8 text-primary" />
                </div>
                <div className="text-sm font-medium text-primary mb-2">
                  Passo {index + 1}
                </div>
                <h3 className="font-semibold text-lg mb-2">{step.title}</h3>
                <p className="text-sm text-muted-foreground">{step.description}</p>
              </CardContent>
            </Card>
          ))}
        </div>

        <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
          {/* What to Donate */}
          <Card>
            <CardHeader>
              <CardTitle className="flex items-center gap-2">
                <Package className="h-5 w-5 text-primary" />
                O Que Pode Doar
              </CardTitle>
            </CardHeader>
            <CardContent>
              <ul className="space-y-3">
                {donationItems.map((item, index) => (
                  <li key={index} className="flex items-start gap-3">
                    <div className="rounded-full bg-primary/10 p-1 mt-0.5">
                      <Heart className="h-3 w-3 text-primary" />
                    </div>
                    <span className="text-sm text-muted-foreground">{item}</span>
                  </li>
                ))}
              </ul>
              <div className="mt-6 p-4 bg-primary/5 rounded-lg">
                <p className="text-sm text-muted-foreground">
                  <strong>Nota:</strong> Aceitamos doações de particulares, empresas e organizações. 
                  Para doações de maior volume, por favor contacte-nos previamente.
                </p>
              </div>
            </CardContent>
          </Card>

          {/* Where and When */}
          <Card>
            <CardHeader>
              <CardTitle className="flex items-center gap-2">
                <Building2 className="h-5 w-5 text-primary" />
                Onde e Quando Entregar
              </CardTitle>
            </CardHeader>
            <CardContent className="space-y-6">
              <div className="space-y-4">
                <div className="flex items-start gap-3">
                  <MapPin className="h-5 w-5 text-primary flex-shrink-0 mt-0.5" />
                  <div>
                    <h4 className="font-medium">Localização</h4>
                    <p className="text-sm text-muted-foreground">
                      Serviços de Ação Social (SAS)<br />
                      Campus do IPCA<br />
                      4750-810 Barcelos, Portugal
                    </p>
                  </div>
                </div>

                <div className="flex items-start gap-3">
                  <Clock className="h-5 w-5 text-primary flex-shrink-0 mt-0.5" />
                  <div>
                    <h4 className="font-medium">Horário de Funcionamento</h4>
                    <p className="text-sm text-muted-foreground">
                      Segunda a Sexta-feira<br />
                      09:00 - 12:30 | 14:00 - 17:30
                    </p>
                  </div>
                </div>

                <div className="flex items-start gap-3">
                  <Mail className="h-5 w-5 text-primary flex-shrink-0 mt-0.5" />
                  <div>
                    <h4 className="font-medium">Email</h4>
                    <a 
                      href="mailto:sas@ipca.pt" 
                      className="text-sm text-primary hover:underline"
                    >
                      sas@ipca.pt
                    </a>
                  </div>
                </div>

                <div className="flex items-start gap-3">
                  <Phone className="h-5 w-5 text-primary flex-shrink-0 mt-0.5" />
                  <div>
                    <h4 className="font-medium">Telefone</h4>
                    <a 
                      href="tel:+351253802260" 
                      className="text-sm text-primary hover:underline"
                    >
                      +351 253 802 260
                    </a>
                  </div>
                </div>
              </div>

              <Button className="w-full" size="lg" asChild>
                <Link href="mailto:sas@ipca.pt?subject=Doação para Loja Social">
                  <Mail className="mr-2 h-5 w-5" />
                  Contactar para Doar
                </Link>
              </Button>
            </CardContent>
          </Card>
        </div>
      </div>
    </section>
  );
}

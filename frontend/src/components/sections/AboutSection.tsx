import { Card, CardContent } from "@/components/ui/card";
import { Heart, Package, Calendar, Users } from "lucide-react";

const features = [
  {
    icon: Heart,
    title: "Apoio Social",
    description: "Disponibilização gratuita de bens de primeira necessidade a estudantes em situação de vulnerabilidade.",
  },
  {
    icon: Package,
    title: "Bens Essenciais",
    description: "Alimentos, produtos de higiene pessoal e de limpeza provenientes de doações da comunidade.",
  },
  {
    icon: Calendar,
    title: "Apoio Regular",
    description: "Agendamento mensal de entregas para garantir apoio contínuo aos estudantes beneficiários.",
  },
  {
    icon: Users,
    title: "Comunidade Solidária",
    description: "Campanhas de recolha dinamizadas pelo IPCA com o apoio de particulares, empresas e organizações.",
  },
];

export function AboutSection() {
  return (
    <section id="sobre" className="py-20 bg-background">
      <div className="container mx-auto px-4">
        <div className="max-w-3xl mx-auto text-center mb-12">
          <h2 className="text-3xl md:text-4xl font-bold mb-4">
            Sobre a Loja Social
          </h2>
          <p className="text-lg text-muted-foreground">
            A Loja Social funciona como um espaço de acolhimento gerido pelos Serviços de Ação Social (SAS) do IPCA, 
            respondendo às necessidades da comunidade académica com especial foco nos estudantes em maior vulnerabilidade.
          </p>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
          {features.map((feature, index) => (
            <Card key={index} className="border-none shadow-lg hover:shadow-xl transition-shadow">
              <CardContent className="pt-6">
                <div className="rounded-full bg-primary/10 w-12 h-12 flex items-center justify-center mb-4">
                  <feature.icon className="h-6 w-6 text-primary" />
                </div>
                <h3 className="font-semibold text-lg mb-2">{feature.title}</h3>
                <p className="text-sm text-muted-foreground">{feature.description}</p>
              </CardContent>
            </Card>
          ))}
        </div>

        <div className="mt-16 bg-primary/5 rounded-2xl p-8 md:p-12">
          <div className="max-w-3xl mx-auto text-center">
            <h3 className="text-2xl font-bold mb-4">O Nosso Compromisso</h3>
            <p className="text-muted-foreground">
              A Loja Social do IPCA opera como uma rede de partilha e solidariedade. 
              Os bens são recolhidos através de campanhas ou doações diretas e posteriormente 
              distribuídos gratuitamente a membros da comunidade académica que comprovem carências socioeconómicas. 
              O nosso objetivo é garantir que nenhum estudante fique sem apoio devido à sua situação económica.
            </p>
          </div>
        </div>
      </div>
    </section>
  );
}

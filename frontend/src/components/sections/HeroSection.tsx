import { Button } from "@/components/ui/button";
import { Heart, Users } from "lucide-react";
import Link from "next/link";

export function HeroSection() {
  return (
    <section className="relative overflow-hidden bg-gradient-to-br from-primary/5 via-background to-primary/10 py-20 md:py-32">
      <div className="container mx-auto px-4">
        <div className="max-w-3xl mx-auto text-center space-y-8">
          <div className="inline-flex items-center gap-2 rounded-full bg-primary/10 px-4 py-2 text-sm font-medium text-primary">
            <Heart className="h-4 w-4" />
            <span>Solidariedade em Ação</span>
          </div>
          
          <h1 className="text-4xl md:text-5xl lg:text-6xl font-bold tracking-tight">
            Loja Social{" "}
            <span className="text-primary">IPCA</span>
          </h1>
          
          <p className="text-lg md:text-xl text-muted-foreground max-w-2xl mx-auto">
            Uma iniciativa de responsabilidade social do Instituto Politécnico do Cávado e do Ave, 
            criada para apoiar a comunidade académica em situação de vulnerabilidade económica e social.
          </p>
          
          <div className="flex flex-col sm:flex-row gap-4 justify-center">
            <Button size="lg" asChild>
              <Link href="#doar">
                <Heart className="mr-2 h-5 w-5" />
                Quero Doar
              </Link>
            </Button>
            <Button size="lg" variant="outline" asChild>
              <Link href="#sobre">
                <Users className="mr-2 h-5 w-5" />
                Saber Mais
              </Link>
            </Button>
          </div>
        </div>
      </div>
      
      {/* Decorative elements */}
      <div className="absolute top-1/2 left-0 -translate-y-1/2 -translate-x-1/2 w-96 h-96 bg-primary/5 rounded-full blur-3xl" />
      <div className="absolute top-1/2 right-0 translate-x-1/2 -translate-y-1/2 w-96 h-96 bg-primary/5 rounded-full blur-3xl" />
    </section>
  );
}

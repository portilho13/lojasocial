import { Button } from "@/components/ui/button";
import { Heart, Users } from "lucide-react";
import Link from "next/link";
import Image from "next/image";

export function HeroSection() {
  return (
    <section className="relative overflow-hidden py-20 md:py-32">
      {/* Banner Background */}
      <div className="absolute inset-0 z-0">
        <Image
          src="/banner.png"
          alt="Loja Social IPCA Banner"
          fill
          className="object-cover"
          priority
        />
        {/* Dark overlay for text readability */}
        <div className="absolute inset-0 bg-primary/80" />
      </div>
      <div className="container mx-auto px-4 relative z-10">
        <div className="max-w-3xl mx-auto text-center space-y-8">
          <h1 className="text-4xl md:text-5xl lg:text-6xl font-bold tracking-tight text-white">
            Loja Social{" "}
            <span className="text-secondary">IPCA</span>
          </h1>
          
          <p className="text-lg md:text-xl text-white/90 max-w-2xl mx-auto">
            Uma iniciativa de responsabilidade social do Instituto Politécnico do Cávado e do Ave, 
            criada para apoiar a comunidade académica em situação de vulnerabilidade económica e social.
          </p>
          
          <div className="flex flex-col sm:flex-row gap-4 justify-center">
            <Button size="lg" className="bg-secondary text-primary hover:bg-secondary/90" asChild>
              <Link href="#doar">
                <Heart className="mr-2 h-5 w-5" />
                Quero Doar
              </Link>
            </Button>
            <Button size="lg" className="border-2 border-white bg-transparent text-white hover:bg-white hover:text-primary" asChild>
              <Link href="#sobre">
                <Users className="mr-2 h-5 w-5" />
                Saber Mais
              </Link>
            </Button>
          </div>
        </div>
      </div>
      
      </section>
  );
}

import { Card, CardContent } from "@/components/ui/card";
import { Mail, Phone, MapPin, ExternalLink } from "lucide-react";
import { Button } from "@/components/ui/button";
import Link from "next/link";

export function ContactSection() {
  return (
    <section id="contacto" className="py-20 bg-background">
      <div className="container mx-auto px-4">
        <div className="max-w-3xl mx-auto text-center mb-12">
          <h2 className="text-3xl md:text-4xl font-bold mb-4">
            Contacte-nos
          </h2>
          <p className="text-lg text-muted-foreground">
            Tem dúvidas ou precisa de mais informações? 
            Entre em contacto com os Serviços de Ação Social do IPCA.
          </p>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-6 max-w-4xl mx-auto">
          <Card className="text-center hover:shadow-lg transition-shadow">
            <CardContent className="pt-6">
              <div className="rounded-full bg-primary/10 w-12 h-12 flex items-center justify-center mx-auto mb-4">
                <Mail className="h-6 w-6 text-primary" />
              </div>
              <h3 className="font-semibold mb-2">Email</h3>
              <a 
                href="mailto:sas@ipca.pt" 
                className="text-sm text-muted-foreground hover:text-primary transition-colors"
              >
                sas@ipca.pt
              </a>
            </CardContent>
          </Card>

          <Card className="text-center hover:shadow-lg transition-shadow">
            <CardContent className="pt-6">
              <div className="rounded-full bg-primary/10 w-12 h-12 flex items-center justify-center mx-auto mb-4">
                <Phone className="h-6 w-6 text-primary" />
              </div>
              <h3 className="font-semibold mb-2">Telefone</h3>
              <a 
                href="tel:+351253802260" 
                className="text-sm text-muted-foreground hover:text-primary transition-colors"
              >
                +351 253 802 260
              </a>
            </CardContent>
          </Card>

          <Card className="text-center hover:shadow-lg transition-shadow">
            <CardContent className="pt-6">
              <div className="rounded-full bg-primary/10 w-12 h-12 flex items-center justify-center mx-auto mb-4">
                <MapPin className="h-6 w-6 text-primary" />
              </div>
              <h3 className="font-semibold mb-2">Morada</h3>
              <p className="text-sm text-muted-foreground">
                Campus do IPCA<br />
                4750-810 Barcelos
              </p>
            </CardContent>
          </Card>
        </div>

        <div className="mt-12 text-center">
          <Button variant="outline" size="lg" asChild>
            <Link href="https://portal.sas.ipca.pt/dashboard" target="_blank" rel="noopener noreferrer">
              <ExternalLink className="mr-2 h-5 w-5" />
              Visitar Website dos SAS
            </Link>
          </Button>
        </div>
      </div>
    </section>
  );
}

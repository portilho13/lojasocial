import Link from "next/link";
import Image from "next/image";
import { Mail, Phone, MapPin } from "lucide-react";
import { Separator } from "@/components/ui/separator";

export function Footer() {
  return (
    <footer className="bg-primary text-primary-foreground">
      <div className="container mx-auto px-4 py-12">
        <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
          {/* Logo and Description */}
          <div className="space-y-4">
            <Image
              src="/SAS_IPCA_LOGO.png"
              alt="Loja Social IPCA"
              width={180}
              height={40}
              className="h-10 w-auto"
            />
            <p className="text-sm text-primary-foreground/80">
              A Loja Social é uma iniciativa de responsabilidade social do IPCA,
              criada para apoiar a comunidade académica em situação de vulnerabilidade.
            </p>
          </div>

          {/* Quick Links */}
          <div className="space-y-4">
            <h3 className="font-semibold text-lg">Links Rápidos</h3>
            <nav className="flex flex-col gap-2">
              <Link href="#sobre" className="text-sm text-primary-foreground/80 hover:text-primary-foreground transition-colors">
                Sobre Nós
              </Link>
              <Link href="#campanhas" className="text-sm text-primary-foreground/80 hover:text-primary-foreground transition-colors">
                Campanhas
              </Link>
              <Link href="#stock" className="text-sm text-primary-foreground/80 hover:text-primary-foreground transition-colors">
                Stock Disponível
              </Link>
              <Link href="#doar" className="text-sm text-primary-foreground/80 hover:text-primary-foreground transition-colors">
                Como Doar
              </Link>
            </nav>
          </div>

          {/* Contact Info */}
          <div className="space-y-4">
            <h3 className="font-semibold text-lg">Contacto</h3>
            <div className="space-y-3">
              <div className="flex items-center gap-3 text-sm text-primary-foreground/80">
                <MapPin className="h-4 w-4 flex-shrink-0" />
                <span>Campus do IPCA, Barcelos, Portugal</span>
              </div>
              <div className="flex items-center gap-3 text-sm text-primary-foreground/80">
                <Mail className="h-4 w-4 flex-shrink-0" />
                <a href="mailto:sas@ipca.pt" className="hover:text-primary-foreground transition-colors">
                  sas@ipca.pt
                </a>
              </div>
              <div className="flex items-center gap-3 text-sm text-primary-foreground/80">
                <Phone className="h-4 w-4 flex-shrink-0" />
                <a href="tel:+351253802260" className="hover:text-primary-foreground transition-colors">
                  +351 253 802 260
                </a>
              </div>
            </div>
          </div>
        </div>

        <Separator className="my-8 bg-primary-foreground/20" />

        <div className="flex flex-col md:flex-row justify-between items-center gap-4 text-sm text-primary-foreground/60">
          <p>&copy; {new Date().getFullYear()} Loja Social IPCA. Todos os direitos reservados.</p>
          <p>Desenvolvido por estudantes do IPCA</p>
        </div>
      </div>
    </footer>
  );
}

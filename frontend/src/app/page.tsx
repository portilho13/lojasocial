import { Header } from "@/components/layout/Header";
import { Footer } from "@/components/layout/Footer";
import { HeroSection } from "@/components/sections/HeroSection";
import { AboutSection } from "@/components/sections/AboutSection";
import { CampaignsSection } from "@/components/sections/CampaignsSection";
import { StockSection } from "@/components/sections/StockSection";
import { DonateSection } from "@/components/sections/DonateSection";
import { ContactSection } from "@/components/sections/ContactSection";
import { getStockSummary, getCampaigns } from "@/lib/api";

export default async function Home() {
  const [stockSummary, campaigns] = await Promise.all([
    getStockSummary(),
    getCampaigns(),
  ]);

  return (
    <div className="min-h-screen flex flex-col">
      <Header />
      <main className="flex-1">
        <HeroSection />
        <AboutSection />
        <CampaignsSection campaigns={campaigns} />
        <StockSection stockSummary={stockSummary} />
        <DonateSection />
        <ContactSection />
      </main>
      <Footer />
    </div>
  );
}

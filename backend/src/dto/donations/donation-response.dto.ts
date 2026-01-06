import { Donation, Donor, DonationItem, Product, Campaign, DonorType } from '@prisma/client';

type DonationWithRelations = Donation & {
  donor: Donor;
  campaign?: Campaign | null;
  items: (DonationItem & {
    product: Product & { stockBatches?: { expiryDate?: Date | null }[] };
  })[];
};

export class DonationResponseDto {
  id: Donation['id'];
  date: Donation['date'];
  donorName: NonNullable<Donor['name']>;
  donorType: DonorType;
  campaignTitle?: Campaign['title'];
  items: {
    productId: Product['id'];
    productName: Product['name'];
    quantity: DonationItem['quantity'];
    expiryDate?: Date;
  }[];

  constructor(donation: DonationWithRelations) {
    this.id = donation.id;
    this.date = donation.date;
    this.donorName = donation.donor.name || 'Anónimo';
    this.donorType = donation.donor.type;
    this.campaignTitle = donation.campaign?.title;
    this.items = donation.items.map((item) => ({
      productId: item.productId,
      productName: item.product.name,
      quantity: item.quantity,
      expiryDate: item.product.stockBatches?.[0]?.expiryDate ?? undefined,
    }));
  }
}

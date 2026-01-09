import { Donor } from '@prisma/client';

export class DonorResponseDto {
  id: Donor['id'];
  name: NonNullable<Donor['name']>;
  nif?: Donor['nif'];
  type: Donor['type'];
  contact?: Donor['contact'];
  totalDonations: number;

  constructor(donor: Donor, totalDonations: number = 0) {
    this.id = donor.id;
    this.name = donor.name || 'Anónimo';
    this.nif = donor.nif || undefined;
    this.type = donor.type;
    this.contact = donor.contact || undefined;
    this.totalDonations = totalDonations;
  }
}
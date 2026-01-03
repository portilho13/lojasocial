import { DonationRequest } from "@prisma/client";

export class DonationResponseDto {
  id: DonationRequest['id'];
  studentId: DonationRequest['studentId'];
  status: DonationRequest['status'];
  deliveryDate: DonationRequest['deliveryDate'];
  notes: DonationRequest['notes'];

  constructor(donation: DonationRequest) {
    this.id = donation.id;
    this.studentId = donation.studentId;
    this.status = donation.status;
    this.deliveryDate = donation.deliveryDate;
    this.notes = donation.notes;
  }
}
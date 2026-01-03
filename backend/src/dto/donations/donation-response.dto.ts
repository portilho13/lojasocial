import { DonationRequest, Student, DeliveryProduct, Product } from "@prisma/client";

type DonationWithRelations = DonationRequest & {
  student: Student;
  deliveryProducts: (DeliveryProduct & {
    product: Product;
  })[];
};

export class DonationResponseDto {
  id: DonationRequest['id'];
  studentId: DonationRequest['studentId'];
  studentName: Student['name'];
  status: DonationRequest['status'];
  deliveryDate: DonationRequest['deliveryDate'];
  notes: DonationRequest['notes'];
  products: {
    productId: Product['id'];
    productName: Product['name'];
    quantity: DeliveryProduct['deliveredQuantity'];
  }[];

  constructor(donation: DonationWithRelations) {
    this.id = donation.id;
    this.studentId = donation.studentId;
    this.studentName = donation.student.name;
    this.status = donation.status;
    this.deliveryDate = donation.deliveryDate;
    this.notes = donation.notes;
    this.products = donation.deliveryProducts.map(dp => ({
      productId: dp.productId,
      productName: dp.product.name,
      quantity: dp.deliveredQuantity,
    }));
  }
}
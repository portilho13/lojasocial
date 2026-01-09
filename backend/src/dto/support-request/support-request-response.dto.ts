import { RequestStatus, SupportRequest, RequestItem, Product } from '@prisma/client';

export class SupportRequestResponseDto {
    id: string;
    date: Date;
    status: RequestStatus;
    observation: string | null;
    studentId: string;
    items: {
        id: string;
        productId: string;
        productName: string;
        qtyRequested: number;
        qtyDelivered: number | null;
        observation: string | null;
    }[];

    constructor(request: SupportRequest & { items: (RequestItem & { product: Product })[] }) {
        this.id = request.id;
        this.date = request.date;
        this.status = request.status;
        this.observation = request.observation;
        this.studentId = request.studentId;
        this.items = request.items.map(item => ({
            id: item.id,
            productId: item.productId,
            productName: item.product.name,
            qtyRequested: item.qtyRequested,
            qtyDelivered: item.qtyDelivered,
            observation: item.observation,
        }));
    }
}

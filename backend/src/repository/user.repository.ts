import { Injectable } from "@nestjs/common";
import { Prisma, User } from "@prisma/client";
import { PrismaService } from "prisma/prisma.service";

@Injectable()
export class UserRepository {
    constructor(private readonly prisma: PrismaService) { }

    public async getUserByEmail(email: User["email"]) {
        return this.prisma.user.findUnique({
            where: { email }
        })
    }

    public async getUserById(id: string) {
        return this.prisma.user.findUnique({
            where: { id }
        })
    }

    public async createUser(data: Prisma.UserCreateInput) {
        return this.prisma.user.create({
            data
        })
    }

    public async updateUser(id: string, data: Prisma.UserUpdateInput) {
        return this.prisma.user.update({
            where: { id },
            data
        })
    }
}

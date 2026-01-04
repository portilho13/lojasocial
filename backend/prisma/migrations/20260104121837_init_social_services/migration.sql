/*
  Warnings:

  - You are about to drop the `Campaign` table. If the table is not empty, all the data it contains will be lost.
  - You are about to drop the `DeliveryProduct` table. If the table is not empty, all the data it contains will be lost.
  - You are about to drop the `Donation` table. If the table is not empty, all the data it contains will be lost.
  - You are about to drop the `DonationProduct` table. If the table is not empty, all the data it contains will be lost.
  - You are about to drop the `DonationRequest` table. If the table is not empty, all the data it contains will be lost.
  - You are about to drop the `Product` table. If the table is not empty, all the data it contains will be lost.
  - You are about to drop the `ProductType` table. If the table is not empty, all the data it contains will be lost.
  - You are about to drop the `Schedule` table. If the table is not empty, all the data it contains will be lost.
  - You are about to drop the `Stock` table. If the table is not empty, all the data it contains will be lost.
  - You are about to drop the `StockHistory` table. If the table is not empty, all the data it contains will be lost.
  - You are about to drop the `Student` table. If the table is not empty, all the data it contains will be lost.
  - You are about to drop the `User` table. If the table is not empty, all the data it contains will be lost.

*/
-- CreateEnum
CREATE TYPE "UserRole" AS ENUM ('ADMIN', 'VOLUNTARIO', 'STAFF');

-- CreateEnum
CREATE TYPE "StudentStatus" AS ENUM ('ATIVO', 'SUSPENSO', 'GRADUADO');

-- CreateEnum
CREATE TYPE "DonorType" AS ENUM ('EMPRESA', 'INDIVIDUAL', 'ANONIMO');

-- CreateEnum
CREATE TYPE "RequestStatus" AS ENUM ('PENDENTE', 'APROVADO', 'ENTREGUE', 'CANCELADO');

-- DropForeignKey
ALTER TABLE "DeliveryProduct" DROP CONSTRAINT "DeliveryProduct_donationRequestId_fkey";

-- DropForeignKey
ALTER TABLE "DeliveryProduct" DROP CONSTRAINT "DeliveryProduct_productId_fkey";

-- DropForeignKey
ALTER TABLE "Donation" DROP CONSTRAINT "Donation_campaignId_fkey";

-- DropForeignKey
ALTER TABLE "DonationProduct" DROP CONSTRAINT "DonationProduct_donationId_fkey";

-- DropForeignKey
ALTER TABLE "DonationProduct" DROP CONSTRAINT "DonationProduct_productId_fkey";

-- DropForeignKey
ALTER TABLE "DonationRequest" DROP CONSTRAINT "DonationRequest_donationId_fkey";

-- DropForeignKey
ALTER TABLE "DonationRequest" DROP CONSTRAINT "DonationRequest_scheduleId_fkey";

-- DropForeignKey
ALTER TABLE "DonationRequest" DROP CONSTRAINT "DonationRequest_studentId_fkey";

-- DropForeignKey
ALTER TABLE "DonationRequest" DROP CONSTRAINT "DonationRequest_userId_fkey";

-- DropForeignKey
ALTER TABLE "Product" DROP CONSTRAINT "Product_productTypeId_fkey";

-- DropForeignKey
ALTER TABLE "Stock" DROP CONSTRAINT "Stock_productId_fkey";

-- DropForeignKey
ALTER TABLE "Stock" DROP CONSTRAINT "Stock_userId_fkey";

-- DropForeignKey
ALTER TABLE "StockHistory" DROP CONSTRAINT "StockHistory_stockId_fkey";

-- DropTable
DROP TABLE "Campaign";

-- DropTable
DROP TABLE "DeliveryProduct";

-- DropTable
DROP TABLE "Donation";

-- DropTable
DROP TABLE "DonationProduct";

-- DropTable
DROP TABLE "DonationRequest";

-- DropTable
DROP TABLE "Product";

-- DropTable
DROP TABLE "ProductType";

-- DropTable
DROP TABLE "Schedule";

-- DropTable
DROP TABLE "Stock";

-- DropTable
DROP TABLE "StockHistory";

-- DropTable
DROP TABLE "Student";

-- DropTable
DROP TABLE "User";

-- CreateTable
CREATE TABLE "estudante" (
    "id" SERIAL NOT NULL,
    "nome" TEXT NOT NULL,
    "numero_estudante" TEXT NOT NULL,
    "curso" TEXT NOT NULL,
    "ano_curricular" INTEGER NOT NULL,
    "numero_seguranca_social" TEXT,
    "contacto" TEXT,
    "email" TEXT NOT NULL,
    "status" "StudentStatus" NOT NULL DEFAULT 'ATIVO',

    CONSTRAINT "estudante_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "utilizadores" (
    "id" SERIAL NOT NULL,
    "nome" TEXT NOT NULL,
    "tipo_utilizador" "UserRole" NOT NULL DEFAULT 'VOLUNTARIO',
    "contacto" TEXT,
    "email" TEXT NOT NULL,

    CONSTRAINT "utilizadores_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "agendamento" (
    "id" SERIAL NOT NULL,
    "data_agendada" TIMESTAMP(3) NOT NULL,
    "notificacao_enviada" BOOLEAN NOT NULL DEFAULT false,
    "id_estudante" INTEGER NOT NULL,

    CONSTRAINT "agendamento_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "tipo_produto" (
    "id" SERIAL NOT NULL,
    "descricao" TEXT NOT NULL,

    CONSTRAINT "tipo_produto_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "produto" (
    "id" SERIAL NOT NULL,
    "nome_produto" TEXT NOT NULL,
    "id_tipo_produto" INTEGER NOT NULL,

    CONSTRAINT "produto_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "stock" (
    "id" SERIAL NOT NULL,
    "quantidade" INTEGER NOT NULL,
    "data_validade" TIMESTAMP(3),
    "localizacao" TEXT,
    "id_produto" INTEGER NOT NULL,

    CONSTRAINT "stock_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "doador" (
    "id" SERIAL NOT NULL,
    "nome" TEXT,
    "nif" TEXT,
    "tipo" "DonorType" NOT NULL DEFAULT 'INDIVIDUAL',
    "contacto" TEXT,

    CONSTRAINT "doador_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "campanha" (
    "id" SERIAL NOT NULL,
    "titulo" TEXT NOT NULL,
    "descricao" TEXT,
    "data_inicio" TIMESTAMP(3) NOT NULL,
    "data_fim" TIMESTAMP(3),

    CONSTRAINT "campanha_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "doacao" (
    "id" SERIAL NOT NULL,
    "data_doacao" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "id_doador" INTEGER NOT NULL,
    "id_campanha" INTEGER,

    CONSTRAINT "doacao_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "itens_doacao" (
    "id" SERIAL NOT NULL,
    "quantidade" INTEGER NOT NULL,
    "id_doacao" INTEGER NOT NULL,
    "id_produto" INTEGER NOT NULL,

    CONSTRAINT "itens_doacao_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "pedido_apoio" (
    "id" SERIAL NOT NULL,
    "data_pedido" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "estado" "RequestStatus" NOT NULL DEFAULT 'PENDENTE',
    "observacao" TEXT,
    "id_estudante" INTEGER NOT NULL,
    "id_utilizador" INTEGER,
    "id_agendamento" INTEGER,

    CONSTRAINT "pedido_apoio_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "itens_pedido" (
    "id" SERIAL NOT NULL,
    "qtd_solicitada" INTEGER NOT NULL,
    "qtd_entregue" INTEGER DEFAULT 0,
    "observacao" TEXT,
    "id_pedido" INTEGER NOT NULL,
    "id_produto" INTEGER NOT NULL,

    CONSTRAINT "itens_pedido_pkey" PRIMARY KEY ("id")
);

-- CreateIndex
CREATE UNIQUE INDEX "estudante_numero_estudante_key" ON "estudante"("numero_estudante");

-- CreateIndex
CREATE UNIQUE INDEX "estudante_email_key" ON "estudante"("email");

-- CreateIndex
CREATE UNIQUE INDEX "utilizadores_email_key" ON "utilizadores"("email");

-- CreateIndex
CREATE UNIQUE INDEX "pedido_apoio_id_agendamento_key" ON "pedido_apoio"("id_agendamento");

-- AddForeignKey
ALTER TABLE "agendamento" ADD CONSTRAINT "agendamento_id_estudante_fkey" FOREIGN KEY ("id_estudante") REFERENCES "estudante"("id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "produto" ADD CONSTRAINT "produto_id_tipo_produto_fkey" FOREIGN KEY ("id_tipo_produto") REFERENCES "tipo_produto"("id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "stock" ADD CONSTRAINT "stock_id_produto_fkey" FOREIGN KEY ("id_produto") REFERENCES "produto"("id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "doacao" ADD CONSTRAINT "doacao_id_doador_fkey" FOREIGN KEY ("id_doador") REFERENCES "doador"("id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "doacao" ADD CONSTRAINT "doacao_id_campanha_fkey" FOREIGN KEY ("id_campanha") REFERENCES "campanha"("id") ON DELETE SET NULL ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "itens_doacao" ADD CONSTRAINT "itens_doacao_id_doacao_fkey" FOREIGN KEY ("id_doacao") REFERENCES "doacao"("id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "itens_doacao" ADD CONSTRAINT "itens_doacao_id_produto_fkey" FOREIGN KEY ("id_produto") REFERENCES "produto"("id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "pedido_apoio" ADD CONSTRAINT "pedido_apoio_id_estudante_fkey" FOREIGN KEY ("id_estudante") REFERENCES "estudante"("id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "pedido_apoio" ADD CONSTRAINT "pedido_apoio_id_utilizador_fkey" FOREIGN KEY ("id_utilizador") REFERENCES "utilizadores"("id") ON DELETE SET NULL ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "pedido_apoio" ADD CONSTRAINT "pedido_apoio_id_agendamento_fkey" FOREIGN KEY ("id_agendamento") REFERENCES "agendamento"("id") ON DELETE SET NULL ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "itens_pedido" ADD CONSTRAINT "itens_pedido_id_pedido_fkey" FOREIGN KEY ("id_pedido") REFERENCES "pedido_apoio"("id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "itens_pedido" ADD CONSTRAINT "itens_pedido_id_produto_fkey" FOREIGN KEY ("id_produto") REFERENCES "produto"("id") ON DELETE RESTRICT ON UPDATE CASCADE;

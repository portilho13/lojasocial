/*
  Warnings:

  - The primary key for the `campanha` table will be changed. If it partially fails, the table could be left without primary key constraint.
  - The primary key for the `doacao` table will be changed. If it partially fails, the table could be left without primary key constraint.
  - The primary key for the `doador` table will be changed. If it partially fails, the table could be left without primary key constraint.
  - The primary key for the `itens_doacao` table will be changed. If it partially fails, the table could be left without primary key constraint.
  - The primary key for the `itens_pedido` table will be changed. If it partially fails, the table could be left without primary key constraint.
  - The primary key for the `pedido_apoio` table will be changed. If it partially fails, the table could be left without primary key constraint.
  - The primary key for the `produto` table will be changed. If it partially fails, the table could be left without primary key constraint.
  - The primary key for the `stock` table will be changed. If it partially fails, the table could be left without primary key constraint.
  - The primary key for the `tipo_produto` table will be changed. If it partially fails, the table could be left without primary key constraint.

*/
-- DropForeignKey
ALTER TABLE "doacao" DROP CONSTRAINT "doacao_id_campanha_fkey";

-- DropForeignKey
ALTER TABLE "doacao" DROP CONSTRAINT "doacao_id_doador_fkey";

-- DropForeignKey
ALTER TABLE "itens_doacao" DROP CONSTRAINT "itens_doacao_id_doacao_fkey";

-- DropForeignKey
ALTER TABLE "itens_doacao" DROP CONSTRAINT "itens_doacao_id_produto_fkey";

-- DropForeignKey
ALTER TABLE "itens_pedido" DROP CONSTRAINT "itens_pedido_id_pedido_fkey";

-- DropForeignKey
ALTER TABLE "itens_pedido" DROP CONSTRAINT "itens_pedido_id_produto_fkey";

-- DropForeignKey
ALTER TABLE "produto" DROP CONSTRAINT "produto_id_tipo_produto_fkey";

-- DropForeignKey
ALTER TABLE "stock" DROP CONSTRAINT "stock_id_produto_fkey";

-- AlterTable
ALTER TABLE "campanha" DROP CONSTRAINT "campanha_pkey",
ALTER COLUMN "id" DROP DEFAULT,
ALTER COLUMN "id" SET DATA TYPE TEXT,
ADD CONSTRAINT "campanha_pkey" PRIMARY KEY ("id");
DROP SEQUENCE "campanha_id_seq";

-- AlterTable
ALTER TABLE "doacao" DROP CONSTRAINT "doacao_pkey",
ALTER COLUMN "id" DROP DEFAULT,
ALTER COLUMN "id" SET DATA TYPE TEXT,
ALTER COLUMN "id_doador" SET DATA TYPE TEXT,
ALTER COLUMN "id_campanha" SET DATA TYPE TEXT,
ADD CONSTRAINT "doacao_pkey" PRIMARY KEY ("id");
DROP SEQUENCE "doacao_id_seq";

-- AlterTable
ALTER TABLE "doador" DROP CONSTRAINT "doador_pkey",
ALTER COLUMN "id" DROP DEFAULT,
ALTER COLUMN "id" SET DATA TYPE TEXT,
ADD CONSTRAINT "doador_pkey" PRIMARY KEY ("id");
DROP SEQUENCE "doador_id_seq";

-- AlterTable
ALTER TABLE "itens_doacao" DROP CONSTRAINT "itens_doacao_pkey",
ALTER COLUMN "id" DROP DEFAULT,
ALTER COLUMN "id" SET DATA TYPE TEXT,
ALTER COLUMN "id_doacao" SET DATA TYPE TEXT,
ALTER COLUMN "id_produto" SET DATA TYPE TEXT,
ADD CONSTRAINT "itens_doacao_pkey" PRIMARY KEY ("id");
DROP SEQUENCE "itens_doacao_id_seq";

-- AlterTable
ALTER TABLE "itens_pedido" DROP CONSTRAINT "itens_pedido_pkey",
ALTER COLUMN "id" DROP DEFAULT,
ALTER COLUMN "id" SET DATA TYPE TEXT,
ALTER COLUMN "id_pedido" SET DATA TYPE TEXT,
ALTER COLUMN "id_produto" SET DATA TYPE TEXT,
ADD CONSTRAINT "itens_pedido_pkey" PRIMARY KEY ("id");
DROP SEQUENCE "itens_pedido_id_seq";

-- AlterTable
ALTER TABLE "pedido_apoio" DROP CONSTRAINT "pedido_apoio_pkey",
ALTER COLUMN "id" DROP DEFAULT,
ALTER COLUMN "id" SET DATA TYPE TEXT,
ADD CONSTRAINT "pedido_apoio_pkey" PRIMARY KEY ("id");
DROP SEQUENCE "pedido_apoio_id_seq";

-- AlterTable
ALTER TABLE "produto" DROP CONSTRAINT "produto_pkey",
ALTER COLUMN "id" DROP DEFAULT,
ALTER COLUMN "id" SET DATA TYPE TEXT,
ALTER COLUMN "id_tipo_produto" SET DATA TYPE TEXT,
ADD CONSTRAINT "produto_pkey" PRIMARY KEY ("id");
DROP SEQUENCE "produto_id_seq";

-- AlterTable
ALTER TABLE "stock" DROP CONSTRAINT "stock_pkey",
ALTER COLUMN "id" DROP DEFAULT,
ALTER COLUMN "id" SET DATA TYPE TEXT,
ALTER COLUMN "id_produto" SET DATA TYPE TEXT,
ADD CONSTRAINT "stock_pkey" PRIMARY KEY ("id");
DROP SEQUENCE "stock_id_seq";

-- AlterTable
ALTER TABLE "tipo_produto" DROP CONSTRAINT "tipo_produto_pkey",
ALTER COLUMN "id" DROP DEFAULT,
ALTER COLUMN "id" SET DATA TYPE TEXT,
ADD CONSTRAINT "tipo_produto_pkey" PRIMARY KEY ("id");
DROP SEQUENCE "tipo_produto_id_seq";

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
ALTER TABLE "itens_pedido" ADD CONSTRAINT "itens_pedido_id_pedido_fkey" FOREIGN KEY ("id_pedido") REFERENCES "pedido_apoio"("id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "itens_pedido" ADD CONSTRAINT "itens_pedido_id_produto_fkey" FOREIGN KEY ("id_produto") REFERENCES "produto"("id") ON DELETE RESTRICT ON UPDATE CASCADE;

/*
  Warnings:

  - The primary key for the `agendamento` table will be changed. If it partially fails, the table could be left without primary key constraint.
  - The primary key for the `estudante` table will be changed. If it partially fails, the table could be left without primary key constraint.
  - The primary key for the `utilizadores` table will be changed. If it partially fails, the table could be left without primary key constraint.

*/
-- DropForeignKey
ALTER TABLE "agendamento" DROP CONSTRAINT "agendamento_id_estudante_fkey";

-- DropForeignKey
ALTER TABLE "pedido_apoio" DROP CONSTRAINT "pedido_apoio_id_agendamento_fkey";

-- DropForeignKey
ALTER TABLE "pedido_apoio" DROP CONSTRAINT "pedido_apoio_id_estudante_fkey";

-- DropForeignKey
ALTER TABLE "pedido_apoio" DROP CONSTRAINT "pedido_apoio_id_utilizador_fkey";

-- AlterTable
ALTER TABLE "agendamento" DROP CONSTRAINT "agendamento_pkey",
ALTER COLUMN "id" DROP DEFAULT,
ALTER COLUMN "id" SET DATA TYPE TEXT,
ALTER COLUMN "id_estudante" SET DATA TYPE TEXT,
ADD CONSTRAINT "agendamento_pkey" PRIMARY KEY ("id");
DROP SEQUENCE "agendamento_id_seq";

-- AlterTable
ALTER TABLE "estudante" DROP CONSTRAINT "estudante_pkey",
ALTER COLUMN "id" DROP DEFAULT,
ALTER COLUMN "id" SET DATA TYPE TEXT,
ADD CONSTRAINT "estudante_pkey" PRIMARY KEY ("id");
DROP SEQUENCE "estudante_id_seq";

-- AlterTable
ALTER TABLE "pedido_apoio" ALTER COLUMN "id_estudante" SET DATA TYPE TEXT,
ALTER COLUMN "id_utilizador" SET DATA TYPE TEXT,
ALTER COLUMN "id_agendamento" SET DATA TYPE TEXT;

-- AlterTable
ALTER TABLE "utilizadores" DROP CONSTRAINT "utilizadores_pkey",
ALTER COLUMN "id" DROP DEFAULT,
ALTER COLUMN "id" SET DATA TYPE TEXT,
ADD CONSTRAINT "utilizadores_pkey" PRIMARY KEY ("id");
DROP SEQUENCE "utilizadores_id_seq";

-- AddForeignKey
ALTER TABLE "agendamento" ADD CONSTRAINT "agendamento_id_estudante_fkey" FOREIGN KEY ("id_estudante") REFERENCES "estudante"("id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "pedido_apoio" ADD CONSTRAINT "pedido_apoio_id_estudante_fkey" FOREIGN KEY ("id_estudante") REFERENCES "estudante"("id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "pedido_apoio" ADD CONSTRAINT "pedido_apoio_id_utilizador_fkey" FOREIGN KEY ("id_utilizador") REFERENCES "utilizadores"("id") ON DELETE SET NULL ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "pedido_apoio" ADD CONSTRAINT "pedido_apoio_id_agendamento_fkey" FOREIGN KEY ("id_agendamento") REFERENCES "agendamento"("id") ON DELETE SET NULL ON UPDATE CASCADE;

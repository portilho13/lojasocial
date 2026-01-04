/*
  Warnings:

  - Added the required column `password` to the `estudante` table without a default value. This is not possible if the table is not empty.
  - Added the required column `password` to the `utilizadores` table without a default value. This is not possible if the table is not empty.

*/
-- AlterTable
ALTER TABLE "estudante" ADD COLUMN     "hashedRefreshToken" TEXT,
ADD COLUMN     "password" TEXT NOT NULL;

-- AlterTable
ALTER TABLE "utilizadores" ADD COLUMN     "hashedRefreshToken" TEXT,
ADD COLUMN     "password" TEXT NOT NULL;

import { Controller, HttpStatus, Post, Res, ValidationPipe, UseGuards, Req, Get } from "@nestjs/common";
import type { Response } from "express";
import { AuthenticatedGuard } from "src/common/guards/authenticated.guard";
import { StudentGuard } from "src/common/guards/student.guard";
import { UserGuard } from "src/common/guards/user.guard";

@Controller('api/v1/test')
export class TestController {
    constructor(
    ) { }

    @UseGuards(AuthenticatedGuard)
    @Get()
    async test(@Res() res: Response) {
        return res.status(HttpStatus.OK).json({message: "ok"})
    }

}
"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.AppModule = void 0;
const common_1 = require("@nestjs/common");
const auth_controller_1 = require("./controller/auth.controller");
const prisma_module_1 = require("../prisma/prisma.module");
const student_repository_1 = require("./repository/student.repository");
const student_service_1 = require("./service/student.service");
const user_service_1 = require("./service/user.service");
const user_repository_1 = require("./repository/user.repository");
const jwt_1 = require("@nestjs/jwt");
const access_token_strategy_1 = require("./auth/strategies/access-token.strategy");
const refresh_token_strategy_1 = require("./auth/strategies/refresh-token.strategy");
const inventory_module_1 = require("./inventory.module");
const repositorioes = [
    student_repository_1.StudentRepository,
    user_repository_1.UserRepository,
];
const services = [
    student_service_1.StudentService,
    user_service_1.UserService,
    access_token_strategy_1.AccessTokenStrategy,
    refresh_token_strategy_1.RefreshTokenStrategy,
];
let AppModule = class AppModule {
};
exports.AppModule = AppModule;
exports.AppModule = AppModule = __decorate([
    (0, common_1.Module)({
        imports: [
            prisma_module_1.PrismaModule,
            jwt_1.JwtModule.register({
                global: true,
                secret: process.env.JWT_SECRET || 'secret',
                signOptions: { expiresIn: '15m' },
            }), prisma_module_1.PrismaModule, inventory_module_1.InventoryModule
        ],
        controllers: [auth_controller_1.AuthController],
        providers: [...repositorioes, ...services],
    })
], AppModule);
//# sourceMappingURL=app.module.js.map
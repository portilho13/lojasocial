"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.AccessTokenGuard = void 0;
const passport_1 = require("@nestjs/passport");
class AccessTokenGuard extends (0, passport_1.AuthGuard)('jwt') {
}
exports.AccessTokenGuard = AccessTokenGuard;
//# sourceMappingURL=access-token.guard.js.map
import { Strategy } from 'passport-jwt';
type JwtPayload = {
    sub: string;
    email: string;
};
declare const AccessTokenStrategy_base: new (...args: [opt: import("passport-jwt").StrategyOptionsWithRequest] | [opt: import("passport-jwt").StrategyOptionsWithoutRequest]) => Strategy & {
    validate(...args: any[]): unknown;
};
export declare class AccessTokenStrategy extends AccessTokenStrategy_base {
    constructor();
    validate(payload: JwtPayload): JwtPayload;
}
export {};

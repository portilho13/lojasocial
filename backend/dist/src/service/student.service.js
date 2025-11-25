"use strict";
var __createBinding = (this && this.__createBinding) || (Object.create ? (function(o, m, k, k2) {
    if (k2 === undefined) k2 = k;
    var desc = Object.getOwnPropertyDescriptor(m, k);
    if (!desc || ("get" in desc ? !m.__esModule : desc.writable || desc.configurable)) {
      desc = { enumerable: true, get: function() { return m[k]; } };
    }
    Object.defineProperty(o, k2, desc);
}) : (function(o, m, k, k2) {
    if (k2 === undefined) k2 = k;
    o[k2] = m[k];
}));
var __setModuleDefault = (this && this.__setModuleDefault) || (Object.create ? (function(o, v) {
    Object.defineProperty(o, "default", { enumerable: true, value: v });
}) : function(o, v) {
    o["default"] = v;
});
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __importStar = (this && this.__importStar) || (function () {
    var ownKeys = function(o) {
        ownKeys = Object.getOwnPropertyNames || function (o) {
            var ar = [];
            for (var k in o) if (Object.prototype.hasOwnProperty.call(o, k)) ar[ar.length] = k;
            return ar;
        };
        return ownKeys(o);
    };
    return function (mod) {
        if (mod && mod.__esModule) return mod;
        var result = {};
        if (mod != null) for (var k = ownKeys(mod), i = 0; i < k.length; i++) if (k[i] !== "default") __createBinding(result, mod, k[i]);
        __setModuleDefault(result, mod);
        return result;
    };
})();
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.StudentService = void 0;
const common_1 = require("@nestjs/common");
const student_repository_1 = require("../repository/student.repository");
const bcrypt = __importStar(require("bcrypt"));
const student_response_dto_1 = require("../dto/student.response.dto");
let StudentService = class StudentService {
    studentRepository;
    constructor(studentRepository) {
        this.studentRepository = studentRepository;
    }
    async signUp(dto) {
        const studentByEmail = await this.studentRepository.getStudentByEmail(dto.email);
        if (studentByEmail)
            throw new common_1.ConflictException("User already exists");
        const encryptedPassword = await bcrypt.hash(dto.password, 10);
        const userData = await this.studentRepository.createStudent({
            ...dto,
            password: encryptedPassword
        });
        return new student_response_dto_1.StudentResponseDto(userData);
    }
    async signIn(dto) {
        const student = await this.studentRepository.getStudentByEmail(dto.email);
        if (!student)
            throw new common_1.UnauthorizedException("Invalid credentials");
        const isPasswordValid = await bcrypt.compare(dto.password, student.password);
        if (!isPasswordValid)
            throw new common_1.UnauthorizedException("Invalid credentials");
        return new student_response_dto_1.StudentResponseDto(student);
    }
};
exports.StudentService = StudentService;
exports.StudentService = StudentService = __decorate([
    (0, common_1.Injectable)(),
    __metadata("design:paramtypes", [student_repository_1.StudentRepository])
], StudentService);
//# sourceMappingURL=student.service.js.map
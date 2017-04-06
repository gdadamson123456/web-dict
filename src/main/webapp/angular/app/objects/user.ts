import {Role} from "./role";

export class User {
    id: number;
    login: string;
    password: string;
    password_repeat: string;
    email: string;
    role: Role;
}
import {Role} from "./Role";

export class User {
    id: number;
    login: string;
    password: string;
    email: string;
    role: Role;
}
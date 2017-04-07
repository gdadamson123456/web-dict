import {InMemoryDbService} from "angular-in-memory-web-api";
export class InMemoryDataService implements InMemoryDbService {
    createDb() {
        let roles = [
            {id: 1, name: 'admin'},
            {id: 2, name: 'user'},
            {id: 3, name: 'kaka'}
        ];
        let users = [
            {id: 1, login: 'admin', password: 'welcome', email: 'admin@gmail.com', role: {id: 1, name: 'admin'}},
            {id: 2, login: 'user', password: 'user_welcome', email: 'user@gmail.com', role: {id: 2, name: 'user'}},
            {id: 3, login: 'ukaka', password: 'user_welcome', email: 'user@gmail.com', role: {id: 3, name: 'kaka'}}
        ];
        return {roles: roles, users: users};
    }
}

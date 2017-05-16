import {Component, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {User} from "../objects/user";
import {UserService} from "../services/user.service";

@Component({
    moduleId: module.id,
    selector: 'users-list',
    templateUrl: '../templates/users-list.component.html'
    // styleUrls: ['../styles/users-list.component.css']
})
export class UserListComponent implements OnInit {
    users: User[];
    selectedUser: User;

    constructor(private userService: UserService,
                private router: Router) {
    }

    ngOnInit(): void {
        this.getUsers();
    }

    getUsers(): void {
        this.userService
            .getUsers()
            .then(users => this.users = users);
    }

    remove(user: User): void {
        this.userService
            .remove(user.id)
            .then(() => {
                this.users = this.users.filter(u => u !== user);
                if (this.selectedUser === user) {
                    this.selectedUser = null;
                }
            });
    }

    edit(user: User): void {
        this.router.navigate(['admin/edit', user.id]);
    }

    onSelect(user: User): void {
        this.selectedUser = user;
    }
}
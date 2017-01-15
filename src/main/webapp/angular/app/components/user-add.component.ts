import "rxjs/add/operator/switchMap";
import {Component, OnInit} from "@angular/core";
import {Location} from "@angular/common";
import {User} from "../objects/user";
import {UserService} from "../services/user.service";
import {RoleService} from "../services/role.service";
import {Role} from "../objects/role";
import {AbstractEditComponent} from "./abstract-edit.component";
import {ActivatedRoute} from "@angular/router";

@Component({
    moduleId: module.id,
    selector: 'user-add',
    templateUrl: '../templates/user-edit.component.html',
    styleUrls: ['../styles/user-detail.component.css']
})
export class UserAddComponent extends AbstractEditComponent implements OnInit {
    constructor(protected userService: UserService,
                protected roleService: RoleService,
                protected route: ActivatedRoute,
                protected location: Location) {
        super(userService, roleService, route, location);
    }

    initComponent(): void {
        this.isReadonly = false;
    }

    save(): void {
        this.userService.create(this.user).then(() => this.goBack());
    }

    onRoleSelect(): void {
        this.user.role = this.selectedRole;
    }

    ngOnInit(): void {
        this.roleService.getRole(1).then(role => {
            this.user = new User();
            this.user.login = '';
            this.user.password = '';
            this.user.email = '';
            this.user.role = role;
        });
        this.roleService.getRoles().then(roles => this.roles = roles);
    }
}
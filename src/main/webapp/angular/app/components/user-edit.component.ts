import "rxjs/add/operator/switchMap";
import {Location} from "@angular/common";
import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Params} from "@angular/router";
import {AbstractEditComponent} from "./abstract-edit.component";
import {RoleService} from "../services/role.service";
import {UserService} from "../services/user.service";

@Component({
    moduleId: module.id,
    selector: 'user-edit',
    templateUrl: '../templates/user-edit.component.html'
    // styleUrls: ['../styles/user-detail.component.css']
})
export class UserEditComponent extends AbstractEditComponent implements OnInit {
    constructor(protected userService: UserService,
                protected roleService: RoleService,
                protected route: ActivatedRoute,
                protected location: Location) {
        super(userService, roleService, route, location);
    }

    initComponent(): void {
        this.isReadonly = true;
    }

    save(): void {
        this.userService.update(this.user).then(() => this.goBack());
    }

    onRoleSelect(value: string): void {
        this.roleService.getRoles().then(roles => {
            let selectedRole = roles.find(r => r.name == value);
            this.user.role = selectedRole;
        });
    }

    ngOnInit(): void {
        this.route.params
            .switchMap((params: Params) => this.userService.getUser(params['id']))
            .subscribe(user => {
                this.user = user;
            });
        this.roleService.getRoles().then(roles => this.roles = roles);
    }
}
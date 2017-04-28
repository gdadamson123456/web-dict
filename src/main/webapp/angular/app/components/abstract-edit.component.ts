import "rxjs/add/operator/switchMap";
import {Location} from "@angular/common";
import {User} from "../objects/user";
import {UserService} from "../services/user.service";
import {RoleService} from "../services/role.service";
import {Role} from "../objects/role";
import {ActivatedRoute} from "@angular/router";

export abstract class AbstractEditComponent {
    user: User;
    roles: Role[];
    isReadonly: boolean;

    constructor(protected userService: UserService,
                protected roleService: RoleService,
                protected route: ActivatedRoute,
                protected location: Location) {
        this.initComponent();
    }

    abstract initComponent(): void;

    abstract save(): void;

    abstract onRoleSelect(value: string): void;

    goBack(): void {
        this.location.back();
    }
}
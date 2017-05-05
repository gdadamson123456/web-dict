import "rxjs/add/operator/switchMap";
import {Component, OnInit, Input} from "@angular/core";
import {ActivatedRoute, Params} from "@angular/router";
import {Location} from "@angular/common";
import {User} from "../objects/user";
import {UserService} from "../services/user.service";

@Component({
    moduleId: module.id,
    selector: 'user-detail',
    templateUrl: '../templates/user-detail.component.html'
    // styleUrls: ['../styles/user-detail.component.css']
})
export class UserDetailComponent implements OnInit {
    @Input() user: User;

    constructor(private userService: UserService,
                private route: ActivatedRoute,
                private location: Location) {
    }

    ngOnInit(): void {
        if (!this.user) {
            this.route.params
                .switchMap((params: Params) => this.userService.getUser(params['id']))
                .subscribe(user => this.user = user);
        }
    }

    goBack(): void {
        this.location.back();
    }
}
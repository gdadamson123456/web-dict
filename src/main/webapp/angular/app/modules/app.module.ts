import "../rxjs-extensions";
import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {FormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import {AppRoutingModule} from "./app-routing.module";
// Imports for loading & configuring the in-memory web api
import {InMemoryWebApiModule} from "angular-in-memory-web-api";
import {InMemoryDataService} from "../services/in-memory-data.service";
import {AppComponent} from "../components/app.component";
import {UserDetailComponent} from "../components/user-detail.component";
import {UserService} from "../services/user.service";
import {UserListComponent} from "../components/users-list.component";
import {UserEditComponent} from "../components/user-edit.component";
import {UserAddComponent} from "../components/user-add.component";
import {RoleService} from "../services/role.service";

@NgModule({
    imports: [
        BrowserModule,
        FormsModule,
        HttpModule,
        InMemoryWebApiModule.forRoot(InMemoryDataService),
        AppRoutingModule
    ],
    declarations: [
        AppComponent,
        UserListComponent,
        UserDetailComponent,
        UserEditComponent,
        UserAddComponent
    ],
    providers: [UserService, RoleService],
    bootstrap: [AppComponent]
})
export class AppModule {
}
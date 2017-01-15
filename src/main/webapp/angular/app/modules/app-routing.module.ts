import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {UserListComponent} from "../components/users-list.component";
import {UserEditComponent} from "../components/user-edit.component";
import {UserAddComponent} from "../components/user-add.component";

const routes: Routes = [
    {path: '', redirectTo: '/users', pathMatch: 'full'},
    {path: 'users', component: UserListComponent},
    {path: 'users/edit/:id', component: UserEditComponent},
    {path: 'users/add', component: UserAddComponent}
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}

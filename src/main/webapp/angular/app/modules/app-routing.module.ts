import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {UserListComponent} from "../components/users-list.component";
import {UserEditComponent} from "../components/user-edit.component";
import {UserAddComponent} from "../components/user-add.component";

const routes: Routes = [
    {path: '', redirectTo: 'admin', pathMatch: 'full'},
    {path: 'admin', component: UserListComponent},
    {path: 'admin/edit/:id', component: UserEditComponent},
    {path: 'admin/add', component: UserAddComponent}
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}

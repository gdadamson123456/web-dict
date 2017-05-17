import "rxjs/add/operator/toPromise";
import {Injectable} from "@angular/core";
import {Http} from "@angular/http";
import {Role} from "../objects/role";

@Injectable()
export class RoleService {
    private rolesUrl = window.location.protocol + '//' + window.location.host+'/api/rest/roles';  // URL to web api

    constructor(private http: Http) {
    }

    getRoles(): Promise<Role[]> {
        return this.http.get(this.rolesUrl)
            .toPromise()
            .then(response => response.json() as Role[])
            .catch(this.handleError);
    }


    getRole(id: number): Promise<Role> {
        const url = `${this.rolesUrl}/${id}`;
        return this.http.get(url)
            .toPromise()
            .then(response =>
                response.json() as Role)
            .catch(this.handleError);
    }

    private handleError(error: any): Promise<any> {
        console.error('An error occurred', error); // for demo purposes only
        return Promise.reject(error.message || error);
    }
}
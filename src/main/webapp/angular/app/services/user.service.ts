import "rxjs/add/operator/toPromise";
import {Injectable} from "@angular/core";
import {Headers, Http} from "@angular/http";
import {User} from "../objects/user";

@Injectable()
export class UserService {
    private headers = new Headers({'Content-Type': 'application/json'});
    private usersUrl = window.location.protocol + '//' + window.location.host+'/api/rest/users';  // URL to web api

    constructor(private http: Http) {
    }

    getUsers(): Promise<User[]> {
        return this.http.get(this.usersUrl)
            .toPromise()
            .then(response =>
                response.json() as User[])
            .catch(this.handleError);
    }

    getUser(id: number): Promise<User> {
        const url = `${this.usersUrl}/${id}`;
        return this.http.get(url)
            .toPromise()
            .then(response =>
                response.json() as User)
            .catch(this.handleError);
    }

    remove(id: number): Promise<void> {
        const url = `${this.usersUrl}/${id}`;
        return this.http.delete(url, {headers: this.headers})
            .toPromise()
            .then(() => null)
            .catch(this.handleError);
    }

    create(user: User): Promise<User> {
        return this.http
            .post(this.usersUrl, JSON.stringify(user), {headers: this.headers})
            .toPromise()
            .then(res => res.json())
            .catch(this.handleError);
    }

    update(user: User): Promise<User> {
        const url = `${this.usersUrl}/${user.id}`;
        return this.http
            .put(url, JSON.stringify(user), {headers: this.headers})
            .toPromise()
            .then(() => user)
            .catch(this.handleError);
    }

    private handleError(error: any): Promise<any> {
        console.error('An error occurred', error); // for demo purposes only
        return Promise.reject(error.message || error);
    }
}
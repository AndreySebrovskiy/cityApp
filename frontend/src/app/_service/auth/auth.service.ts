import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Router} from "@angular/router";
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = environment.api;
  private isLoggedIn = false;

  constructor(private http: HttpClient, private router: Router) {
  }

  login(username: string, password: string) {
    const headers = new HttpHeaders().set('Authorization', 'Basic ' + btoa(username + ':' + password));

    // make the HTTP request with the authorization header
    return this.http.options(this.apiUrl, {headers}).subscribe(response => {
      // authentication successful
      this.isLoggedIn = true;
      console.log("authentication successful")
      localStorage.setItem("username", username)
      localStorage.setItem("password", password)
    }, error => {
      // authentication failed
      console.error(error);
      this.isLoggedIn = false;
    });
  }

  logout(): void {
    localStorage.removeItem("username");
    localStorage.removeItem("password");
    this.isLoggedIn = false;
    this.router.navigate(['login']);
  }

  isAuthenticated(): boolean {
    return this.isLoggedIn;
  }
}

import {AuthService} from "../../_service/auth/auth.service";
import {Component} from "@angular/core";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent {
  username = "";
  password = "";
  loginError: boolean = false;

  constructor(public authService: AuthService, private router: Router) {}

  login(): void {
    this.authService.login(this.username, this.password);
    if (this.authService.isAuthenticated()) {
      this.router.navigate(['city-list']);
    } else {
      this.loginError = true;
    }
  }
}

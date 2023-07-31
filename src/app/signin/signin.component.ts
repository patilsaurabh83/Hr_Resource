import { Component } from '@angular/core';
import { FormBuilder, FormControl, NgForm, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../model/user';
import { AuthService } from '../service/auth.service';
import { UserService } from '../service/user.service'; @Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.css']
})
export class SigninComponent {
  //   constructor(public userService: UserService, private router: Router, private activeRouter: ActivatedRoute) { }
  // isLoggedIn: boolean = false;
  // show: boolean = true;
  // addFrom: any; model = {
  //   email: '',
  //   password: ''
  // }; user: User = new User();


  // serverErrorMessages: string = '';
  // ngOnInit() {

  // } loginUser() {
  //   this.userService.login(this.user).subscribe(
  //     (res: any) => {
  //       console.log("TOken : " + res.jwtToken);
  //       let token = res.jwtToken;
  //       const role = res.role;
  //       this.isLoggedIn = true; if (token != null) {
  //         if (token == "You are already login") {
  //           alert("You have logged in successfully!");
  //         }
  //         // this.userService.removeRole();
  //         // this.userService.deleteToken();
  //         this.userService.setRoles(role);
  //         this.userService.setToken(token); if (res.role == "ADMIN") {
  //           console.log("admin-true");
  //           this.router.navigateByUrl("/admin")
  //         } else {
  //           this.router.navigateByUrl("/employee")
  //         }
  //       }
  //     },
  //     (err: any) => {
  //       if (err.error.message === 'Bad credentials') {
  //         this.serverErrorMessages = 'Invalid password !';
  //       } 
  //       else{
  //       this.serverErrorMessages = err.error.message;
  //       alert("Error in login :" + JSON.stringify(err))
  //       }
  //     }
  //   );
  // }

  isLoggedIn: boolean = false;

  show: boolean = true;

  addFrom: any;

  email: string = '';

  password: string = '';




  user: User = new User();

  serverErrorMessages: string = '';

  loginForm: any;

  FormControl = new FormControl('', Validators.required);




  constructor(

    public userService: UserService,

    private router: Router,

    private activeRouter: ActivatedRoute,

    private formBuilder: FormBuilder

  ) { }

 




  ngOnInit() {

    this.loginForm = this.formBuilder.group({

      email: new FormControl('', [

        Validators.required,

        Validators.email

      ]),




      password: new FormControl('', [

        Validators.required

      ])

    });

  }




  loginUser(form: NgForm) {

    this.userService.login(this.user).subscribe(

      (res: any) => {

        let token = res.jwtToken;

        const role = res.role;

        this.isLoggedIn = true;




        if (token != null) {

          this.userService.setRoles(role);

          this.userService.setToken(token);




          this.getUser();




          if (res.role == "ADMIN") {

            this.router.navigateByUrl("/admin")

          } else {

            this.router.navigateByUrl("/employee")

          }

        }

      },

      (err: any) => {

        if (err.status === 403) {

          this.serverErrorMessages = "Incorrect password"

        } else {

          console.log(err);

          this.serverErrorMessages = err.error.message;

          console.log("Error in login :" + JSON.stringify(err))

        }

      }

    );

  }




  // fetching user details and setting in localstorage

  getUser() {

    this.userService.getUserDetails(this.user.email).subscribe(

      (resp: any) => {

        this.userService.setUserDetails(resp);

      },

      (error: any) => {

        console.log("Error in fetching user details");

      }

    )

  }
}

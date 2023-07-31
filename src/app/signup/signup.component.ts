import { Component } from '@angular/core';
import { EmailValidator, FormBuilder, FormControl, NgForm, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../service/auth.service';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {
  // constructor(public userService:UserService,private router:Router) {}

  // serverErrorMessages: string = '';

  // onSubmit(form: NgForm) {

  //     // console.log("outside : " + JSON.stringify(form));
  //      const email = form.value.email;
  //      if (!email.toLowerCase().endsWith('@gmail.com')) {
  //       this.serverErrorMessages = 'Email address must be a Gmail account.';
  //     }

  //     this.userService.register(form.value).subscribe(
        
  //       (res: any) => {

  //         alert("new user registered..")

  //         this.resetForm(form);

  //         this.router.navigateByUrl("/login");

  //     },

  //       (err: any) => {
  //         if (err.error.message === 'No enum constant com.example.demo.entities.Role.') {
  //           this.serverErrorMessages = 'Invalid role';
  //         }
  //         else if (err.error.message === 'User has already registered') {
  //           this.serverErrorMessages = 'User has already registered. Please log in.';
  //         } 
          
  //         console.log("Something went wrong"+ JSON.stringify(err));
  //     }

  //     );

  // }

  //   resetForm(form: NgForm) {

  //     this.userService.user = {

  //       id:0,

  //       firstname: '',

  //       lastname: '',

  //       email: '',

  //       password: '',

  //       role: '',

  //       logged:''

  //   };

  //     form.resetForm();

  // }

  // public isLoggedIn(){
  //    return this.userService.isLogedIn();
  // }

  // public logOut(){
  //   this.userService.clear();
  //   this.router.navigate([""]);
  // }

  // isHomePage():boolean{
  //   return this.router.url === '';
  // }



  showSucessMessage: boolean = false;

  serverErrorMessages: string = '';

  signUpForm: any;

  constructor(

    public userService: UserService,

    private router: Router,

    private formBuilder: FormBuilder

  ) {




  }

  ngOnInit(): void {

    this.signUpForm = this.formBuilder.group({

      firstname: new FormControl('', [

        Validators.required

      ]),

      lastname: new FormControl('', [

        Validators.required

      ]),

      email: new FormControl('', [

        Validators.required,

        Validators.email,

      ]),

      password: new FormControl('', [

        Validators.required

      ]),

      role: new FormControl('', [

        Validators.required

      ])

    });

  }




  onSubmit(form: NgForm) {

    this.userService.register(form.value).subscribe(

      (res: any) => {

        alert("Registration Successful")

        this.resetForm(form);

        this.router.navigateByUrl("/signin");

      },

      (error: any) => {

        // const m = JSON.parse(error.error);

        this.serverErrorMessages = "User already registered"

        // console.log(this.serverErrorMessages);


      }

    );

  }




  resetForm(form: NgForm) {

    form.resetForm();

    this.serverErrorMessages = '';

  }


     
}



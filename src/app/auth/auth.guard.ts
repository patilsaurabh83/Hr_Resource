// import { Injectable } from '@angular/core';

// import { ActivatedRouteSnapshot, CanActivate, CanActivateFn, Router, RouterStateSnapshot, UrlTree } from '@angular/router';

// import { Observable } from 'rxjs';
// import { UserService } from '../service/user.service';



// @Injectable({

//     providedIn: 'root'

// })

// export class Authguard implements CanActivate {

//     constructor(

//         private userService: UserService,

//         private router: Router

//     ) { }

//     canActivate(

//         route: ActivatedRouteSnapshot,

//         state: RouterStateSnapshot): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {

//         if (this.userService.isLogedIn()) {

//             return true;

//         } else {

//             alert("You must be logged in to access this page");

//             this.router.navigate(['/login']);

//             return false;

//         }

//     }

// }



import { EmployeeService } from './../service/employee.service';

import { UserService } from './../service/user.service';

import { Injectable } from '@angular/core';

import { ActivatedRouteSnapshot, CanActivate, CanActivateFn, Router, RouterStateSnapshot, UrlTree } from '@angular/router';

import { Observable } from 'rxjs';




@Injectable({

  providedIn: 'root'

})

export class Authguard implements CanActivate{




  constructor(

    private userService:UserService,

    private router:Router,

    private empService:EmployeeService

    ){}




  canActivate(

    route: ActivatedRouteSnapshot,

    state: RouterStateSnapshot): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {




      const isAdmin = this.userService.getRoles()?.includes("ADMIN");

      const isEmployee = this.userService.getRoles()?.includes("EMPLOYEE");  




      if (isAdmin && !isEmployee && state.url.includes('admin')) {

        return true;

      } else if (!isAdmin && isEmployee && state.url.includes('employee')) {

        return true;

      } else {

        console.log("In else");

       

        alert("You don't have permission to access this page");

        this.router.navigate(['/signin']);

        return false;

      }

   

     

  }

 

}
import { HttpClient } from '@angular/common/http';
import { Component, numberAttribute, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { last } from 'rxjs';
import { Employee } from '../model/Employee';
import { Employee1, Location1 } from '../model/Employee copy';
import { User } from '../model/user';
import { EmployeeService } from '../service/employee.service';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-employee',
  templateUrl: './employee.component.html',
  styleUrls: ['./employee.component.css']
})
export class EmployeeComponent {

  constructor(private employeeservice: EmployeeService,private http : HttpClient,
    public userService:UserService,private router:Router){}



  locations: Location1[] = [];
    
  userName:string='';
  ngOnInit() {
    // const storedUser = localStorage.getItem('user');
    // this.user1 = storedUser ? JSON.parse(storedUser) : new User();
    // this.userName=this.user1.firstname.toUpperCase();


    this.employeeservice.getLocations().subscribe(
      (data: Location1[]) => {
        this.locations = data;
        console.log(data);

      },
      (error) => {
        console.error('Error fetching Locations:', error);
      }
    );

  }


  //to print the tables

  showTable: boolean = false;
printTable(action:String) {
  if (this.showTable) {
    this.closeTable();
  }
  else {
   
  }
}
closeTable() {
  this.showTable = false;
}


  //for form operations

  formName :String="";

  toggle(formName: string) {
    if (this.formName === formName) {
      this.formName = ''; 
    } else {
      this.formName = formName; 
    }
  }

  // for add employee operation
 
  employee: Employee1 = {
    employeeId: 0,
    firstName: '',
    lastName: '',
    email: '',
    phoneNumber: '',
    hireDate: '',
    salary: 0,
    commissionPct: 0,
    manager: {
      employeeId: 0,
    },
    department: {
      departmentId: 0,
      departmentName: '',
      location: {
        locationId: 0,
        locationName:'',
      },
    },
    jobId: {
      jobId: '',
      jobTitle: '',
      minSalary: 0,
      maxSalary: 0
    },
  };


  user1: User = {} as User;
 public logOut() {

  const storedUser = localStorage.getItem('user');
    this.user1 = storedUser ? JSON.parse(storedUser) : new User();


    this.userService.logout(this.user1.id).subscribe(

      (resp:any) => {
        console.log(this.user1);
        
        console.log(resp);

        this.userService.clear();

        this.router.navigate(['']);
      },

      (error:any) => {

        console.log(JSON.stringify(error));

      }

    )

  }


  fullName:String=""
  fetchEmployeeName() {
    if (this.employee.employeeId) {
      this.http.get(`http://localhost:1991/api/v1/public/employees/
      ${this.employee.employeeId}/name`, 
      { responseType: 'text' })
        .subscribe(
          data => {
            this.fullName = data;
          },
          error => {
            this.fullName = "Employee Does not exist"
            console.error('Error fetching employee name:', error);
          }
        );
    } else {
      this.fullName = '';
    }
  }


  oldPhoneNumber:string=""
  fetchEmployeePhoneNumber() {
    if (this.employee.employeeId) {
      this.http.get(`http://localhost:1991/api/v1/public/employees/
      ${this.employee.employeeId}/phone`, 
      { responseType: 'text' })
        .subscribe(
          data => {
            this.oldPhoneNumber = data;
          },
          error => {
            this.oldPhoneNumber = 'No data Found';
            console.error('Error fetching employee Phone number:', error);
          }
        );
    } else {
      this.oldPhoneNumber = '';
    }
  }


  oldEmail:string=""
  fetchEmployeeEmail() {
    if (this.employee.employeeId) {
      this.http.get(`http://localhost:1991/api/v1/public/employees/
      ${this.employee.employeeId}/email`, 
      { responseType: 'text' })
        .subscribe(
          data => {
            this.oldEmail = data;
          },
          error => {
            this.oldEmail='No data Found'
            console.error('Error fetching employee Email:', error);
          }
        );
    } else {
      this.oldEmail = '';
    }
  }




  updateEmpPhoneNumber( phoneNumber: string,OldPhoneNumber:string) : void {
    this.employeeservice.updateEmpPhoneNumber(this.employee.phoneNumber, this.oldPhoneNumber).subscribe(
      (response) => {
        console.log('Phone Number updated successfully');
        alert('Phone Number updated successfully');
      },
      (error: any) => {
        console.error('Error updating Phone Number:', error.error);
      }
    );
  }

  updateEmpEmail( email: string,oldEmail:string) : void {
    this.employeeservice.updateEmpEmail(this.employee.email, this.oldEmail).subscribe(
      (response) => {
        console.log('Email updated successfully');
        alert('Email updated successfully');
      },
      (error: any) => {
        console.error('Error updating Email:', error.error);
      }
    );  
  }


  updateEmpName(employeeId:number,firstName:string,lastName:string) : void {
    console.log(firstName);
    console.log(lastName);
    
    
    this.employeeservice.updateEmpName(employeeId,firstName,lastName).subscribe(
      (response) => {
        console.log('Name has updated successfully');
        alert('Name updated successfully');
      },
      (error: any) => {
        console.log('Error in updating the Name:', JSON.stringify(error.error));
      }
    );  
  }


  employees: Employee1[] = [];
  getEmployeesByLocation(locationId:number){
      this.employeeservice.getEmployeesByLocation(locationId).subscribe(
        (fetchedEmployees: Employee1[]) => {
          if(fetchedEmployees.length == 0){
            alert('No data Found for the location ID '+locationId);
          }
          else{
          this.employees = fetchedEmployees; 
          }
          
        },
        (error: any) => {
          console.error('Error fetching employees:', error);
        }
      );
      
    }

  

}

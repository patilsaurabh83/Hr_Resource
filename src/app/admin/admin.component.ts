import { HttpClient } from '@angular/common/http';
import { ThisReceiver } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Department, Employee, JobId, Location1 } from '../model/Employee';
import { JobHistory } from '../model/JobHistory';
import { User } from '../model/user';
import { AdminService } from '../service/admin.service';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})


export class AdminComponent {
  constructor(private adminservice: AdminService, private http: HttpClient,
    public userService: UserService, private router: Router) {

  }

  operationViewActive: boolean = true;
  name: string = '';




  // for table operations
  showTable: boolean = false;
  printTable(action: String) {
    if (this.showTable) {
      this.closeTable();
    } else {
      this.showTable = true;
      if (action == 'minAndMaxSal') {
        this.getSalaryStats(this.selectedDepartment);
      }

    }
  }

  closeTable() {
    this.showTable = false;
  }


  jobHistoryData: JobHistory[] = [];

  locations: Location1[] = [];

  comissionedEmployee: Employee[] = [];
  nonComissionedEmployee: Employee[] = [];
  managerDetails: Employee[] = [];
  hireDateEmployees: Employee[] = [];
  locationwiseEmployees: Employee[] = [];

  jobs: JobId[] = [];
  selectedJobId: string = '';
  selectedJobTitle: string = '';

  departments: Department[] = [];
  selectedDepartment: number = 0;

  employeeIdFilter: number = 0; // Add this property to your component
  departmentFilter: string = ''; // Add this property to your component


  userName: string = '';
  ngOnInit() {

    this.fetchComissionEmployees();
    this.fetchNonComissionEmployees();
    this.fetchAllManagerDetails();


    this.adminservice.getDepartments().subscribe(
      (data: Department[]) => {
        console.log(data)
        this.departments = data;
      },
      (error) => {
        console.error('Error fetching departments:', error);
      }
    );

    this.adminservice.getLocations().subscribe(
      (data: Location1[]) => {
        this.locations = data;
        console.log(data);

      },
      (error) => {
        console.error('Error fetching Locations:', error);
      }
    );


    this.adminservice.getJobs().subscribe(
      (data: JobId[]) => {
        this.jobs = data;
      },
      (error) => {
        console.error('Error fetching jobs:', error);
      }
    );


    this.adminservice.getAllJobHistory()
      .subscribe(
        data => {
          console.log(data);

          this.jobHistoryData = data;
        },
        error => {
          console.error('Error occurred while fetching job history:', error);
        }
      );
  }



  //for form operations

  formName: String = "";

  toggle(formName: string) {
    if (this.formName === formName) {
      this.formName = '';
    }
    else {
      this.formName = formName;
    }
  }





  firstName: string = ''
  lastName: string = ''
  email: string = ''
  phoneNumber: string = ''
  hireDate: string = ''
  salary: number = 0
  departmentId: number = 0
  jobId: string = ''

  // for add employee operation

  employee: Employee = {
    employeeId: 0,
    firstName: '',
    lastName: '',
    email: '',
    phoneNumber: '',
    hireDate: '',
    salary: 0,
    commissionPct: 0,
    manager: {
      employeeId: 0
    },
    department: {
      departmentId: 0,
      departmentName: '',
      location: {
        locationId: 0,
        locationName: '',
      },
    },
    jobId: {
      jobId: '',
      jobTitle: '',
      minSalary: 0,
      maxSalary: 0
    },
  };

  location: Location1 = {
    locationId: 0,
    streetAddress: '',
    postalCode: 0,
    city: '',
    stateProvience: '',

  }


  jobHistory: JobHistory = {
    employee: {
      employeeId: 0,
      firstName: "",
      lastName: "",
      email: "",
      phoneNumber: "",
      hireDate: "",
      salary: 0,
      commissionPct: null,
      department: {
        departmentId: 0,
        departmentName: ""
      },
      jobId: {
        jobId: "",
        jobTitle: "",
        minSalary: 0,
        maxSalary: 0
      }
    },
    job: {
      jobId: "",
      jobTitle: "",
      minSalary: 0,
      maxSalary: 0
    },
    department: {
      departmentId: 0,
      departmentName: ""
    },
    startDate: "0",
    endDate: "0"
  };






  user2: User = {} as User;
  public logOut() {

    const storedUser = localStorage.getItem('user');
    this.user2 = storedUser ? JSON.parse(storedUser) : new User();


    this.userService.logout(this.user2.id).subscribe(

      (resp: any) => {
        console.log(this.user2.id);

        console.log(resp);

        this.userService.clear();

        this.router.navigate(['']);



      },

      (error: any) => {

        console.log(error);

      }

    )

  }




  addEmployee() {
    const employee = {
      firstName: this.firstName,
      lastName: this.lastName,
      email: this.email,
      phoneNumber: this.phoneNumber,
      hireDate: this.hireDate,
      salary: this.salary,
      department: {
        departmentId: this.departmentId
      },
      jobId: {
        jobId: this.jobId
      }
    }
    this.adminservice.addEmployee(employee)
      .subscribe(
        response => {
          console.log('Employee added successfully:', response);
          alert('Employee added successfully:)');
        },
        error => {
          console.error('Error adding employee:', error);
        }
      );
  }


  // To delete the employee

  deleteEmployee(employeeId: number) {
    this.adminservice.deleteEmployee(employeeId)
      .subscribe(
        () => {
          alert("Employee deleted successfully with Employee Id: " + employeeId);
        },
        error => {
          if (error.error instanceof ErrorEvent) {
            console.error('An error occurred:', error.error.message);
          } else {
            console.error('Error deleting employee:', error);
          }

        }
      );
  }


  //for Delete employee Operation

  fullName: String = ""
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
    }
  }





  //to fetch the employees with no comission

  employees: Employee[] = [];
  fetchComissionEmployees() {
    this.adminservice.getComissionEmployees().subscribe(
      (fetchedEmployees: Employee[]) => {
        console.log(fetchedEmployees);

        this.comissionedEmployee = fetchedEmployees;
      },
      (error: any) => {
        console.error('Error fetching employees:', error);
      }
    );

  }

  // to fetch employees who don't get the comission

  fetchNonComissionEmployees() {
    this.adminservice.getNonComissionEmployees().subscribe(
      (fetchedEmployees: Employee[]) => {
        this.nonComissionedEmployee = fetchedEmployees;
      },
      (error: any) => {
        console.error('Error fetching employees:', error);
      }
    );

  }



  // top fetch employees by hire date from to hire date end
  hireDateFrom: string = ""
  hireDateTo: string = ""

  fetchHireRangeByEmployees(hireDateFrom: string, hireDateTo: string) {
    this.adminservice.fetchHireRangeByEmployees(this.hireDateFrom, this.hireDateTo).subscribe(
      (fetchedEmployees: Employee[]) => {
        this.hireDateEmployees = fetchedEmployees;
      },
      (error: any) => {
        console.error('Error fetching employees:', error);
      }
    );

  }


  // To get all the manager details

  fetchAllManagerDetails() {
    this.adminservice.fetchAllManagerDetails().subscribe(
      (fetchedEmployees: Employee[]) => {
        this.managerDetails = fetchedEmployees;
      },
      (error: any) => {
        console.error('Error fetching employees:', error);
      }
    );

  }


  // To fetch the employee details by employee Id


  deptNo: number = 0;
  deptName: String = '';
  fetchDepartmentName() {
    if (this.employee.department.departmentId) {
      this.http.get(`http://localhost:1991/api/v1/public/departments/
      ${this.employee.department.departmentId}/name`,
        { responseType: 'text' })
        .subscribe(
          data => {
            console.log(data);
            this.deptName = data;
          },
          error => {
            console.error('Error fetching department name:', error);
          }
        );
    }
  }



  fetchEmployeesByDeptId(departmentId: Number) {
    this.adminservice.fetchEmployeesByDeptId(this.employee.department.departmentId).subscribe(
      (fetchedEmployees: Employee[]) => {
        this.employees = fetchedEmployees;
      },
      (error: any) => {
        console.error('Error fetching employees:', error);
      }
    );
  }






  //to assign manager to the employee
  managerName: String = "";
  fetchManagerName() {
    if (this.employee.manager.employeeId) {
      this.http.get(`http://localhost:1991/api/v1/public/employees/
      ${this.employee.manager.employeeId}/managername`,
        { responseType: 'text' })
        .subscribe(
          data => {
            if (data != null) {
              this.managerName = data;
            }
          },
          error => {
            this.managerName = 'Manager does not exist'
            console.error('Error fetching employee name:', error);
          }
        );
    } else {
      this.managerName = 'Manager does not exist';
    }
  }


  updateManager(employeeId: number, managerId: number): void {
    this.adminservice.updateManager(this.employee.employeeId, this.employee.manager.employeeId).subscribe(
      (response) => {
        console.log('Manager updated successfully');
        alert('Manager updated successfully');
      },
      (error: any) => {
        console.error('Error updating manager:', error.error);
      }
    );
  }



  // update the department and sales percentage

  updateSalesPercentage(employeeId: number, deptId: number, commissionPct: number) {

    this.adminservice.updateDeptAndSalesPercentage(employeeId, deptId, commissionPct)
      .subscribe(
        response => {
          console.log('Sales percentage updated successfully:', response);
          alert('Sales percentage updated successfully:');
        },
        error => {
          console.error('Error updating sales percentage:', error);
          alert('Error updating sales percentage And Department');
        }
      );
  }


  // To update the department of the employee

  updateEmpDepartment(employeeId: number, departmentId: number) {
    this.adminservice.updateDepartment(employeeId, departmentId)
      .subscribe(
        () => {
          console.log('Department updated successfully');
          alert('Department updated successfully');
        },
        (error) => {
          console.error('Error updating department:', error);
          alert('Error updating department:' + error);
        }
      );
  }



  newLocationId: string = ""
  fetchLocationId() {
    console.log("Inside the method");
    if (this.employee.department.location.locationName) {
      this.http.get(`http://localhost:1991/api/v1/public/locations/${this.employee.department.location.locationName}/locationId`,
        { responseType: 'text' })
        .subscribe(
          data => {
            this.newLocationId = data;
          },
          error => {
            console.error('Error fetching Location Id:', error);
          }
        );
    } else {
      this.newLocationId = '';
    }
  }

  getEmployeesByLocation() {
    const locationId = parseInt(this.newLocationId);
    this.adminservice.getEmployeesByLocation(locationId).subscribe(
      (fetchedEmployees: Employee[]) => {
        console.log(fetchedEmployees);

        this.locationwiseEmployees = fetchedEmployees;
      },
      (error: any) => {
        console.error('Error fetching employees:', error);
      }
    );

  }


  //To assign the Job

  newJobId: string = ""
  fetchJobId() {
    if (this.selectedJobTitle) {
      console.log(this.selectedJobTitle)
      this.http.get(`http://localhost:1991/api/v1/public/job/${this.selectedJobTitle}/getJobIds`,
        { responseType: 'text' })
        .subscribe(
          data => {
            const jobTitleString = data;
            const formattedJobTitle = jobTitleString.slice(2, -2).replace(/"/g, '');
            console.log(formattedJobTitle);
            this.newJobId = formattedJobTitle;
          },
          error => {
            console.error('Error fetching Location Id:', error);
          }
        );
    } else {
      this.newJobId = '';
    }
  }

  updateJob(employeeId: number, jobId: string) {
    this.adminservice.updateEmployeeJob(employeeId, jobId)
      .subscribe(
        () => {
          console.log('Job updated successfully');
          alert('Job updated successfully');
        },
        (error) => {
          console.error('Error updating Job:', error);
          alert('Error updating Job:' + error);
        }
      );
  }


  // to get the min and max salary by department

  departmentName: string = '';
  maxSalary: number = 0;
  minSalary: number = 0;

  getSalaryStats(departmentId: number) {
    this.adminservice.getSalaryStatsByDepartmentId(departmentId).subscribe(
      (response: any) => {
        if (response != null) {
          this.departmentName = response.department_name;
          this.maxSalary = response.max_salary;
          this.minSalary = response.min_salary;
        }
        else {
          this.departmentName = response.department_name;
          this.maxSalary = 0;
          this.minSalary = 0;
        }
      },
      (error: any) => {
        console.error(error);
      }
    );
  }



  jobMinSalary: number = 0;
  jobMaxSalary: number = 0;
  getSalaryStatsByJob(selectedJobTitle: string) {
    this.adminservice.getSalaryStatsByJobId(selectedJobTitle).subscribe(
      (response: any) => {
        if (response != null) {
          console.log(response);

          this.jobMaxSalary = response.maxSalary;
          this.jobMinSalary = response.minSalary;
        }
        else {
          this.maxSalary = 0;
          this.minSalary = 0;
        }
      },
      (error: any) => {
        console.error(error);
      }
    );
  }


  getJobHistory(): void {
    this.adminservice.getAllJobHistory()
      .subscribe(
        data => {
          this.jobHistoryData = data;
        },
        error => {
          console.error('Error occurred while fetching job history:', error);
        }
      );
  }

  experience: any;
  getEmployeeExperience() {
    this.adminservice.getEmployeeExperience(this.employee.employeeId)
      .subscribe(
        (response) => {
          console.log(response);
          this.experience = response;
        },
        (error) => {
          console.error(error);
        }
      );
  }



  //to validate calender dates

  getCurrentDate(): string {
    const today = new Date();
    const year = today.getFullYear();
    const month = ('0' + (today.getMonth() + 1)).slice(-2);
    const day = ('0' + today.getDate()).slice(-2);
    return `${year}-${month}-${day}`;
  }




}

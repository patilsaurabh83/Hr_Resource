export interface Employee1 {
    employeeId: number;
    firstName: string;
    lastName: string;
    email: string;
    phoneNumber: string;
    hireDate: string;
    salary: number;
    commissionPct: number;
    manager:Manager;
    department: Department;
    jobId: JobId; 
  }
  
  export interface Department {
    departmentId: number;
    departmentName: string;
    location:Location;
    
  }

  
  export interface JobId {
    jobId: string;
    jobTitle: string;
    minSalary: number;
    maxSalary: number;
  }

  export interface Manager{
    employeeId:number;
  }

  export interface Location{
    locationId:number;
    locationName:string;
  }
  

  export interface EmployeeClass{
    employeeId: number;
    firstName: string;
    lastName: string;
    email: string;
    phoneNumber: string;
    hireDate: string;
    salary: number;
    commissionPct: number;

  } 

  export interface Location1{
    locationId:number;
    streetAddress:string;
    postalCode:number;
    city:string;
    stateProvince:string;
  }
 

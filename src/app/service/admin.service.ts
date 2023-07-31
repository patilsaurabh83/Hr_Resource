import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { Department, Employee, JobId, Location1 } from '../model/Employee';
import { JobHistory } from '../model/JobHistory';


interface SalaryStats {
  department_name: string;
  max_salary: number;
  min_salary: number;
} 

interface SalaryRangeResponse {
  minSalary: number;
  maxSalary: number;
}

@Injectable({
  providedIn: 'root'
})



export class AdminService {
  private apiUrl = 'http://localhost:1991/api/v1/admin/employees';
  private baseUrl = 'http://localhost:1991/api/v1/admin/employees';

  constructor(private http: HttpClient) {}

  addEmployee(formData: any): Observable<any> {
    const url = `${this.apiUrl}`;
    return this.http.post(url, formData, {responseType: 'text'});
  }

  deleteEmployee(employeeId: number): Observable<string> {
    return this.http.delete(`http://localhost:1991/api/v1/admin/employees/${employeeId}`,{ responseType: 'text' } );
  }

  getComissionEmployees(): Observable<Employee[]> {
    return this.http.get<Employee[]>('http://localhost:1991/api/v1/admin/employees/findAllEmployeeWithCommission');
  }


  getNonComissionEmployees(): Observable<Employee[]> {
    return this.http.get<Employee[]>('http://localhost:1991/api/v1/admin/employees/findAllEmployeeWithNoCommission');
  }


  fetchHireRangeByEmployees(hireDateFrom:string, hireDateTo:string): Observable<Employee[]> {
    const url = `http://localhost:1991/api/v1/admin/employees/listallemployeebyhiredate/${hireDateFrom}/${hireDateTo}`;
    return this.http.get<Employee[]>(url);
  }


  fetchAllManagerDetails(): Observable<Employee[]> {
    return this.http.get<Employee[]>('http://localhost:1991/api/v1/public/employees/listallmanagerdetails');
  }

  fetchEmployeesByDeptId(departmentId:Number): Observable<Employee[]> {
    return this.http.get<Employee[]>(`http://localhost:1991/api/v1/public/employees/listAllEmployees/${departmentId}`);
  }


  updateManager(employeeId: number, managerId: number): Observable<String> {
    console.log(managerId +" "+employeeId);
    const url = `http://localhost:1991/api/v1/admin/employees/employeesupdateManager/${managerId}`;    
    return this.http.put(url, {employeeId: employeeId},{ responseType: 'text' } );
  }


  updateDeptAndSalesPercentage(employeeId: number, deptId: number, commissionPct: number): Observable<String> {
    
    const url = `${this.baseUrl}/updateDepartmentAndSales/${employeeId}/${deptId}/${commissionPct}`;
    return this.http.put(url,{},{ responseType: 'text' });
  }

  updateDepartment(employeeId: number, departmentId: number): Observable<String> {
    const url = `${this.baseUrl}/updateDepartment/${departmentId}`;
    const body = { employeeId: employeeId };

    return this.http.put(url, body,{ responseType: 'text' });
  }

  updateEmployeeJob(employeeId: number, jobId: string) : Observable<String> {
    const url = `http://localhost:1991/api/v1/public/employees/${employeeId}/updateJob/${jobId}`;
    return this.http.put(url, {},{ responseType: 'text' });
  }

  getEmployeesByLocation(locationId: number): Observable<Employee[]> {
    const url = `http://localhost:1991/api/v1/public/locations/locationwiseemployee/${locationId}`;

    return this.http.get<Employee[]>(url);
  }

  getDepartments(): Observable<Department[]> {
    return this.http.get<Department[]>('http://localhost:1991/api/v1/public/department/departmentname');
  }

  getLocations(): Observable<Location1[]> {
    return this.http.get<Location1[]>('http://localhost:1991/api/v1/public/locations');
  }

  getJobs(): Observable<JobId[]> {
    return this.http.get<JobId[]>('http://localhost:1991/api/v1/public/job');
  }


  getSalaryStatsByDepartmentId(departmentId: number): Observable<SalaryStats> {
    const url = `http://localhost:1991/api/v1/public/departments/salaryStats/${departmentId}`; 
    return this.http.get<SalaryStats>(url);
  }

  getSalaryStatsByJobId(selectedJobTitle: string): Observable<SalaryRangeResponse> {
    const url = `http://localhost:1991/api/v1/admin/job/${selectedJobTitle}/salaryrange`;
    //job/{jobTitle}/salary-range // Replace with the appropriate endpoint for retrieving salary stats
    return this.http.get<SalaryRangeResponse>(url);
  }


  getAllJobHistory(): Observable<JobHistory[]> {
    return this.http.get<JobHistory[]>('http://localhost:1991/api/v1/admin/jobhistory/getalljobhistory');
  }

  getEmployeeExperience(employeeId: number): Observable<any> {
    const url = `http://localhost:1991/api/v1/admin/jobhistory/totalyearsofexperience/${employeeId}`;
    return this.http.get<any>(url);
  }

}

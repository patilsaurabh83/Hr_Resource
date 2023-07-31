import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Employee1, Location1 } from '../model/Employee copy';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  constructor(private http: HttpClient) {}

  private baseUrl = 'http://localhost:1991/api/v1/public/employees';

  updateEmpPhoneNumber(phoneNumber:string, oldPhoneNumber:string): Observable<String> {
    console.log(oldPhoneNumber)
    const url = `http://localhost:1991/api/v1/employee/employees/updatePhoneNumber/${oldPhoneNumber}`;
    
    return this.http.put(url, {phoneNumber: phoneNumber},{ responseType: 'text' } );
  }

  updateEmpEmail(email:string, oldEmail:string): Observable<String> {
    const url = `http://localhost:1991/api/v1/employee/employees/updateEmail/${oldEmail}`;
    
    return this.http.put(url, {email: email},{ responseType: 'text' } );
  }

  updateEmpName(employeeId:number,firstName:string,lastName:string): Observable<String> {
    console.log(firstName);
    console.log(lastName);
    
    
    const url = `http://localhost:1991/api/v1/employee/employees/updateName/${employeeId}`;
    const body = {firstName, lastName };
    
    return this.http.put(url, body,{ responseType: 'text' } );
  }

  getEmployeesByLocation(locationId: number): Observable<Employee1[]> {
    const url = `http://localhost:1991/api/v1/public/locations/locationwiseemployee/${locationId}`;

    return this.http.get<Employee1[]>(url);
  }

  getLocations(): Observable<Location1[]> {
    return this.http.get<Location1[]>('http://localhost:1991/api/v1/public/locations');
  }
}

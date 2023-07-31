export interface JobHistory {
    employee: {
      employeeId: number;
      firstName: string;
      lastName: string;
      email: string;
      phoneNumber: string;
      hireDate: string;
      salary: number;
      commissionPct: number | null;
      department: {
        departmentId: number;
        departmentName: string;
      };
      jobId: {
        jobId: string;
        jobTitle: string;
        minSalary: number;
        maxSalary: number;
      };
    };
    job: {
      jobId: string;
      jobTitle: string;
      minSalary: number;
      maxSalary: number;
    };
    department: {
      departmentId: number;
      departmentName: string;
    };
    startDate: string;
    endDate: string;
  }
  
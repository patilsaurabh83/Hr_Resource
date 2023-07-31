package com.cg.hrresource.dto;

 

import java.math.BigDecimal;

 

public class SalaryRangeResponse {
    private BigDecimal minSalary;
    private BigDecimal maxSalary;

 

    public SalaryRangeResponse(BigDecimal minSalary, BigDecimal maxSalary) {
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
    }

 

    public BigDecimal getMinSalary() {
        return minSalary;
    }

 

    public BigDecimal getMaxSalary() {
        return maxSalary;
    }
}

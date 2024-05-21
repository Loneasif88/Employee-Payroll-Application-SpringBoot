package com.PayrollSystem.Service;

import com.PayrollSystem.Model.Employee;
import com.PayrollSystem.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayrollService {
    @Autowired
    EmployeeRepository employeeRepository;

    private static final double TAX_RATE = 0.2;
    private static final double INSURANCE_DEDUCTION = 200;


    // GET ALL EMPLOYEES METHOD
    public List<Employee> getAllEmployee(){
        return employeeRepository.findAll();
    }
    // get Employee by ID
    public Employee getEmployeeDetailsById(Long id){
        return employeeRepository.findById(id).orElse(null);
    }
    // Add Employee to database
    public Employee addEmployee(Employee employee){
        return employeeRepository.save(employee);
    }
    //Calculate Salery
    public double calculateNetSalery(Employee employee){
        Double grossSalary = employee.getSalary();
        Double tax = grossSalary * TAX_RATE;
        Double netSalary = grossSalary - tax - INSURANCE_DEDUCTION;
        return netSalary;
    }
}

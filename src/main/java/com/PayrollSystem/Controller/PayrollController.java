package com.PayrollSystem.Controller;

import com.PayrollSystem.Service.PayrollService;
import com.PayrollSystem.Model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/employee")
public class PayrollController {
    @Autowired
    PayrollService payrollService;

    // Get All employees
    @GetMapping
    public String getAllEmployee(Model model){
        List<Employee> employees = payrollService.getAllEmployee();
        model.addAttribute("employees", employees);
        return "employeeList";
    }
    //Add employee
    @GetMapping("/add")
    public String addEmployee(Model model){
        model.addAttribute("employee",new Employee());
        return "addEmployee";
    }
    @PostMapping
    public String addEmployee(@ModelAttribute("employee") Employee employee){
        payrollService.addEmployee(employee);
        return "redirect:/api/employee";
    }
    //Find employee by id
    @GetMapping("/{id}")
    public String getEmployeeById(@PathVariable Long id, Model model){
        Employee employee = payrollService.getEmployeeDetailsById(id);
        double netSalary = payrollService.calculateNetSalery(employee);
        model.addAttribute("employee",employee);
        model.addAttribute("netSalary" ,netSalary);
        return "testPage";

    }
    // Show the search page
    @GetMapping("/search")
    public String showSearchPage(Model model) {
        model.addAttribute("employee", null);
        model.addAttribute("error", null); // Also initializing the error attribute
        return "viewEmployee";
    }

    // Search employee by id
    @PostMapping("/search")
    public String searchEmployeeById(@RequestParam("employeeId") String employeeIdString, Model model) {
        //System.out.println("Received Employee ID: " + employeeIdString);
        try {
            Long employeeId = Long.parseLong(employeeIdString);
            Employee employee = payrollService.getEmployeeDetailsById(employeeId);
            if (employee != null) {
                double netSalary = payrollService.calculateNetSalery(employee);
                model.addAttribute("employee", employee);
                model.addAttribute("netSalary", netSalary);
            } else {
                model.addAttribute("employee", null);
            }
        } catch (NumberFormatException e) {
            model.addAttribute("error", "Invalid Employee ID format.");
        }
        return "viewEmployee";
    }


}

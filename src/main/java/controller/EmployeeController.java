package controller;

import exception.ResourceNotFoundException;
import model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.EmployeeRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    //getAllEmployee
    @GetMapping("employees")
    public List<Employee> getAllEmployee(){
        System.out.println("Entering the function....");

        return this.employeeRepository.findAll();
    }

    //getEmployeeById
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") Long employeeId) throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(()->new ResourceNotFoundException("Employee not found for the id :"+ employeeId));
        return ResponseEntity.ok().body(employee);
    }

    //addEmployee
    @PostMapping("employees")
    public Employee createEmployee(@RequestBody Employee employee){
        return this.employeeRepository.save(employee);
    }

    //updateEmployee
    @PutMapping("employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long employeeId,@RequestBody Employee employeeDetails)
            throws ResourceNotFoundException {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(()->new ResourceNotFoundException("Employee not found for the id :"+ employeeId));

        employeeDetails.setFirstName(employee.getFirstName());
        employeeDetails.setLastName(employee.getLastName());
        employee.setEmail(employee.getEmail());

        return ResponseEntity.ok(this.employeeRepository.save(employee));
    }

    //deleteEmployee
    @DeleteMapping("employee/{id}")
    public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId)
            throws ResourceNotFoundException {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(()->new ResourceNotFoundException("Employee not found for the id :"+ employeeId));

        this.employeeRepository.delete(employee);

        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return response;
    }
}

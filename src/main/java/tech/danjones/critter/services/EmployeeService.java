package tech.danjones.critter.services;

import tech.danjones.critter.entities.Employee;
import tech.danjones.critter.enums.EmployeeSkill;
import tech.danjones.critter.repository.EmployeeRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(
        EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(long employeeId) {
        return employeeRepository.getOne(employeeId);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public List<Employee> getEmployeesById(List<Long> employeeId) {
        return employeeRepository.findAllById(employeeId);
//        return employeeRepository.findAllById(Collections.singleton(employeeId));
    }

    public List<Employee> getEmployeesForService(LocalDate date, Set<EmployeeSkill> skills) {
        return employeeRepository
            .getAllByDaysAvailableContains(date.getDayOfWeek()).stream()
            .filter(employee -> employee.getSkills().containsAll(skills))
            .collect(Collectors.toList());
    }
}

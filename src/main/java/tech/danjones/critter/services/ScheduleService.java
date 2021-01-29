package tech.danjones.critter.services;

import tech.danjones.critter.entities.Customer;
import tech.danjones.critter.entities.Employee;
import tech.danjones.critter.entities.Pet;
import tech.danjones.critter.entities.Schedule;
import tech.danjones.critter.repository.CustomerRepository;
import tech.danjones.critter.repository.EmployeeRepository;
import tech.danjones.critter.repository.PetRepository;
import tech.danjones.critter.repository.ScheduleRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;
    private final PetRepository petRepository;

    public ScheduleService(
        ScheduleRepository scheduleRepository, EmployeeRepository employeeRepository,
        CustomerRepository customerRepository, PetRepository petRepository) {
        this.scheduleRepository = scheduleRepository;
        this.employeeRepository = employeeRepository;
        this.customerRepository = customerRepository;
        this.petRepository = petRepository;
    }

    public Schedule saveSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getAllSchedulesByEmployeeId(long employeeId) {
        Employee employee = employeeRepository.getOne(employeeId);
        return scheduleRepository.getAllByEmployeesContains(employee);
    }

    public List<Schedule> getAllSchedulesByCustomerId(long customerId) {
        Customer customer = customerRepository.getOne(customerId);
        return  scheduleRepository.getAllByPetsIn(customer.getPets());
    }

    public List<Schedule> getAllSchedulesByPetId(long petId) {
        Pet pet = petRepository.getOne(petId);
        return scheduleRepository.getAllByPetsContains(pet);
    }
}

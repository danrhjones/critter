package tech.danjones.critter.controller;

import tech.danjones.critter.dto.ScheduleDTO;
import tech.danjones.critter.entities.Employee;
import tech.danjones.critter.entities.Pet;
import tech.danjones.critter.entities.Schedule;
import tech.danjones.critter.services.EmployeeService;
import tech.danjones.critter.services.PetService;
import tech.danjones.critter.services.ScheduleService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private final EmployeeService employeeService;
    private final PetService petService;
    private final ScheduleService scheduleService;

    public ScheduleController(
        EmployeeService employeeService, PetService petService, ScheduleService scheduleService) {
        this.employeeService = employeeService;
        this.petService = petService;
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        schedule.setDate(scheduleDTO.getDate());
        schedule.setEmployees(employeeService.getEmployeesById(scheduleDTO.getEmployeeIds()));
        schedule.setActivities(scheduleDTO.getActivities());
        schedule.setPets(petService.getPetsById(scheduleDTO.getPetIds()));
        return getScheduleDTO(scheduleService.saveSchedule(schedule));
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleService.getAllSchedules();
        List<ScheduleDTO> list = new ArrayList<>();
        for (Schedule schedule: schedules) {
            ScheduleDTO scheduleDTO = getScheduleDTO(schedule);
            list.add(scheduleDTO);
        }
        return list;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> schedules = scheduleService.getAllSchedulesByPetId(petId);
        return schedules.stream().map(this::getScheduleDTO).collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> schedules = scheduleService.getAllSchedulesByEmployeeId(employeeId);
        return schedules.stream().map(this::getScheduleDTO).collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> schedules = scheduleService.getAllSchedulesByCustomerId(customerId);
        return schedules.stream().map(this::getScheduleDTO).collect(Collectors.toList());
    }

    private ScheduleDTO getScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setId(schedule.getId());
        scheduleDTO.setDate(schedule.getDate());
        scheduleDTO.setEmployeeIds(schedule.getEmployees().stream().map(Employee::getId).collect(Collectors.toList()));
        scheduleDTO.setPetIds(schedule.getPets().stream().map(Pet::getId).collect(Collectors.toList()));
        scheduleDTO.setActivities(schedule.getActivities());
        return scheduleDTO;
    }
}

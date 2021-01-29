package tech.danjones.critter.repository;

import tech.danjones.critter.entities.Employee;
import tech.danjones.critter.entities.Pet;
import tech.danjones.critter.entities.Schedule;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> getAllByEmployeesContains(Employee employee);

    List<Schedule> getAllByPetsIn(List<Pet> pets);

    List<Schedule> getAllByPetsContains(Pet pet);
}

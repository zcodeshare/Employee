package in.in2it.employee.repository;

import in.in2it.employee.dto.EmployeeDTO;
import in.in2it.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}

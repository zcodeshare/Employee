package in.in2it.employee.service.impl;

import in.in2it.employee.dto.EmployeeDTO;
import in.in2it.employee.entity.Employee;
import in.in2it.employee.repository.EmployeeRepository;
import in.in2it.employee.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final ModelMapper modelMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {

        if(employeeDTO.getId() != null){
            throw new RuntimeException("Employee already exists");
        }
        Employee entity = modelMapper.map(employeeDTO,Employee.class);
        Employee savedEntity = employeeRepository.save(entity);
        return modelMapper.map(savedEntity,EmployeeDTO.class);

//        Employee employee = modelMapper.map(employeeDTO,Employee.class);
//        Employee emp=employeeRepository.save(employee );
//        return  modelMapper.map(emp,EmployeeDTO.class);
    }

    @Override
    public EmployeeDTO getSingleEmployee(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(()-> new RuntimeException("Employee not found"));
        return modelMapper.map(employee,EmployeeDTO.class);

//        Employee employee = employeeRepository.findById(id).orElse(null);
//        return modelMapper.map(employee,EmployeeDTO.class);
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {

        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(emp -> modelMapper.map(emp, EmployeeDTO.class))
                .toList();
    }

    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {

        if(id == null || employeeDTO.getId() == null){
            throw new RuntimeException(("Please enter id"));
        }

        if(!Objects.equals(id,employeeDTO.getId())){
            throw  new RuntimeException(("id mismatch"));
        }

        employeeRepository.findById(id).orElseThrow(()-> new RuntimeException("Employee not found"));
        Employee entity = modelMapper.map(employeeDTO,Employee.class);
        Employee updatedEntity = employeeRepository.save(entity);
        return modelMapper.map(updatedEntity, EmployeeDTO.class);

//        Employee employee = employeeRepository.findById(id).orElse(null);
//        if(employee != null){
//            employee.setId(id);
//            employee.setEmpName(employeeDTO.getEmpName());
//            employee.setEmpEmail(employeeDTO.getEmpEmail());
//            employee.setEmpCode(employeeDTO.getEmpCode());
//            employee.setEmpName(employeeDTO.getCompanyName());
//
//            Employee savedEmployee = employeeRepository.save(employee);
//            return modelMapper.map(savedEmployee, EmployeeDTO.class);
//        }
//        return null;
    }

    @Override
    public void deleteEmployee(Long id) {

        Employee employee = employeeRepository.findById(id).orElseThrow(()-> new RuntimeException("Employee not found"));
        employeeRepository.delete(employee);
//        if(employee != null){
//            employeeRepository.delete(employee);
//            //return modelMapper.map(employee, EmployeeDTO.class);
//        }
    }
}

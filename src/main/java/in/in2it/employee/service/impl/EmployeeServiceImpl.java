package in.in2it.employee.service.impl;

import in.in2it.employee.dto.EmployeeDTO;
import in.in2it.employee.entity.Employee;
import in.in2it.employee.exception.BadRequestException;
import in.in2it.employee.exception.ResourceNotFoundException;
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
            throw new BadRequestException("Employee already exists with id");
        }

        Employee entity = modelMapper.map(employeeDTO, Employee.class);
        Employee savedEntity = employeeRepository.save(entity);

        return modelMapper.map(savedEntity, EmployeeDTO.class);
    }

    @Override
    public EmployeeDTO getSingleEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id : " + id));

        return modelMapper.map(employee,EmployeeDTO.class);
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        if(employees.isEmpty()){
            throw new ResourceNotFoundException("No employee found");
        }
        return employees.stream()
                .map(emp -> modelMapper.map(emp, EmployeeDTO.class))
                .toList();
    }

    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {

        if(id == null || employeeDTO.getId() == null){
            throw new BadRequestException(("Please enter id"));
        }

        if(!Objects.equals(id,employeeDTO.getId())){
            throw  new BadRequestException(("id mismatch"));
        }

        employeeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Employee not found with id : "+id));
        Employee entity = modelMapper.map(employeeDTO,Employee.class);
        Employee updatedEntity = employeeRepository.save(entity);
        return modelMapper.map(updatedEntity, EmployeeDTO.class);
    }

    @Override
    public void deleteEmployee(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id : " + id));

        employeeRepository.delete(employee);
    }

    @Override
    public EmployeeDTO getEmployeeByEmpCodeAndCompanyName(String empCode, String companyName) {
        Employee employee = employeeRepository.findByEmpCodeAndCompanyName(empCode,companyName).orElseThrow(()-> new ResourceNotFoundException("Employee not found with empCode : "+empCode + " and companyName: "+companyName));
        return modelMapper.map(employee,EmployeeDTO.class);
    }
}

package in.in2it.employee.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeDTO {

    private Long id;
    private String empName;
    private String empEmail;
    private String empCode;
    private String companyName;
}

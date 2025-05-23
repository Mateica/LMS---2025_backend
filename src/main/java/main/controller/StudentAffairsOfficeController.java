package main.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import main.dto.StudentAffairsOfficeDTO;
import main.service.ServiceInterface;

@RestController
@RequestMapping("/api/studentAffairsOffices")
public class StudentAffairsOfficeController implements ServiceInterface<StudentAffairsOfficeDTO> {

}

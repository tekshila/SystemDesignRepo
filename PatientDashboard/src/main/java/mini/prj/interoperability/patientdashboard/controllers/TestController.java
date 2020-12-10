package mini.prj.interoperability.patientdashboard.controllers;

import mini.prj.interoperability.patientdashboard.domain.Department;
import mini.prj.interoperability.patientdashboard.domain.DepartmentPatients;
import mini.prj.interoperability.patientdashboard.domain.PatientInfo;
import mini.prj.interoperability.patientdashboard.helpers.LoadDataHelper;
import mini.prj.interoperability.patientdashboard.helpers.PatientHelper;
import mini.prj.interoperability.patientdashboard.repository.DepartmentPatientRepository;
import mini.prj.interoperability.patientdashboard.repository.DepartmentRepository;
import org.hl7.fhir.dstu3.model.Observation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@RestController
public class TestController {

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    LoadDataHelper loadDataHelper;

    @Autowired
    DepartmentPatientRepository departmentPatientRepository;

    @Autowired
    PatientHelper patientHelper;

    @GetMapping("/mp3/departments")
    public @ResponseBody Department getDepartment() {
        return departmentRepository.findById("Emergency Room");
    }

    @GetMapping("/ddd")
    public @ResponseBody String putDepartment() {
        loadDataHelper.loadData();
        return "Added";
    }

    @GetMapping("/patients")
    public @ResponseBody DepartmentPatients getPatients() {
        DepartmentPatients dps = departmentPatientRepository.findById("Emergency Room");
        return dps;
    }

    @GetMapping("/observations/{patient_id}")
    public @ResponseBody
    HashMap<String, BigDecimal> getObservations(@PathVariable("patient_id") String patient_id) {
        HashMap<String, BigDecimal> r = patientHelper.getObservations(patient_id);
        return r;
    }

    @GetMapping("/conditions/{patient_id}")
    public @ResponseBody
    HashMap<String, String> getConditions(@PathVariable("patient_id") String patient_id) {
        HashMap<String, String> r = patientHelper.getConditions(patient_id);
        return r;
    }

    @GetMapping("/encounters/{patient_id}")
    public @ResponseBody
    HashMap<String, String> getEncounters(@PathVariable("patient_id") String patient_id) {
        HashMap<String, String> r = patientHelper.getEncounters(patient_id);
        return r;
    }
}

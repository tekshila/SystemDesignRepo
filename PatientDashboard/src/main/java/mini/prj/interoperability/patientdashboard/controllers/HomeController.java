package mini.prj.interoperability.patientdashboard.controllers;

import mini.prj.interoperability.patientdashboard.domain.Department;
import mini.prj.interoperability.patientdashboard.domain.DepartmentPatients;
import mini.prj.interoperability.patientdashboard.domain.PatientEncounters;
import mini.prj.interoperability.patientdashboard.domain.PatientInfo;
import mini.prj.interoperability.patientdashboard.repository.DepartmentPatientRepository;
import mini.prj.interoperability.patientdashboard.repository.DepartmentRepository;
import mini.prj.interoperability.patientdashboard.repository.EncounterPatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping(value = "/cache-demo")
public class HomeController {

    private static final String DEFAULT_DEPARTMENT = "Emergency Room";

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    DepartmentPatientRepository departmentPatientRepository;

    @Autowired
    EncounterPatientRepository encounterPatientRepository;


    @RequestMapping("/home")
    public String home(Model model) {
        DepartmentPatients dp = departmentPatientRepository.findById(DEFAULT_DEPARTMENT);
        int critical = 0;

            for(PatientInfo p : dp.getPatients()) {
                HashMap<String,String> patientEncounters = encounterPatientRepository.findById(p.getSmallId());
                if(null != patientEncounters) {
                   p.setDoctorName(patientEncounters.get("docter_name"));
                       p.setConditionName(patientEncounters.get("encounter_type"));
                       p.setSeverity("Critical");
                       critical++;
                } else {
                    p.setDoctorName("Not Assigned");
                    p.setConditionName("Not Assigned");
                    p.setSeverity("Not Assigned");

                }
            }

        model.addAttribute("dp",dp);
            model.addAttribute("totalCount",dp.getPatients().size());
        model.addAttribute("criticalCount",critical);
        return "home";
    }
}

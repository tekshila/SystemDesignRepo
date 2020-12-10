package mini.prj.interoperability.patientdashboard.controllers;

import mini.prj.interoperability.patientdashboard.domain.DepartmentPatients;
import mini.prj.interoperability.patientdashboard.domain.PairHolder;
import mini.prj.interoperability.patientdashboard.domain.PatientInfo;
import mini.prj.interoperability.patientdashboard.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;

@Controller
@RequestMapping(value = "/cache-demo")
public class PatientController {
    private static final String DEFAULT_DEPARTMENT = "Emergency Room";

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    DepartmentPatientRepository departmentPatientRepository;

    @Autowired
    ConditionPatientRepository conditionPatientRepository;

    @Autowired
    ObservationPatientRepository observationPatientRepository;


    @RequestMapping("/patient/{patient_id}")
    public String home(@PathVariable(name = "patient_id") String patient_id, Model model) {
        PatientInfo patientInfo = patientRepository.findById(patient_id);
            model.addAttribute("patientInfo",patientInfo);

            HashMap<String,String> conditions = conditionPatientRepository.findById(patient_id);
            ArrayList<PairHolder> conditionsList = new ArrayList<>();
            for(String key: conditions.keySet()) {
                conditionsList.add(new PairHolder(key, conditions.get(key)));
            }
        model.addAttribute("conditions",conditionsList);

        HashMap<String, BigDecimal> observations = observationPatientRepository.findById(patient_id);
        ArrayList<PairHolder> observationList = new ArrayList<>();
        for(String key: observations.keySet()) {
            observationList.add(new PairHolder(key, observations.get(key).toString()));
        }
        model.addAttribute("observations",observationList);


        return "patient";
    }

    @RequestMapping("/add_patient")
    public String add(Model model) {
        DepartmentPatients dp = departmentPatientRepository.findById(DEFAULT_DEPARTMENT);
            model.addAttribute("dp",dp);
        return "add_patient";
    }

    @RequestMapping("/delete/{patient_id}")
    public String deletePatient(@PathVariable(name = "patient_id") String patient_id,Model model) {
        departmentPatientRepository.removeId(DEFAULT_DEPARTMENT, patient_id);
        return "forward:/mini-project-3/add_patient";
    }

    @RequestMapping("/add/{patient_id}")
    public String addNewPatient(@PathVariable(name = "patient_id") String patient_id,Model model) {
        DepartmentPatients dp = departmentPatientRepository.findById(DEFAULT_DEPARTMENT);
        model.addAttribute("dp",dp);
        return "add_patient";
    }
}

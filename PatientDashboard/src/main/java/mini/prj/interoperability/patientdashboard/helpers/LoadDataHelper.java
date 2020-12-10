package mini.prj.interoperability.patientdashboard.helpers;

import mini.prj.interoperability.patientdashboard.domain.*;
import mini.prj.interoperability.patientdashboard.repository.*;
import org.hl7.fhir.dstu3.model.Bundle;
import org.hl7.fhir.dstu3.model.Observation;
import org.hl7.fhir.dstu3.model.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/*
 * This class loads the data initially
 */
@Component
public class LoadDataHelper {

    Logger logger = LoggerFactory.getLogger(LoadDataHelper.class);

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DepartmentPatientRepository departmentPatientRepository;

    @Autowired
    private PatientHelper patientHelper;

    @Autowired
    private ObservationPatientRepository observationPatientRepository;

    @Autowired
    private ConditionPatientRepository conditionPatientRepository;

    @Autowired
    private EncounterPatientRepository encounterPatientRepository;

    List<String> defaultPatientList =
            Arrays.asList("2736787","1315776","1314731","1323436", "1322201", "2500011");
           // Arrays.asList("1437886", "1425682", "2752045","2736787","1315776","1314731");

    @PostConstruct
    private void init() {
        logger.info("Loading data on init at startup...");
        loadData();
    }

    public void loadData() {
        this.loadDepartments();
        this.loadPatients();
        this.loadObservations();
        this.loadConditions();
        this.loadEncouters();
    }

    public void loadDepartments() {
        Department dept = new Department();
        dept.setName("Emergency Room");
        dept.setType("ER");
            departmentRepository.save(dept);
    }

    public void loadPatients() {
        List<PatientInfo> patients =
                patientHelper.getPatients(defaultPatientList);

        for(PatientInfo p: patients) {
            patientRepository.save(p);
        }

        departmentPatientRepository.save(
                new DepartmentPatients("Emergency Room", patients));
    }

    public void loadObservations() {

        for(String patientId: defaultPatientList) {
            HashMap<String, BigDecimal> observations = patientHelper.getObservations(patientId);
            PatientObservations patientObsvs = new PatientObservations(patientId, observations);
            observationPatientRepository.save(patientObsvs);
        }

    }

    public void loadConditions() {
        for(String patientId: defaultPatientList) {
            HashMap<String, String> conditions = patientHelper.getConditions(patientId);
            PatientConditions patientCnds = new PatientConditions(patientId, conditions);
            conditionPatientRepository.save(patientCnds);
        }
    }

    public void loadEncouters() {
        for(String patientId: defaultPatientList) {
            HashMap<String, String> encounters = patientHelper.getEncounters(patientId);
            PatientEncounters patientEncounters = new PatientEncounters(patientId, encounters);
            encounterPatientRepository.save(patientEncounters);
        }
    }


////    http://hapi.fhir.org/baseDstu3/Observation?patient=22693&code=8867-4,9279-1,85354-9,59408-5,8462-4,8310-5
//    public void loadObservations() {
//        Bundle bundle2 = client.search().forResource(Observation.class).
//                where(Observation.SUBJECT.hasId("1425682")).
//                returnBundle(Bundle.class).execute();
//    }


}

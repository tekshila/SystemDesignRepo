package mini.prj.interoperability.patientdashboard.domain;

import lombok.Getter;
import lombok.Setter;
import org.hl7.fhir.dstu3.model.Patient;

import java.io.Serializable;
import java.util.List;
import java.util.ListIterator;

@Getter
@Setter
public class DepartmentPatients implements Serializable {
    private String departmentName;
    private List<PatientInfo> patients;

    public DepartmentPatients(String departmentName, List<PatientInfo> patients) {
        this.departmentName = departmentName;
        this.patients = patients;
    }

    public void deletePatient(String patientId) {
        ListIterator<PatientInfo> li = patients.listIterator();
        while(li.hasNext()) {
            PatientInfo value = li.next();
            if(patientId.equals(value.getSmallId())) li.remove();
        }
    }
}

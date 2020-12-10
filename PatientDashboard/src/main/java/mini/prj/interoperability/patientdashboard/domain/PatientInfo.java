package mini.prj.interoperability.patientdashboard.domain;

import lombok.Getter;
import lombok.Setter;
import org.hl7.fhir.dstu3.model.Patient;
import org.hl7.fhir.exceptions.FHIRException;

import java.io.Serializable;

@Getter
@Setter
public class PatientInfo implements Serializable {

    private String id;
    private String smallId;
    private String name = "";
    private String gender = "";
    private String telephone = "";
    private String birthDate = "";
    private String address = "";
    private String maritalStatus = "";
    private String isAlive = "";

    private String doctorName = "";
    private String conditionName = "";
    private String severity = "";

    public PatientInfo(Patient patient,String patientId) {
        this.id = patient.getId();
        this.smallId = patientId;
        this.telephone = (null != patient.getContactFirstRep()) ? patient.getContactFirstRep().toString() : "";
        this.name = patient.getNameFirstRep().getNameAsSingleString();
        this.gender = (null != patient.getGender()) ? patient.getGender().toString() : "";
        this.birthDate = (null != patient.getBirthDate()) ? patient.getBirthDate().toString() : "";
    }
}

package mini.prj.interoperability.patientdashboard.domain;

import org.hl7.fhir.dstu3.model.Observation;
import org.hl7.fhir.dstu3.model.Patient;

import java.io.Serializable;

public class ObservationInfo implements Serializable {
    private String id;
    private String code;
    private String value;
    private String gender;
    private String telephone;
    private String birthDate;
    private String address;
    private String maritalStatus;
    private String isAlive;

    public ObservationInfo(Observation obs) {
        this.id = obs.getId();
        this.code = obs.getCode().getText();
        this.value = obs.getValue().toString();
    }
}

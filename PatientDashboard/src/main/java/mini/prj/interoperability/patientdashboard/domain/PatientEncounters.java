package mini.prj.interoperability.patientdashboard.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
public class PatientEncounters implements Serializable {
    private String patientId;
    HashMap<String,String> encounters;

    public PatientEncounters(String patientId, HashMap<String,String> encounters) {
        this.patientId = patientId;
        this.encounters = encounters;
    }

}

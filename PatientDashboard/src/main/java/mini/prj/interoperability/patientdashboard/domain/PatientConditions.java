package mini.prj.interoperability.patientdashboard.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
public class PatientConditions implements Serializable {
    private String patientId;
    private HashMap<String,String> conditions;

    public PatientConditions(String patientId, HashMap<String,String> conditions) {
        this.patientId = patientId;
        this.conditions = conditions;
    }

}

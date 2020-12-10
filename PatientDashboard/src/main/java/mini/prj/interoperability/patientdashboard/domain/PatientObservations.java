package mini.prj.interoperability.patientdashboard.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
public class PatientObservations implements Serializable {
    private String patientId;
    private HashMap<String, BigDecimal> observations;


    public PatientObservations(String patientId, HashMap<String, BigDecimal> observations) {
        this.patientId = patientId;
        this.observations = observations;
    }


}

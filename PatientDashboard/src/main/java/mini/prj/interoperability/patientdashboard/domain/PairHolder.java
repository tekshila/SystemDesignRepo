package mini.prj.interoperability.patientdashboard.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PairHolder implements Serializable {
    private String key;
    private String value;

    public PairHolder(String key, String value) {
        this.key = key;
        this.value = value;
    }
}

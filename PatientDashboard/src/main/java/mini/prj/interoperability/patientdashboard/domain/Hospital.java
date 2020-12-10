package mini.prj.interoperability.patientdashboard.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter @Getter
public class Hospital implements Serializable {
    private Integer id;
    private String name;
    private String address;
    private String zipCode;
    private String state;
}

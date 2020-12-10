package mini.prj.interoperability.patientdashboard.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
public class Department implements Serializable {
    private String name;
    private String type;
}

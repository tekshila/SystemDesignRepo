package mini.prj.interoperability.patientdashboard;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import mini.prj.interoperability.patientdashboard.helpers.PatientHelper;
import org.hl7.fhir.dstu3.model.Observation;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class PatientHelperTests extends TestCase {

    private String serverBase ="http://hapi.fhir.org/baseDstu3";

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public PatientHelperTests(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(PatientHelperTests.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testGetTotalNumPatientsByObservation() {


    }

}

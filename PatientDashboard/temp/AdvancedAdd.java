package mini.prj.interoperability.patientdashboard.deletable;

import ca.uhn.fhir.model.primitive.IdDt;
import ca.uhn.fhir.util.BundleUtil;
import com.google.gson.Gson;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.Observation.ObservationStatus;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.context.FhirContext;
import org.hl7.fhir.exceptions.FHIRException;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains methods for adding resoruces to the FHIR server.
 */
public class AdvancedAdd {

    private IGenericClient client = null;

    public AdvancedAdd(String baseUrl) {
        FhirContext ctx = FhirContext.forDstu3();
        client = ctx.newRestfulGenericClient(baseUrl);
    }

    /**
     * Add a new patient to the FHIR server with the given first and last name.
     * Return the ID of the newly created patient.
     */
    public String addPatient(String firstName, String lastName) {
        //Place your code here
        Patient patient = new Patient();
            patient.addName().setFamily(lastName).addGiven(firstName);
//            patient.setId(IdType.newRandomUuid());

        Bundle bundle = new Bundle();
            bundle.setType(Bundle.BundleType.TRANSACTION);

        bundle.addEntry()
                .setFullUrl(patient.getIdElement().getValue())
                .setResource(patient)
                .getRequest()
                .setUrl("PatientInfo")
                .setMethod(Bundle.HTTPVerb.POST);

        Bundle resp = client.transaction().withBundle(bundle).execute();

        System.out.println(client.getFhirContext().newJsonParser().setPrettyPrint(true).encodeResourceToString(resp));
        String location = resp.getEntryFirstRep().getResponse().getLocation();
        String pid = location.substring(location.indexOf("/") + 1,location.indexOf("/_"));
        System.out.println("PatientInfo ID : " + pid);
        return pid;
    }

    /**
     * Add a new observation to the FHIR server with a reference to the specified patient ID.
     * Assume the patient already exists in the FHIR server.
     * The observation will have a loinc code and display name, a unit of measure value code,
     * units, and value for the observation.
     * Return the ID of the newly created observation.
     */
    public String addObservation(String patientId, String loincCode, String loincDisplayName,
                                double value, String valueUnit, String valueCode) {
        //Place your code here
        Observation observation = new Observation();
            observation.setStatus(Observation.ObservationStatus.FINAL);
            observation.setSubject(new Reference("Patient/" + patientId));
            observation.getCode().addCoding().setCode(loincCode);
            observation.getCode().addCoding().setDisplay(loincDisplayName);
            observation.setValue(
                new Quantity()
                        .setValue(value)
                        .setUnit(valueUnit)
                        .setCode(valueCode));

        Bundle bundle = new Bundle();
            bundle.setType(Bundle.BundleType.TRANSACTION);

            bundle.addEntry()
                    .setResource(observation)
                    .getRequest()
                    .setUrl("Observation")
                    .setMethod(Bundle.HTTPVerb.POST);

        Bundle resp = client.transaction().withBundle(bundle).execute();

        System.out.println(client.getFhirContext().newJsonParser().setPrettyPrint(true).encodeResourceToString(resp));

        String location = resp.getEntryFirstRep().getResponse().getLocation();
        String observeId = location.substring(location.indexOf("/") + 1,location.indexOf("/_"));

        System.out.println(" Observation Id : " + observeId);

//        Observation obs = client.read().resource(Observation.class).withId(observeId).execute();
//        try {
//            System.out.println("----------------" + obs.getValueQuantity().getValue());
//
//        } catch (FHIRException e) {
//            e.printStackTrace();
//        }

        return observeId;
    }

}

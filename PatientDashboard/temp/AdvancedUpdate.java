package mini.prj.interoperability.patientdashboard.deletable;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.api.MethodOutcome;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.ContactPoint.ContactPointUse;
import org.hl7.fhir.exceptions.FHIRException;

/**
 * This class contains methods for updating resources in the FHIR server.
 */
public class AdvancedUpdate {

    private IGenericClient client = null;

    public AdvancedUpdate(String baseUrl) {
        FhirContext ctx = FhirContext.forDstu3();
        client = ctx.newRestfulGenericClient(baseUrl);
    }

    /**
     * Find the patient with the given ID and update the home phone number.  If the
     * patient does not already have a home phone number, add it.  Return the ID
     * of the updated resource.
     */
    public String updatePatientHomePhone(String patientId, String homePhoneNumber) {
        //Place your code here
        Patient patient = client.read().resource(Patient.class).withId(patientId).execute();
        if(null != patient.getTelecom() && patient.getTelecom().size() > 0) {
            System.out.println("Telephone : " + patient.getTelecomFirstRep().getValue());
        } else {
            ContactPoint cp = new ContactPoint();
               cp.setValue(homePhoneNumber);
               cp.setSystem(ContactPoint.ContactPointSystem.PHONE);
               cp.setUse(ContactPointUse.HOME);
               patient.getTelecom().add(cp);
        }


        MethodOutcome outcome = client.update()
                .resource(patient)
                .execute();

        IdType id = (IdType) outcome.getId();
        System.out.println("Got ID: " + id.getValue());

        return id.getValue();//just so it will compile, return nothing
    }

    /**
     * Find the observation with the given ID and update the value.  Recall that
     * observations have a unit of measure value code, units, and a value - this
     * method only updates the value.  Return the ID of the updated resource.
     */
    public String updateObservationValue(String observationId, double value) {
        //Place your code here
        Observation obs = client.read().resource(Observation.class).withId(observationId).execute();
        try {
            System.out.println("----------------" + obs.getValueQuantity().getValue());
            obs.getValueQuantity().setValue(value);
        } catch (FHIRException e) {
            e.printStackTrace();
        }

        MethodOutcome outcome = client.update()
                .resource(obs)
                .execute();

        IdType id = (IdType) outcome.getId();
        System.out.println("Got ID: " + id.getValue());

        return obs.getId();//just so it will compile, return nothing
    }

}

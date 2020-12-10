package mini.prj.interoperability.patientdashboard.deletable;

import java.util.List;
import java.util.ArrayList;

import ca.uhn.fhir.util.BundleUtil;
import org.hl7.fhir.dstu3.model.Bundle;
import org.hl7.fhir.dstu3.model.Observation;
import org.hl7.fhir.dstu3.model.Patient;
import org.hl7.fhir.dstu3.model.Bundle.BundleEntryComponent;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.instance.model.api.IBaseBundle;

/**
 * This class contains methods for reading resources from the FHIR server.
 */
public class SimpleRead {

    IGenericClient client = null;

    public SimpleRead(String baseUrl) {
        FhirContext ctx = FhirContext.forDstu3();
        client = ctx.newRestfulGenericClient(baseUrl);
    }

    /**
     * Find the patient with the given ID and return the full name as a
     * single string.
     */
    public String getNameByPatientID(String id) {
        // Hint, there is a method that will return the full name including
        // prefix, first, last, and suffix
        //Place your code here
        Patient patient = client.read().resource(Patient.class).withId(id).execute();
        return patient.getNameFirstRep().getNameAsSingleString();
    }

    /**
     * Find all the patients that have the provided name and return a list
     * of the IDs for those patients.  The search should include matches
     * where any part of the patient name (family, given, prefix, etc.)
     * matches the method 'name' parameter.
     */
    public List<String> getIDByPatientName(String name) {
        //Place your code here
        Bundle bundle = client.search().forResource(Patient.class).
                where(Patient.NAME.matches().value(name)).
                returnBundle(Bundle.class).execute();



        List<String> idsList = new ArrayList<>();

        List<Patient> patientsList = new ArrayList<>();
            patientsList.addAll(BundleUtil.toListOfResourcesOfType(client.getFhirContext(),bundle,Patient.class));

        while (bundle.getLink(IBaseBundle.LINK_NEXT) != null) {
            bundle = client
                    .loadPage()
                    .next(bundle)
                    .execute();
            patientsList.addAll(BundleUtil.toListOfResourcesOfType(client.getFhirContext(),bundle, Patient.class));
        }

            System.out.println("Number of patients matched : " + patientsList.size());

            for(Patient p : patientsList) {
                System.out.println(" p ==> " + p.getIdElement().getIdPart());
                idsList.add(p.getIdElement().getIdPart());
            }

        return idsList;//just so it will compile, return nothing
    }

}
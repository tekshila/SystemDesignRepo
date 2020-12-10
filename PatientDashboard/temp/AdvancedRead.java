package mini.prj.interoperability.patientdashboard.deletable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.util.BundleUtil;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.Bundle.BundleEntryComponent;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.instance.model.api.IBaseBundle;

/**
 * This class contains methods for reading resoruces from the FHIR server.
 */
public class AdvancedRead {

    private IGenericClient client = null;

    public AdvancedRead(String baseUrl) {
        FhirContext ctx = FhirContext.forDstu3();
        client = ctx.newRestfulGenericClient(baseUrl);
    }

    /**
     * Find all observations with the givin loinc code and return the total
     * number of patients referenced.  Note that patients may have multiple 
     * observations, so the number of observations >= number of patients.
     */
    public int getTotalNumPatientsByObservation(String loincCode) {

        Bundle bundle = client.search().forResource(Observation.class).
                where(Observation.CODE.exactly().code(loincCode)).
                returnBundle(Bundle.class).execute();

        List<Observation> obsvList = new ArrayList<>();
            obsvList.addAll(BundleUtil.toListOfResourcesOfType(client.getFhirContext(),bundle,Observation.class));

        while (bundle.getLink(IBaseBundle.LINK_NEXT) != null) {
            bundle = client
                    .loadPage()
                    .next(bundle)
                    .execute();
            obsvList.addAll(BundleUtil.toListOfResourcesOfType(client.getFhirContext(),bundle,Observation.class));
        }

        HashSet<String> patientIds = new HashSet<>();

           for(Observation ob: obsvList) {
               System.out.println("-------> obsv id : " + ob.getId());
               System.out.println("-------> patient id : " + ob.getSubject().getReference());
                 patientIds.add(ob.getSubject().getReference());
           }

        System.out.println("-------> obsv size : " + obsvList.size());
        System.out.println("-------> patient id : " + patientIds.size());

        return patientIds.size();
    }

}

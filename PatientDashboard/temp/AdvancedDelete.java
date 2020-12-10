package mini.prj.interoperability.patientdashboard.deletable;

import ca.uhn.fhir.rest.server.exceptions.ResourceVersionConflictException;
import org.hl7.fhir.dstu3.model.Bundle;
import org.hl7.fhir.dstu3.model.IdType;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.context.FhirContext;
import org.hl7.fhir.dstu3.model.Observation;
import org.hl7.fhir.dstu3.model.Patient;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains methods for removing resoruces from the FHIR server.
 */
public class AdvancedDelete {

    private IGenericClient client = null;

    public AdvancedDelete(String baseUrl) {
        FhirContext ctx = FhirContext.forDstu3();
        client = ctx.newRestfulGenericClient(baseUrl);
    }

    /**
     * Delete the patient with the given ID from the FHIR server.
     */

    public void deletePatient(String patientId) {
        Patient patient = client.read().resource(Patient.class).withId(patientId).execute();
        if(null == patient) return;
        try {
            client.delete()
                    .resourceConditionalByType("PatientInfo")
                    .where(Patient.RES_ID.exactly().identifier(patientId))
                    .execute();
        } catch(ResourceVersionConflictException e) {
            // do nothing
            return;
        }
    }
//
//    public void deletePatient(String patientId) {
//        Bundle bundle = client.search().forResource(Observation.class).
//                where(Observation.SUBJECT.hasId(patientId)).
//                returnBundle(Bundle.class).execute();
//
//        List<Observation> obsvList = new ArrayList<>();
//        obsvList.addAll(BundleUtil.toListOfResourcesOfType(client.getFhirContext(),bundle,Observation.class));
//
//        System.out.println("obser count " + obsvList.size());
//
//        obsvList.forEach(obv -> System.out.println( obv.getIdElement().getIdPart() ));
//
//        obsvList.forEach(obv -> deleteObservation(obv.getIdElement().getIdPart()));
//
//        client
//                .transaction().withBundle(bundle).re
//                .resourceById(new IdType("PatientInfo", patientId))
//                .execute();
//    }

    /**
     * Delete the observation with the given ID from the FHIR server.
     */
    public void deleteObservation(String observationId) {
        Observation obs = client.read().resource(Observation.class).withId(observationId).execute();
        if(null == obs) return;
        try {
            client.delete()
                    .resourceConditionalByType("Observation")
                    .where(Observation.RES_ID.exactly().identifier(observationId))
                    .execute();
        } catch(ResourceVersionConflictException e) {
            return; // do nothing
        }
    }

}

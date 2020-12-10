package mini.prj.interoperability.patientdashboard.helpers;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.util.BundleUtil;
import mini.prj.interoperability.patientdashboard.domain.PatientInfo;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.exceptions.FHIRException;
import org.hl7.fhir.instance.model.api.IBaseBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Component
public class PatientHelper {
    Logger logger = LoggerFactory.getLogger(PatientHelper.class);
    IGenericClient client = null;

    @Value("${fhir.base.url}")
    public String baseUrl;

    public PatientHelper() {

    }

    @PostConstruct
    public void init() {
        logger.info("PatientHelper : Creating FHIR context and client...");
        FhirContext ctx = FhirContext.forDstu3();
        client = ctx.newRestfulGenericClient(baseUrl);
    }

    /**
     * Find the patient with the given ID and return the full name as a
     * single string.
     */
    public PatientInfo getPatient(String id) {
        Patient patient = client.read().resource(Patient.class).withId(id).execute();
        return new PatientInfo(patient,id);
    }


    public List<PatientInfo> getPatients(List<String> patientIds) {
        List<PatientInfo> patients = new ArrayList<>();
            patientIds.forEach( patient_id -> patients.add(
                                        new PatientInfo(client.read().resource(Patient.class).withId(patient_id).execute(),patient_id)
            ));
            return patients;
    }


    public HashMap<String, BigDecimal> getObservations(String patientId) {
        //http://hapi.fhir.org/baseDstu3/Observation?patient=22693&code=8867-4,9279-1,85354-9,59408-5,8462-4,8310-5
        Bundle bundle = client.search().forResource(Observation.class).
                where(Observation.CODE.exactly().codes("8302-2","3141-9","8867-4","9279-1","85354-9","59408-5","8462-4","8310-5")).and(Observation.PATIENT.hasId("22693")).
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

//        obsvList.sort((a,b) -> {
//            try {
//                if(null == a.getEffectiveDateTimeType() || null == a.getEffectiveDateTimeType().getValue())
//                    return 0;
//                else return a.getEffectiveDateTimeType().getValue().compareTo(b.getEffectiveDateTimeType().getValue());
//            }catch(Exception e) { } finally { return 0;}
//        });

        HashMap<String, BigDecimal> vitalsMap = new HashMap<>();
        for(Observation ob: obsvList) {

            if(!vitalsMap.containsKey(ob.getCode().getText())) {

                try {
                    vitalsMap.put(ob.getCode().getText(), ob.getValueQuantity().getValue());
                } catch (FHIRException e) {
//                    e.printStackTrace();
                }

            }

//            if(ob.getStatus() == Observation.ObservationStatus.FINAL) {
//                logger.info("-------> obsv id : " + ob.getId());
//
//                logger.info("-------> CODE : " + ob.getCode().getText());
//                try {
//                    logger.info("-------> Date : " + ob.getEffectiveDateTimeType().getValue());
//                    logger.info("-------> Issued : " + ob.getIssued());
//                    logger.info("-------> CODE : " + ob.getValueQuantity().getValue());
//                } catch (FHIRException e) {
//
//                }
//            }
        }

        for(String key: vitalsMap.keySet()) {
            logger.info("----------------------------------");
            logger.info("-------> Key : " + key);
            logger.info("-------> Value : " + vitalsMap.get(key));
            logger.info("----------------------------------");
        }

        logger.info("-------> obsv size : " + obsvList.size());

        return vitalsMap;

    }


    public HashMap<String, String> getConditions(String patientId) {
        //http://hapi.fhir.org/baseDstu3/Observation?patient=22693&code=8867-4,9279-1,85354-9,59408-5,8462-4,8310-5
        Bundle bundle = client.search().forResource(Condition.class).
                where(Condition.PATIENT.hasId(patientId)).
                returnBundle(Bundle.class).execute();
        List<Condition> obsvList = new ArrayList<>();
        obsvList.addAll(BundleUtil.toListOfResourcesOfType(client.getFhirContext(),bundle,Condition.class));

        while (bundle.getLink(IBaseBundle.LINK_NEXT) != null) {
            bundle = client
                    .loadPage()
                    .next(bundle)
                    .execute();
            obsvList.addAll(BundleUtil.toListOfResourcesOfType(client.getFhirContext(),bundle,Condition.class));
        }


        HashMap<String, String> vitalsMap = new HashMap<>();
        for(Condition ob: obsvList) {

            if (!vitalsMap.containsKey(ob.getCode().getText())) {
                vitalsMap.put(ob.getCode().getText(), ob.getSeverity().getText());
            }
        }
        return vitalsMap;
    }


    public HashMap<String, String> getEncounters(String patientId) {
        //http://hapi.fhir.org/baseDstu3/Observation?patient=22693&code=8867-4,9279-1,85354-9,59408-5,8462-4,8310-5

        Bundle bundle = client.search().forResource(Encounter.class).
                where(Encounter.PATIENT.hasId(patientId)).
                returnBundle(Bundle.class).execute();

        List<Encounter> encounterLst = new ArrayList<>();
            encounterLst.addAll(BundleUtil.toListOfResourcesOfType(client.getFhirContext(),bundle,Encounter.class));

        while (bundle.getLink(IBaseBundle.LINK_NEXT) != null) {
            bundle = client
                    .loadPage()
                    .next(bundle)
                    .execute();
            encounterLst.addAll(BundleUtil.toListOfResourcesOfType(client.getFhirContext(),bundle,Encounter.class));
        }


        HashMap<String, String> vitalsMap = new HashMap<>();
        for(Encounter ob: encounterLst) {

            if (!vitalsMap.containsKey(ob.getTypeFirstRep().getText()) && null != ob.getParticipantFirstRep()) {
                vitalsMap.put("encounter_type", ob.getTypeFirstRep().getText());
                if(null != ob.getParticipantFirstRep().getIndividual().getReference()) {
                    vitalsMap.put("docter_name", getDoctor(ob.getParticipantFirstRep().getIndividual().getReference()));
                } else {
                    vitalsMap.put("docter_name", "");
                }
            }
        }
        return vitalsMap;
    }

    public String getDoctor(String id) {
        logger.info("Doctor ID : >>>>>>>>>>>>>>> " + id);
        String doctor_id = id.substring(id.indexOf("/") + 1);
        logger.info("Doctor ID NOW : >>>>>>>>>>>>>>>  doctor_id : " + doctor_id);
        Practitioner pr = client.read().resource(Practitioner.class).withId(doctor_id).execute();
        String givenName = pr.getNameFirstRep().getNameAsSingleString();
            return givenName;
    }

}

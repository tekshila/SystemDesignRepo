package mini.prj.interoperability.patientdashboard.repository;

import mini.prj.interoperability.patientdashboard.domain.DepartmentPatients;
import mini.prj.interoperability.patientdashboard.domain.PatientEncounters;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class EncounterPatientRepository {

    private HashOperations hashOperations; // to access redis cache

    public EncounterPatientRepository(RedisTemplate<String, Object> redisTemplate) {
        hashOperations = redisTemplate.opsForHash();
    }


    public void save(PatientEncounters patientEncounters) {
        hashOperations.put("PATIENT_ENCOUNTERS", patientEncounters.getPatientId(), patientEncounters.getEncounters());
    }


    public HashMap<String,String> findById(String patientId) {
        return (HashMap<String,String>)hashOperations.get("PATIENT_ENCOUNTERS", patientId);
    }
}

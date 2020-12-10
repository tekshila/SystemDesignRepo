package mini.prj.interoperability.patientdashboard.repository;

import mini.prj.interoperability.patientdashboard.domain.DepartmentPatients;
import mini.prj.interoperability.patientdashboard.domain.PatientConditions;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class ConditionPatientRepository {

    private HashOperations hashOperations; // to access redis cache

    public ConditionPatientRepository(RedisTemplate<String, Object> redisTemplate) {
        hashOperations = redisTemplate.opsForHash();
    }


    public void save(PatientConditions patientConditions) {
        hashOperations.put("PATIENT_CONDITIONS", patientConditions.getPatientId(), patientConditions.getConditions());
    }


    public HashMap<String,String> findById(String patientId) {
        return (HashMap<String,String>)hashOperations.get("PATIENT_CONDITIONS", patientId);
    }


}

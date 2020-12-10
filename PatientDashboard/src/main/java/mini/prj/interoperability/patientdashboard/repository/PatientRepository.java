package mini.prj.interoperability.patientdashboard.repository;

import mini.prj.interoperability.patientdashboard.domain.Department;
import mini.prj.interoperability.patientdashboard.domain.PatientInfo;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class PatientRepository {
    private HashOperations hashOperations; // to access redis cache

    public PatientRepository(RedisTemplate<String, Object> redisTemplate) {
        hashOperations = redisTemplate.opsForHash();
    }


    public void save(PatientInfo p) {
        hashOperations.put("PATIENT", p.getSmallId(), p);
    }

    public Map<String, PatientInfo> findAll() {
        return  hashOperations.entries("PATIENT");
    }

    public PatientInfo findById(String patientId) {
        return (PatientInfo)hashOperations.get("PATIENT", patientId);
    }

}

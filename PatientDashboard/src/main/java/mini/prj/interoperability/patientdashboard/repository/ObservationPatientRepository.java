package mini.prj.interoperability.patientdashboard.repository;

import mini.prj.interoperability.patientdashboard.domain.DepartmentPatients;
import mini.prj.interoperability.patientdashboard.domain.PatientObservations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;

@Repository
public class ObservationPatientRepository {

    private HashOperations hashOperations; // to access redis cache

    public ObservationPatientRepository(RedisTemplate<String, Object> redisTemplate) {
        hashOperations = redisTemplate.opsForHash();
    }


    public void save(PatientObservations pobs) {
        hashOperations.put("PATIENTS_OBSERVATION", pobs.getPatientId(), pobs.getObservations());
    }


    public HashMap<String, BigDecimal> findById(String patientId) {
        return (HashMap<String, BigDecimal>)hashOperations.get("PATIENTS_OBSERVATION", patientId);
    }
}

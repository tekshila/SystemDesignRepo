package mini.prj.interoperability.patientdashboard.repository;

import mini.prj.interoperability.patientdashboard.domain.PatientInfo;
import org.hl7.fhir.dstu3.model.Observation;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class ObservationRepository {
    private HashOperations hashOperations; // to access redis cache

    public ObservationRepository(RedisTemplate<String, Object> redisTemplate) {
        hashOperations = redisTemplate.opsForHash();
    }


    public void save(Observation p) {
        hashOperations.put("OBSERVATION", p.getId(), p);
    }

    public Map<String, Observation> findAll() {
        return  hashOperations.entries("OBSERVATION");
    }

    public Observation findById(String observationid) {
        return (Observation)hashOperations.get("OBSERVATION", observationid);
    }

}

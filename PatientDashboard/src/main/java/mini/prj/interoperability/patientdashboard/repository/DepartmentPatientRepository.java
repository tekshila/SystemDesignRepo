package mini.prj.interoperability.patientdashboard.repository;

import mini.prj.interoperability.patientdashboard.domain.Department;
import mini.prj.interoperability.patientdashboard.domain.DepartmentPatients;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import java.util.Map;

@Repository
public class DepartmentPatientRepository {

    private HashOperations hashOperations; // to access redis cache

    public DepartmentPatientRepository(RedisTemplate<String, Object> redisTemplate) {
        hashOperations = redisTemplate.opsForHash();
    }


    public void save(DepartmentPatients deptPatients) {
        hashOperations.put("DEPT_PATIENTS", deptPatients.getDepartmentName(), deptPatients);
    }


    public DepartmentPatients findById(String name) {
        return (DepartmentPatients)hashOperations.get("DEPT_PATIENTS", name);
    }

    public void removeId(String dept,String patientId) {
        DepartmentPatients dp = (DepartmentPatients)hashOperations.get("DEPT_PATIENTS", dept);
        dp.deletePatient(patientId);
        this.save(dp);
    }
}

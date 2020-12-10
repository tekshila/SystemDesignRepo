package mini.prj.interoperability.patientdashboard.repository;

import mini.prj.interoperability.patientdashboard.domain.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class DepartmentRepository {

    private HashOperations hashOperations; // to access redis cache

    public DepartmentRepository(RedisTemplate<String, Object> redisTemplate) {
        hashOperations = redisTemplate.opsForHash();
    }


    public void save(Department dept) {
        hashOperations.put("DEPARTMENT", dept.getName(), dept);
    }

    public Map<String, Department> findAll() {
        return  hashOperations.entries("DEPARTMENT");
    }

    public Department findById(String name) {
        return (Department)hashOperations.get("DEPARTMENT", name);
    }

}

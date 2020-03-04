package mops.feedback.database;

import mops.feedback.database.dto.AntwortenDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AntwortenRepository extends CrudRepository<AntwortenDto, Long> {

}

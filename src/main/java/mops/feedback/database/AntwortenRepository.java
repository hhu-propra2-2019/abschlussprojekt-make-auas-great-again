package mops.feedback.database;

import mops.feedback.database.dto.AntwortenDto;
import org.springframework.data.repository.CrudRepository;

public interface AntwortenRepository extends CrudRepository<AntwortenDto, Long> {

}

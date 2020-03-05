package mops.feedback.database;

import mops.feedback.database.dto.FormularDto;
import org.springframework.data.repository.CrudRepository;

public interface FormularRepository extends CrudRepository<FormularDto, Long> {

}

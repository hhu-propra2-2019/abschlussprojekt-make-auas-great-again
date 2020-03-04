package mops.feedback.database;

import mops.feedback.database.dto.FormularDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormularRepository extends CrudRepository<FormularDto, Long> {

}

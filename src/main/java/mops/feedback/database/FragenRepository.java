package mops.feedback.database;

import mops.feedback.database.dto.FragenDto;
import org.springframework.data.repository.CrudRepository;

public interface FragenRepository extends CrudRepository<FragenDto, Long> {

}

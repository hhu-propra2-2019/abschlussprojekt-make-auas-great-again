package mops.feedback.database;

import mops.feedback.database.dto.FragenDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FragenRepository extends CrudRepository<FragenDto, Long> {

}

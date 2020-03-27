package mops.database.repos;

import java.util.List;
import mops.database.dto.VeranstaltungDto;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface VeranstaltungRepository extends CrudRepository<VeranstaltungDto, Long> {
  @Query("SELECT * FROM veranstaltung WHERE id IN "
      + "(SELECT veranstaltung FROM dozentOrganisiertVeranstaltung WHERE dozent = :name)")
  List<VeranstaltungDto> getAllFromDozentByUsername(@Param("name") String username);

  @Query("SELECT * FROM veranstaltung WHERE id IN "
      + "(SELECT veranstaltung FROM studentBelegtVeranstaltung WHERE student = :name)")
  List<VeranstaltungDto> getAllFromStudentByUsername(@Param("name") String username);
}

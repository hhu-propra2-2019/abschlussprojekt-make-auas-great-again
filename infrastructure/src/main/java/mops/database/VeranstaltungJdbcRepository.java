package mops.database;

import java.util.Set;
import mops.database.dto.VeranstaltungDto;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface VeranstaltungJdbcRepository extends CrudRepository<VeranstaltungDto, Long> {

  @Query("select * from veranstaltung where upper(name) like '%'|| upper(:search) || '%'")
  Set<VeranstaltungDto> getAllContaining(@Param("search") String search);

  @Query("select * from veranstaltung where id in("
      + "select veranstaltung from studentBelegtVeranstaltung where student= :student)")
  Set<VeranstaltungDto> getAllFromStudent(@Param("student") Long student);

  @Query("select * from veranstaltung where veranstaltung.dozent= :dozent")
  Set<VeranstaltungDto> getAllFromDozent(@Param("dozent") Long dozent);

  @Query("select * from veranstaltung where id in("
      + "select veranstaltung from studentBelegtVeranstaltung where student= :student)"
      + "and upper(name) like '%'|| upper(:search) || '%' ")
  Set<VeranstaltungDto> getAllFromStudentContaining(@Param("student") Long student,
                                                    @Param("search") String search);

  @Query("select * from veranstaltung where veranstaltung.dozent = :dozent "
      + "and upper(name) like '%'|| upper(:search) || '%' ")
  Set<VeranstaltungDto> getAllFromDozentContaining(@Param("dozent") Long dozent,
                                                   @Param("search") String search);

}

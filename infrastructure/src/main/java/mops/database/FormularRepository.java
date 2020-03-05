package mops.database;

import mops.Formular;
import org.springframework.stereotype.Repository;


@Repository
public interface FormularRepository {
  Formular getFormularById(Long id);
}

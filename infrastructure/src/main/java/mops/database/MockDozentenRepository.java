package mops.database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mops.Dozent;
import mops.controllers.DozentenRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("Dozent")
public class MockDozentenRepository implements DozentenRepository {
  @Override
  public List<Dozent> getAll() {
    Dozent d1 = new Dozent(467L, "Jens", "Bendisposto", "A3");
    Dozent d2 = new Dozent(447L, "Christian", "Meter", "B3");
    Dozent d3 = new Dozent(447L, "B1", "B2", "B3");
    List<Dozent> dozenten = new ArrayList<>(Arrays.asList(d1, d2, d3));
    return dozenten;
  }

  @Override
  public Dozent getDozentById(Long id) {
    return null;
  }
}

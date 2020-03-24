package mops.database;

import java.util.HashMap;
import java.util.Map;
import mops.controllers.DozentRepository;
import mops.rollen.Dozent;

public class MockDozentenRepository implements DozentRepository {
  private final Map<String, Dozent> dozenten = new HashMap<>();

  public MockDozentenRepository() {
    dozenten.put("orga1", new Dozent("orga1"));
  }

  @Override
  public Dozent getDozentByUsername(String username) {
    return dozenten.get(username);
  }
}

package mops.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import mops.Fragebogen;
import mops.FragebogenService;
import mops.controllers.FragebogenRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("Faker")
@SuppressWarnings( {"PMD.DataflowAnomalyAnalysis"})
public class MockFragebogenRepository implements FragebogenRepository {
  // static, da beide Controller gleiche Datenbank brauchen
  private static final Map<Long, Fragebogen> frageboegen = new HashMap<>();
  private FragebogenService fragebogenService;

  public MockFragebogenRepository() {
    this.fragebogenService = new FragebogenService();
  }

  @Override
  public Fragebogen getFragebogenById(Long id) {
    if (!frageboegen.containsKey(id)) {
      frageboegen.put(id, fragebogenService.randomFragebogen(id));
    }
    return frageboegen.get(id);
  }

  @Override
  public List<Fragebogen> getAll() {
    List<Fragebogen> fragenliste = new ArrayList<>();
    generateDummyFrageboegen();
    for (Long id : frageboegen.keySet()) {
      fragenliste.add(getFragebogenById(id));
    }
    return fragenliste;
  }

  private void generateDummyFrageboegen() {
    for (long i = 1L; i < 10L; i++) {
      frageboegen.put(i, fragebogenService.randomFragebogen(i));
    }
  }

  @Override
  public List<Fragebogen> getAllContaining(String search) {
    return getAll().stream()
        .filter(bogen -> bogen.contains(search))
        .collect(Collectors.toList());
  }

  @Override
  public void newFragebogen(Fragebogen fragebogen) {
    Long id = fragebogen.getBogennr();
    frageboegen.put(id, fragebogen);
  }
}

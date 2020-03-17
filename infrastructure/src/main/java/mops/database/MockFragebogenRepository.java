package mops.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import mops.Fragebogen;
import mops.FragebogenService;
import mops.controllers.FragebogenRepository;
import org.springframework.stereotype.Repository;

@Repository
public class MockFragebogenRepository implements FragebogenRepository {
  // static, da beide Controller gleiche Datenbank brauchen
  private static transient Map<Long, Fragebogen> frageboegen = new HashMap<>();
  private static transient FragebogenService fragebogenService = new FragebogenService();

  public MockFragebogenRepository() {
    List<Fragebogen> fragebogenList = fragebogenService.randomFrageboegen(10);
    Long id = 0L;
    for (Fragebogen fragebogen : fragebogenList) {
      frageboegen.put(id++, fragebogen);
    }
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
    return new ArrayList<>(frageboegen.values());
  }

  @Override
  public List<Fragebogen> getAllContaining(String search) {
    return frageboegen.values().stream()
        .filter(bogen -> bogen.contains(search))
        .collect(Collectors.toList());
  }

  @Override
  public void newFragebogen(Fragebogen fragebogen) {
    Long id = fragebogen.getBogennr();
    frageboegen.put(id, fragebogen);
  }
}

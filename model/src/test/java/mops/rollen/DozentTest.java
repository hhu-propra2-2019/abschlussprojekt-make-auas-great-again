package mops.rollen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import mops.FragebogenTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DozentTest {
  private transient Dozent dozent;

  @BeforeEach
  public void setUp() {
    dozent = new Dozent("testdozent");
  }

  @Test
  public void korrektesTemplateWirdZurueckGegeben() {
    FragebogenTemplate template1 = new FragebogenTemplate(1L, "template1", null);
    FragebogenTemplate template2 = new FragebogenTemplate(2L, "template2", null);
    dozent.addTemplate(template1);
    dozent.addTemplate(template2);

    FragebogenTemplate totest = dozent.getTemplateById(1L);

    assertEquals(totest, template1);
  }

  @Test
  public void korrektesTemplateWirdGeloescht() {
    FragebogenTemplate template1 = new FragebogenTemplate(1L, "template1", null);
    FragebogenTemplate template2 = new FragebogenTemplate(2L, "template2", null);
    dozent.addTemplate(template1);
    dozent.addTemplate(template2);

    dozent.deleteTemplateById(1L);

    assertTrue(dozent.getTemplates().contains(template2));
    assertFalse(dozent.getTemplates().contains(template1));
  }
}

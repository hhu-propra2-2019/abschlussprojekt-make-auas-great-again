package mops;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import mops.antworten.TextAntwort;
import mops.fragen.Auswahl;
import mops.fragen.Frage;
import mops.fragen.MultipleChoiceFrage;
import mops.fragen.MultipleResponseFrage;
import mops.fragen.SingleResponseFrage;
import mops.fragen.TextFrage;
import mops.rollen.Dozent;

public class DozentService {
  private final transient DateTimeService datetime;

  public DozentService() {
    datetime = new DateTimeService();
  }

  /**
   * Holt alle Fragebögen aus der Veranstaltungsliste.
   *
   * @param veranstaltungen Liste von Veranstaltungen.
   * @return frageboegen alle Fragebögen aus den Veranstaltungen der Liste.
   */
  public List<Fragebogen> holeFrageboegenVomDozent(List<Veranstaltung> veranstaltungen) {
    List<Fragebogen> result = new ArrayList<>();
    veranstaltungen.stream().forEach(x -> result.addAll(x.getFrageboegen()));
    return result;
  }

  public MultipleChoiceFrage getMultipleChoiceFrage(Long fragennr, Fragebogen fragebogen) {
    return (MultipleChoiceFrage) fragebogen.getFrage(fragennr);
  }

  public MultipleChoiceFrage getMultipleChoiceFromTemplate(Long fragennr, Dozent dozent,
      Long templatenr) {
    FragebogenTemplate template = dozent.getTemplateById(templatenr);
    return template.getMultipleChoiceFrageById(fragennr);
  }

  public void zensiereTextAntwort(Fragebogen fragebogen, Long fragennr,
      Long antwortnr, String neuertext) {
    TextAntwort antwort = getTextAntwort(fragennr, antwortnr, fragebogen);
    antwort.setAntworttext(neuertext);
  }

  public void aendereOeffentlichkeitVonFrage(Fragebogen fragebogen, Long fragennr) {
    Frage frage = fragebogen.getFrage(fragennr);
    frage.aendereOeffentlichkeitsStatus();
  }

  public TextAntwort getTextAntwort(Long fragennr, Long antwortnr, Fragebogen fragebogen) {
    TextFrage frage = (TextFrage) fragebogen.getFrage(fragennr);
    return (TextAntwort) frage.getAntwortById(antwortnr);
  }

  public Frage getFrage(Long fragennr, Fragebogen fragebogen) {
    return fragebogen.getFrage(fragennr);
  }

  public void addNeueFrageZuFragebogen(Fragebogen fragebogen, String fragentext, String fragetyp) {
    Frage frage = createNeueFrageAnhandFragetyp(fragetyp, fragentext);
    fragebogen.addFrage(frage);
  }

  public void addMultipleChoiceMoeglichkeit(Fragebogen fragebogen, Long fragennr,
      String antworttext) {
    MultipleChoiceFrage frage = getMultipleChoiceFrage(fragennr, fragebogen);
    frage.addChoice(new Auswahl(antworttext));
  }

  /**
   * Fügt eine Multiple Choice Antwortmöglichkeit zu einer Multiple-Choice-Frage innerhalb eines
   * Templates hinzu.
   *
   * @param dozent Der Dozent, dem das Template gehört
   * @param templateid Die ID des Templates
   * @param fragennr Die ID der Frage
   * @param antworttext Die neue Antwortmöglichkeit
   */
  public void addMultipleChoiceToTemplate(Dozent dozent, Long templateid,
      Long fragennr, String antworttext) {
    FragebogenTemplate template = dozent.getTemplateById(templateid);
    MultipleChoiceFrage frage = template.getMultipleChoiceFrageById(fragennr);
    frage.addChoice(new Auswahl(antworttext));
  }

  public void loescheMultipleChoiceMoeglichkeit(Fragebogen fragebogen, Long fragennr,
      Long antwortnr) {
    MultipleChoiceFrage frage = getMultipleChoiceFrage(fragennr, fragebogen);
    frage.deleteChoice(antwortnr);
  }

  /**
   * Löscht eine Multiple Choice Antwortmöglichkeit aus einer Multiple-Choice-Frage innerhalb eines
   * Templates.
   *
   * @param dozent Der Dozent, dem das Template gehört.
   * @param templateid Die ID des Templates
   * @param fragennr Die ID der Frage
   * @param antwortnr Die ID der zu löschenden Antwortmöglichkeit
   */
  public void loescheMultipleChoiceAusTemplate(Dozent dozent, Long templateid,
      Long fragennr, Long antwortnr) {
    FragebogenTemplate template = dozent.getTemplateById(templateid);
    MultipleChoiceFrage frage = template.getMultipleChoiceFrageById(fragennr);
    frage.deleteChoice(antwortnr);
  }

  public void loescheFrageAusFragebogen(Fragebogen fragebogen, Long fragennr) {
    fragebogen.loescheFrageById(fragennr);
  }

  public void loescheFrageAusTemplate(Dozent dozent, Long templateid, Long fragennr) {
    FragebogenTemplate template = dozent.getTemplateById(templateid);
    template.deleteFrageById(fragennr);
  }

  /**
   * Fügt Fragen aus einem Fragebogen-Template in einen Fragebogen ein.
   *
   * @param fragebogen Der Fragebogen, dem die Fragen hinzugefügt werden sollen
   * @param dozent Der Dozent, dem das Template gehört
   * @param vorlagenid Die ID des Templates, das zum Fragebogen hinzugefügt werden soll
   */
  public void addFragenAusTemplateZuFragebogen(Fragebogen fragebogen, Dozent dozent,
      Long vorlagenid) {
    FragebogenTemplate template = dozent.getTemplateById(vorlagenid);
    List<Frage> fragen = getFragenlisteOhneAntworten(template.getFragen());
    fragen.stream().forEach(x -> fragebogen.addFrage(x));
  }

  public List<Frage> getFragenlisteOhneAntworten(List<Frage> altefragen) {
    return altefragen.stream().map(x -> x.clone()).collect(Collectors.toList());
  }

  /**
   * Aktualisiert die Metadaten eines Fragebogens.
   *
   * @param fragebogen Der Fragebogen, dessen Metadaten aktualisiert werden sollen
   * @param name Der Name des Fragebogens
   * @param typ Der Typ der Veranstaltung
   * @param startdatum Das Datum, wann die Umfrage geöffnet wird
   * @param startzeit Die Uhrzeit, wann die Umfrage geöffnet wird
   * @param enddatum Das Datum, wann die Umfrage geschlossen wird
   * @param endzeit Die Uhrzeit, wann die Umfrage geschlossen wird
   */
  public void updateFragebogenMetadaten(Fragebogen fragebogen, String name, String typ,
      String startdatum, String startzeit, String enddatum, String endzeit) {
    fragebogen.setVeranstaltungsname(name);
    fragebogen.setType(Einheit.valueOf(typ));
    fragebogen.setStartdatum(datetime.getLocalDateTimeFromString(startdatum, startzeit));
    fragebogen.setEnddatum(datetime.getLocalDateTimeFromString(enddatum, endzeit));
  }

  /**
   * Fügt einen neuen Fragebogen zu einer Veranstaltung hinzu.
   *
   * @param veranstaltung Die Veranstaltung, für die ein neuer Fragebogen erstellt wird
   * @param dozent Der Dozent der Veranstaltung
   * @return Die ID des neuen Fragebogens
   */
  public Long fuegeFragebogenZuVeranstaltungHinzu(Veranstaltung veranstaltung, Dozent dozent) {
    Fragebogen neuerbogen = new Fragebogen(veranstaltung.getName(),
        dozent.getVorname() + " " + dozent.getNachname());
    veranstaltung.addFragebogen(neuerbogen);
    return neuerbogen.getBogennr();
  }

  /**
   * Erstellt ein neues Fragebogen-Template.
   *
   * @param dozent Der Dozent, dem das Template zugeordnet wird
   * @param templatename Der Name des neuen Templates
   * @return Die ID des neuen Templates
   */
  public Long createNewTemplate(Dozent dozent, String templatename) {
    FragebogenTemplate template = new FragebogenTemplate(templatename);
    dozent.addTemplate(template);
    return template.getId();
  }

  public void addFrageZuTemplate(Dozent dozent, Long templatenr, String fragetyp,
      String fragentext) {
    FragebogenTemplate template = dozent.getTemplateById(templatenr);
    template.addFrage(createNeueFrageAnhandFragetyp(fragetyp, fragentext));
  }

  /**
   * Klont einen vorhandenen Fragebogen und fügt den neuen Fragebogen wieder in die Veranstaltung
   * ein.
   *
   * @param fragebogen Der zu klonende Fragebogen
   * @param veranstaltung Die Veranstaltung, der der Fragebogen zugeordnet ist
   * @return Die ID des neuen Fragebogens
   */
  public Long kloneFragebogen(Fragebogen fragebogen, Veranstaltung veranstaltung) {
    Fragebogen neu = new Fragebogen(fragebogen.getVeranstaltungsname(),
        fragebogen.getProfessorenname(),
        getFragenlisteOhneAntworten(fragebogen.getFragen()), fragebogen.getType());
    veranstaltung.addFragebogen(neu);
    return neu.getBogennr();
  }

  /**
   * Erzeugt ein passendes Fragenobjekt anhand des übergebenen Fragtyps.
   *
   * @param fragetyp Der Typ der Frage, entweder 'multiplechoice' oder 'textfrage'
   * @param fragetext Der Text der Frage
   * @return das neue Fragenobjekt
   */
  @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
  public Frage createNeueFrageAnhandFragetyp(String fragetyp, String fragetext) {
    Random idgenerator = new Random();
    if ("multiplechoice".equals(fragetyp)) {
      return new SingleResponseFrage(idgenerator.nextLong(), fragetext, false);
    } else if ("textfrage".equals(fragetyp)) {
      return new TextFrage(fragetext);
    } else {
      return new MultipleResponseFrage(idgenerator.nextLong(), fragetext);
    }
  }
}

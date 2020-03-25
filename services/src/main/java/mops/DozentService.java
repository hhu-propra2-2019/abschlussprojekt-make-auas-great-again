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
    this.datetime = new DateTimeService();
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
    return (MultipleChoiceFrage) template.getMultipleChoiceFrageById(fragennr);
  }
  
  public void zensiereTextAntwort(Fragebogen fragebogen, Long fragennr,
      Long antwortnr, String neuertext) {
    TextAntwort antwort = this.getTextAntwort(fragennr, antwortnr, fragebogen);
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
    Frage frage = this.createNeueFrageAnhandFragetyp(fragetyp, fragentext);
    fragebogen.addFrage(frage);
  }
  
  public void addMultipleChoiceMoeglichkeit(Fragebogen fragebogen, Long fragennr,
      String antworttext) {
    MultipleChoiceFrage frage = this.getMultipleChoiceFrage(fragennr, fragebogen);
    frage.addChoice(new Auswahl(antworttext));
  }
  
  public void addMultipleChoiceToTemplate(Dozent dozent, Long templateid,
      Long fragennr, String antworttext) {
    FragebogenTemplate template = dozent.getTemplateById(templateid);
    MultipleChoiceFrage frage = template.getMultipleChoiceFrageById(fragennr);
    frage.addChoice(new Auswahl(antworttext));
  }
  
  public void loescheMultipleChoiceMoeglichkeit(Fragebogen fragebogen, Long fragennr,
      Long antwortnr) {
    MultipleChoiceFrage frage = this.getMultipleChoiceFrage(fragennr, fragebogen);
    frage.deleteChoice(antwortnr);
  }
  
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
  
  public void addFragenAusTemplateZuFragebogen(Fragebogen fragebogen, Dozent dozent,
      Long vorlagenid) {
    FragebogenTemplate template = dozent.getTemplateById(vorlagenid);
    List<Frage> fragen = this.getFragenlisteOhneAntworten(template.getFragen());
    fragen.stream().forEach(x -> fragebogen.addFrage(x));
  }

  public List<Frage> getFragenlisteOhneAntworten(List<Frage> altefragen) {
    return altefragen.stream().map(x -> x.clone()).collect(Collectors.toList());
  }
  
  public void updateFragebogenMetadaten(Fragebogen fragebogen, String name, String typ,
      String startdatum, String startzeit, String enddatum, String endzeit) {
    fragebogen.setVeranstaltungsname(name);
    fragebogen.setType(Einheit.valueOf(typ));
    fragebogen.setStartdatum(datetime.getLocalDateTimeFromString(startdatum, startzeit));    
    fragebogen.setEnddatum(datetime.getLocalDateTimeFromString(enddatum, endzeit));
  }
  
  public Long fuegeFragebogenZuVeranstaltungHinzu(Veranstaltung veranstaltung, Dozent dozent) {
    Fragebogen neuerbogen = new Fragebogen(veranstaltung.getName(),
        dozent.getVorname() + " " + dozent.getNachname());
    veranstaltung.addFragebogen(neuerbogen);
    return neuerbogen.getBogennr();
  }
  
  public Long createNewTemplate(Dozent dozent, String templatename) {
    FragebogenTemplate template = new FragebogenTemplate(templatename);
    dozent.addTemplate(template);
    return template.getId();
  }
  
  public void addFrageZuTemplate(Dozent dozent, Long templatenr, String fragetyp,
      String fragentext) {
    FragebogenTemplate template = dozent.getTemplateById(templatenr);
    template.addFrage(this.createNeueFrageAnhandFragetyp(fragetyp, fragentext));
  }
  
  public Long kloneFragebogen(Fragebogen fragebogen, Veranstaltung veranstaltung) {
    Fragebogen neu = new Fragebogen(fragebogen.getVeranstaltungsname(),
        fragebogen.getProfessorenname(),
        this.getFragenlisteOhneAntworten(fragebogen.getFragen()), fragebogen.getType());
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

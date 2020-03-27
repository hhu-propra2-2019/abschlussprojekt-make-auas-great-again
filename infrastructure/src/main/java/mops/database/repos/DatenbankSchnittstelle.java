package mops.database.repos;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import mops.Fragebogen;
import mops.Veranstaltung;
import mops.database.dto.DozentDto;
import mops.database.dto.FragebogenDto;
import mops.database.dto.VeranstaltungDto;
import mops.rollen.Dozent;
import mops.rollen.Student;

@Service
@AllArgsConstructor
public class DatenbankSchnittstelle {
  private final DozentRepository dozentrepo;
  private final FragebogenRepository fragebogenrepo;
  private final VeranstaltungRepository veranstaltungsrepo;
  
  private final TranslationService translator;
  
  public Fragebogen getFragebogenById(Long bogennr) {
    FragebogenDto dto = fragebogenrepo.findById(bogennr).get();
    return translator.load(dto);
  }
  
  public Veranstaltung getVeranstaltungById(Long veranstaltungsnr) {
    VeranstaltungDto dto = veranstaltungsrepo.findById(veranstaltungsnr).get();
    return translator.load(dto);
  }
  
  public Dozent getDozentByUsername(String username) {
    DozentDto dto = dozentrepo.findById(username).get();
    return translator.load(dto);
  }
  
  public List<Veranstaltung> getVeranstaltungenFromDozentContaining(Dozent dozent, String search) {
    List<Veranstaltung> allevomdozent = this.getVeranstaltungenFromDozent(dozent);
    return allevomdozent.stream().filter(x -> x.contains(search)).collect(Collectors.toList());
  }
  
  public List<Veranstaltung> getVeranstaltungenFromDozent(Dozent dozent) {
    List<VeranstaltungDto> dtos = 
        veranstaltungsrepo.getAllFromDozentByUsername(dozent.getUsername());
    return dtos.stream().map(translator::load).collect(Collectors.toList());
  }
  
  public List<Veranstaltung> getVeranstaltungenFromStudentContaining(Student student,
      String search) {
    List<Veranstaltung> allevomstudent = this.getVeranstaltungenFromStudent(student);
    return allevomstudent.stream().filter(x -> x.contains(search)).collect(Collectors.toList());
  }
  
  public List<Veranstaltung> getVeranstaltungenFromStudent(Student student) {
    List<VeranstaltungDto> dtos = 
        veranstaltungsrepo.getAllFromStudentByUsername(student.getUsername());
    return dtos.stream().map(translator::load).collect(Collectors.toList());
  }
  
  public void saveVeranstaltung(Veranstaltung veranstaltung) {
    veranstaltungsrepo.save(translator.unload(veranstaltung));
  }
  
  public void saveFragebogen(Fragebogen fragebogen) {
    fragebogenrepo.save(translator.unload(fragebogen));
  }
  
  public void saveDozent(Dozent dozent) {
    dozentrepo.save(translator.unload(dozent));
  }
}

package mops.database;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import mops.Fragebogen;
import mops.Veranstaltung;
import mops.database.dto.DozentDto;
import mops.database.dto.FragebogenDto;
import mops.database.dto.VeranstaltungDto;
import mops.database.repos.DozentRepository;
import mops.database.repos.FragebogenRepository;
import mops.database.repos.VeranstaltungRepository;
import mops.rollen.Dozent;
import mops.rollen.Student;
import org.springframework.stereotype.Service;

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
  
  public Veranstaltung saveVeranstaltung(Veranstaltung veranstaltung) {
    VeranstaltungDto dto = veranstaltungsrepo.save(translator.unload(veranstaltung));
    return translator.load(dto);
  }
  
  public Fragebogen saveFragebogen(Fragebogen fragebogen) {
    FragebogenDto dto = fragebogenrepo.save(translator.unload(fragebogen));
    return translator.load(dto);
  }
  
  public Dozent saveDozent(Dozent dozent) {
    DozentDto dto = dozentrepo.save(translator.unload(dozent));
    return translator.load(dto);
  }
}

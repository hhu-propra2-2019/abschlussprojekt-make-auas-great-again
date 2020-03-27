package mops.database;

import java.util.List;
import java.util.Set;
import mops.Fragebogen;
import mops.FragebogenTemplate;
import mops.Veranstaltung;
import mops.antworten.Antwort;
import mops.database.dto.AntwortDto;
import mops.database.dto.AuswahlDto;
import mops.database.dto.DozentDto;
import mops.database.dto.DozentOrganisiertVeranstaltungDto;
import mops.database.dto.FrageDto;
import mops.database.dto.FragebogenDto;
import mops.database.dto.FragebogenTemplateDto;
import mops.database.dto.StudentBeantwortetFragebogenDto;
import mops.database.dto.StudentBelegtVeranstaltungDto;
import mops.database.dto.StudentDto;
import mops.database.dto.VeranstaltungDto;
import mops.fragen.Auswahl;
import mops.fragen.Frage;
import mops.rollen.Dozent;
import mops.rollen.Student;

class TranslationService {
  public static Antwort load(AntwortDto dto) {
    return null;
  }

  public static Auswahl load(AuswahlDto dto) {
    return null;
  }

  public static Dozent load(DozentDto dto) {
    return null;
  }

  public static Fragebogen load(FragebogenDto dto) {
    return null;
  }

  public static FragebogenTemplate load(FragebogenTemplateDto dto) {
    return null;
  }

  public static Frage load(FrageDto dto) {
    return null;
  }

  public static Student load(StudentDto dto) {
    return null;
  }

  public static Veranstaltung load(VeranstaltungDto dto) {
    return null;
  }

  private static List<Dozent> loadOrganisatoren(Set<DozentOrganisiertVeranstaltungDto> dtos) {
    return null;
  }

  private static List<Student> loadBeantworter(Set<StudentBeantwortetFragebogenDto> dtos) {
    return null;
  }

  private static List<Student> loadBeleger(Set<StudentBelegtVeranstaltungDto> dtos) {
    return null;
  }
}

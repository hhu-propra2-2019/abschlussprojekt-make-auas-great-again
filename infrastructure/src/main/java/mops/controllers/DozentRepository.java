package mops.controllers;

import mops.rollen.Dozent;

public interface DozentRepository {
  Dozent getDozentByUsername(String username);
}

package mops.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/feedback")
@Controller

public class ErgebnisController {

  @GetMapping("/ergebnis")
  public String boegenUebersicht() {
    return "ergebnis";
  }

  @PostMapping("/ergebnis")
  public String waehleBogen() {
    return "ergebnis";
  }

  @GetMapping("/ergebnis/{bogennr}")
  public String ergebnisUebersicht() {
    return "ergebnis";
  }

}

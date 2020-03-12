package mops.fragen;

import mops.fragen.MultipleChoiceFrage;

public class MultipleResponseFrage extends MultipleChoiceFrage {


  public MultipleResponseFrage(Long id, String fragentext) {

    super(id, fragentext, true);
  }
}

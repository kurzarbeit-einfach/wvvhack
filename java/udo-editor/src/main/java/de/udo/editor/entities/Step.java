package de.udo.editor.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Step {
  private NextStepKeys nextStepKeys;
  private String type;
  private Request request;
  private Interaction interaction;

}

package de.udo.editor.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class StepConfig {
  private String startStepKey;
  private String[] exitSteps;
  private Map<String,Step> steps;
}

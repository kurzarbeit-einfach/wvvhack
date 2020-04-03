package de.udo.editor.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MonthInputRelative {
  private Integer minMonthFromRelative;
  private Integer maxMonthFromRelative;
  private String relative;
}

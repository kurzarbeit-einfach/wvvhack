package de.udo.editor.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class Interaction {
  ConfirmButton confirmButton;
  Button[] buttons;
  TextInput textInput;
  NumberInput numberInput;
  MonthInputRelative monthInputRelative;
}

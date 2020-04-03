package de.udo.editor.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class FunctionCall {
  String name;
  Map<String, String> parameter;



}

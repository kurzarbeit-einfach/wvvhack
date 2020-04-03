package de.udo.editor.entities;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class NumberInput implements Required {
  private final Map<String, Number> parameter;
  private Boolean required;
  private FunctionCall replyFunctionCall;
  private FunctionCall forwardFunctionCall;


  public NumberInput() {
    parameter = new HashMap<>();
  }


  @JsonAnyGetter
  public Map<String, Number> any() {
    return parameter;
  }

  @JsonAnySetter
  public void set(String name, Number value) {
    parameter.put(name, value);
  }

}
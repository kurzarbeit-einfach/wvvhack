package de.udo.editor.entities;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ConfirmButton implements Required {

  private final Map<String, String> parameter;
  private Boolean required;

  public ConfirmButton(){
    parameter = new HashMap<>();
  }


  @JsonAnyGetter
  public Map<String, String> any() {
    return parameter;
  }

  @JsonAnySetter
  public void set(String name, String value) {
    parameter.put(name, value);
  }

}

package de.udo.editor.entities;

import com.fasterxml.jackson.annotation.*;
import de.udo.editor.exceptions.ValidatorException;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.Map;

import static de.udo.editor.exceptions.ValidatorException.ValidationErrorType.NEXT_STEP_KEYS_INVALID;

@Data
public class NextStepKeys implements SelfValidation {

  @JsonIgnore
  private final Map<String, String> parameter;

  public NextStepKeys(){
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

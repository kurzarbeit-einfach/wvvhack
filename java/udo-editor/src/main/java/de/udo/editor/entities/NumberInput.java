package de.udo.editor.entities;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import de.udo.editor.exceptions.ValidatorException;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

import static de.udo.editor.exceptions.ValidatorException.ValidationErrorType.NUMBER_INPUT_INVALID;

@Data
public class NumberInput implements Required, SelfValidation, InteractionElement {

  @JsonIgnore
  private final Map<String, String> parameter;
  @NotNull
  private Boolean required;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  private FunctionCall replyFunctionCall;
  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  private FunctionCall forwardFunctionCall;
  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  private String nextStepKey;


  public NumberInput() {
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

  @Override
  public void internalValidate() {
    validateInteractionElement();
  }

  @Override
  public ValidatorException.ValidationErrorType getValidationErrorType() {
    return NUMBER_INPUT_INVALID;
  }
}
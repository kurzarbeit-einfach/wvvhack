package de.udo.editor.entities;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import de.udo.editor.exceptions.ValidatorException;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

import static de.udo.editor.exceptions.ValidatorException.ValidationErrorType.BUTTON_INCOMPLETE;

@Data
public class Button implements SelfValidation, InteractionElement {

  @JsonIgnore
  private Map<String, String> parameter = new HashMap<>();

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  private FunctionCall replyFunctionCall;
  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  private FunctionCall forwardFunctionCall;
  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  private String nextStepKey;

  @NotNull
  private String icon;
  @NotNull
  private String text;
  @NotNull
  private String value;

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
    return BUTTON_INCOMPLETE;
  }
}

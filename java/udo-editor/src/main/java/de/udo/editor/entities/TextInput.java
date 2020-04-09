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

import static de.udo.editor.exceptions.ValidatorException.ValidationErrorType.TEXT_INPUT_INVALID;

@Data
public class TextInput implements Required, SelfValidation, InteractionElement {
//
//  @JsonIgnore
//  private final Map<String, String> parameter;

  @NotNull
  private Boolean required;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  private FunctionCall replyFunctionCall;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  private FunctionCall forwardFunctionCall;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  private String nextStepKey;

//  public TextInput(){
//    parameter = new HashMap<>();
//  }

  private String defaultValue;
  private String regExPattern;
  private Integer minLength;
  private Integer maxLength;
  private String placeholder;
  private Integer rows;
//
//  @JsonAnyGetter
//  public Map<String, String> any() {
//    return parameter;
//  }

//  @JsonAnySetter
//  public void set(String name, String value) {
//    parameter.put(name, value);
//  }

  @Override
  public void internalValidate() {
    validateInteractionElement();
  }


  @Override
  public ValidatorException.ValidationErrorType getValidationErrorType() {
    return TEXT_INPUT_INVALID;
  }
}

package de.udo.editor.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.udo.editor.exceptions.ValidatorException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

import static de.udo.editor.exceptions.ValidatorException.ValidationErrorType.MONTH_INPUT_INCLOMPLETE;

@Getter
@Setter
@NoArgsConstructor
public class MonthInput implements SelfValidation, InteractionElement {

  @NotNull
  private Integer defaultFromRelative;
  @NotNull
  private Integer minMonthFromRelative;
  @NotNull
  private Integer maxMonthFromRelative;

  @NotNull
  private String relativeField;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  private FunctionCall replyFunctionCall;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  private FunctionCall forwardFunctionCall;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  private String nextStepKey;

  @Override
  public void internalValidate() {
    validateInteractionElement();
  }

  @Override
  public ValidatorException.ValidationErrorType getValidationErrorType() {
    return MONTH_INPUT_INCLOMPLETE;
  }
}

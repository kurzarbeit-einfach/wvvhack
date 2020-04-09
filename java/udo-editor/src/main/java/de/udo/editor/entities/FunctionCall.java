package de.udo.editor.entities;

import de.udo.editor.exceptions.ValidatorException;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

import static de.udo.editor.exceptions.ValidatorException.ValidationErrorType.FUNCTION_CALL_INCOMPLETE;

@Data
public class FunctionCall implements SelfValidation {

  @NotEmpty
  private String name;

}

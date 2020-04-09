package de.udo.editor.exceptions;

import lombok.Getter;

@Getter
public class ValidatorException extends RuntimeException{
  public enum ValidationErrorType {
    DEAD_END_DETECTED,INTERACTION_INCOMPLETE,MONTH_INPUT_INCLOMPLETE, FUNCTION_CALL_INCOMPLETE,STEP_CONFIG_INCOMPLETE,STEP_INCOMPLETE, TEXT_INCOMPLETE,BUTTON_INCOMPLETE,
    TEXT_INPUT_INVALID, NEXT_STEP_KEYS_INVALID, NUMBER_INPUT_INVALID;
  }

  private ValidationErrorType validationErrorType;

  public ValidatorException(ValidationErrorType validationErrorType, String message){
    super (validationErrorType.name() + ": "+message);
    this.validationErrorType = validationErrorType;
  }

}

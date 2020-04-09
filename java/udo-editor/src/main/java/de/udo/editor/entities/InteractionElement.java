package de.udo.editor.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.udo.editor.exceptions.ValidatorException;

import static de.udo.editor.exceptions.ValidatorException.ValidationErrorType.BUTTON_INCOMPLETE;

public interface InteractionElement {

  FunctionCall getReplyFunctionCall();
  FunctionCall getForwardFunctionCall();
  String getNextStepKey();

  @JsonIgnore
  ValidatorException.ValidationErrorType getValidationErrorType();

  default void validateInteractionElement(){
    if (null == getForwardFunctionCall() && null == getNextStepKey()){
      throw new ValidatorException(getValidationErrorType(), "nextStepKey and forwandFunction are null one has to bet set");
    }

    if (null != getForwardFunctionCall() && null != getNextStepKey()){
      throw new ValidatorException(getValidationErrorType(), "nextStepKey and forwandFunction are not null only (xsd:choice) has to bet set");
    }

  }

}

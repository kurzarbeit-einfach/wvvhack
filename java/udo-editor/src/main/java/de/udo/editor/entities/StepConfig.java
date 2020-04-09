package de.udo.editor.entities;

import de.udo.editor.exceptions.ValidatorException;
import lombok.Data;
import org.apache.commons.lang3.ArrayUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static de.udo.editor.exceptions.ValidatorException.ValidationErrorType.STEP_CONFIG_INCOMPLETE;

@Data
public class StepConfig implements SelfValidation {
  @NotNull
  private String startStepKey;
  @NotEmpty
  private String[] exitSteps;
  @NotEmpty //und beim Start?
  private Map<String,@Valid Step> steps;

  @Override
  public void internalValidate() {
    if (ArrayUtils.isEmpty(exitSteps)) {
      throw new ValidatorException(STEP_CONFIG_INCOMPLETE, "exitSteps is empty");
    }

    if (ArrayUtils.isEmpty(exitSteps)) {
      throw new ValidatorException(STEP_CONFIG_INCOMPLETE, "exitSteps is empty");
    }

    if (null == steps || steps.isEmpty()) {
      throw new ValidatorException(STEP_CONFIG_INCOMPLETE, "steps is empty");
    }
  }

  @Override
  public List<SelfValidation> getChilds() {
    final ArrayList<SelfValidation> selfValidations = new ArrayList<>(steps.values());

    return selfValidations;
  }


}

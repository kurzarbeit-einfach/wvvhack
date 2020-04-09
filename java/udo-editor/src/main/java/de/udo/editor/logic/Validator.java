package de.udo.editor.logic;//package de.udo.editor.logic;

import de.udo.editor.entities.StepConfig;
import de.udo.editor.exceptions.ValidatorException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class Validator {



  public void validate(StepConfig config) {
    new InternalValidator(config).validate();
  }

  @AllArgsConstructor
  private static class InternalValidator {

    private final StepConfig stepConfig;


    public void validate() {

      validateDeadEnds();
      stepConfig.validate();

    }

    private void validateDeadEnds() {
      final Set<String> knownSteps = stepConfig.getSteps().keySet();
      final List<String> exitSteps = Arrays.asList(stepConfig.getExitSteps());

      final Map<String, Collection<String>>
        step2NextStep =
        stepConfig.getSteps().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().getNextStepKeys().getParameter().values()));

      final Map<String, Collection<String>>
        stepWithDeadEnd =
        step2NextStep.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().stream().filter(stepkey -> isUnknown(knownSteps, stepkey))
          .filter(stepKey -> isNotAExitStep(exitSteps, stepKey))
          .collect(Collectors.toSet()))).entrySet().
          stream().filter(e -> !e.getValue().isEmpty()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue
        ));

      if (!stepWithDeadEnd.isEmpty()) { //note the !
        throw new ValidatorException(ValidatorException.ValidationErrorType.DEAD_END_DETECTED, "noEntries expected: " + stepWithDeadEnd.toString());
      }
    }

    private boolean notContains(Collection<String> steps, String stepKey) {
      return !steps.contains(stepKey);
    }

    private boolean isUnknown(Set<String> knownSteps, String stepKey) {
      return notContains(knownSteps, stepKey);

    }

    private boolean isNotAExitStep(List<String> exitSteps, String stepKey) {
      return notContains(exitSteps, stepKey);
    }
  }
}

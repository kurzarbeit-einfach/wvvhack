package de.udo.editor.entities;

import de.udo.editor.entities.testOnlyHelper.ResourceHelper;
import de.udo.editor.exceptions.ValidatorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import static de.udo.editor.entities.testOnlyHelper.StepHelper.SCHRITT_005;
import static de.udo.editor.entities.testOnlyHelper.StepHelper.SCHRITT_006;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class MonthInputTest {

  private MonthInput inputRelative;

  Map<String, Step> steps;

  @BeforeEach
  void init() throws IOException {
    steps = ResourceHelper.getFromResources().getSteps();
    assertThat(steps, is(notNullValue()));
  }

  @Test
  void test_MonthInputRelative() {
    inputRelative = steps.get(SCHRITT_005).getInteraction().getMonthInput();
    assertThat(inputRelative, is(notNullValue()));
    assertThat(inputRelative.getMinMonthFromRelative(), is(equalTo(-1)));
    assertThat(inputRelative.getMaxMonthFromRelative(), is(equalTo(18)));
    assertThat(inputRelative.getDefaultFromRelative(), is(equalTo(0)));
    assertThat(inputRelative.getRelativeField(), is(equalTo("vierterSchritt")));
  }


  @Test
  void test_MonthInput_with_newDate_without_RelativeField() throws IOException {
    inputRelative = steps.get(SCHRITT_006).getInteraction().getMonthInput();
    assertThat(inputRelative.getRelativeField(), is(nullValue()));
  }

  @Test
  void testValidate(){
    steps.get(SCHRITT_005).getInteraction().getMonthInput().validate();
    steps.get(SCHRITT_006).getInteraction().getMonthInput().validate();
  }

  @Test
  void test_internal_validate_negative(){
    ValidatorException validatorException = null;
    try {
      new MonthInput().validate();
    } catch (ValidatorException ex){
      validatorException = ex;
    }
    assertThat(validatorException.getValidationErrorType(), is(ValidatorException.ValidationErrorType.MONTH_INPUT_INCLOMPLETE));
  }

  @Test
  void test_internal_validate_negative_Standard_ValidationFramework() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    Set<ConstraintViolation<MonthInput>> result = validator.validate(new MonthInput());
    assertThat(result.size(), is(greaterThan(3)));
  }


}

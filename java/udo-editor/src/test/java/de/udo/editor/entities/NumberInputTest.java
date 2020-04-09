package de.udo.editor.entities;

import de.udo.editor.entities.testOnlyHelper.ResourceHelper;
import org.apache.commons.lang3.NotImplementedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.util.Set;

import static de.udo.editor.entities.testOnlyHelper.StepHelper.SCHRITT_007;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class NumberInputTest {

  private NumberInput numberInput;




  @BeforeEach
  void init() throws IOException {
    numberInput = ResourceHelper.getFromResources().getSteps().get(SCHRITT_007).getInteraction().getNumberInput();
    assertThat(numberInput,is(notNullValue()));
  }

  @Test
  void test_IntegerInput() {
    assertThat(numberInput.getParameter().get("defaultValue"),is(equalTo("1")));
    assertThat(numberInput.getParameter().get("minValue"),is(equalTo("-1")));
    assertThat(numberInput.getParameter().get("maxValue"),is(equalTo("10")));
    assertThat(numberInput.getParameter().get("stepValue"),is(equalTo("0.5")));
  }

  @Test
  void test_IntegerInputReply_WithFunction() {
    assertThat(numberInput.getReplyFunctionCall(),is(notNullValue()));
    assertThat(numberInput.getReplyFunctionCall(),is(instanceOf(FunctionCall.class)));
  }

  @Test
  void test_IntegerForward_WithFunction() {
    assertThat(numberInput.getForwardFunctionCall(),is(notNullValue()));
    assertThat(numberInput.getForwardFunctionCall(),is(instanceOf(FunctionCall.class)));
  }

  @Test
  void test_internal_validate_negative_Standard_ValidationFramework() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    Set<ConstraintViolation<NumberInput>> result = validator.validate(new NumberInput());
    assertThat(result.size(), is(greaterThan(0)));
  }

}
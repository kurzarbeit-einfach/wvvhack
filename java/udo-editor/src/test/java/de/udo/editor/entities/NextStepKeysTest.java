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
import java.util.Set;

import static de.udo.editor.entities.testOnlyHelper.StepHelper.SCHRITT_003;
import static de.udo.editor.entities.testOnlyHelper.StepHelper.SCHRITT_008;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class NextStepKeysTest {

  NextStepKeys nextStepKeys;

  @BeforeEach
  void init() throws IOException {
    nextStepKeys = ResourceHelper.getFromResources().getSteps().get(SCHRITT_003).getNextStepKeys();
    assertThat(nextStepKeys, is(notNullValue()));
  }

  @Test
  void test(){
    assertThat(nextStepKeys.getParameter(), is(notNullValue()));
    assertThat(nextStepKeys.getParameter().get("value2"),is(equalTo("dritterSchrittB")));
  }


  @Test
  void test_validate() throws IOException {
    nextStepKeys.validate();
  }

  @Test
  void test_internal_validate_negative() {
    ValidatorException validatorException = null;
    try {
      new NextStepKeys().validate();
    } catch (ValidatorException ex) {
      validatorException = ex;
    }
    assertThat(validatorException, is(nullValue()));
  }


  @Test
  void test_internal_validate_negative_Standard_ValidationFramework() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    Set<ConstraintViolation<NextStepKeys>> result = validator.validate(new NextStepKeys());
    assertThat(result.size(), is(equalTo(0)));
  }

}

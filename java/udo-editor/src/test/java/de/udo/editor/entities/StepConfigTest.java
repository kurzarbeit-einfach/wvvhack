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
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class StepConfigTest {


  private StepConfig config = null;

  @BeforeEach
  void init() throws IOException {
    config = ResourceHelper.getFromResources();
  }

  @Test
  void check_StepCount() {
    assertThat(config.getSteps().size(), is(greaterThan(1)));
  }

  @Test
  void checkStartStep() {
    assertThat(config.getStartStepKey(), is(equalTo("ersterSchritt")));
  }

  @Test
  void check_exitSteps() {
    assertThat(config.getExitSteps()[0], is(equalTo("ende_ohne_pdf")));
  }


  @Test
  void testValidate(){
    config.validate();
  }

  @Test
  void testChilds(){
    List<SelfValidation> selfValidations = config.getChilds();
    assertThat(selfValidations, is(notNullValue()));
    assertThat(selfValidations.size(), is(greaterThan(3)));

  }


  @Test
  void test_internal_validate_negative(){
    ValidatorException validatorException = null;
    try {
      new StepConfig().validate();
    } catch (ValidatorException ex){
      validatorException = ex;
    }
    assertThat(validatorException.getValidationErrorType(), is(ValidatorException.ValidationErrorType.STEP_CONFIG_INCOMPLETE));
  }

  @Test
  void test_internal_validate_negative_Standard_ValidationFramework() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    Set<ConstraintViolation<StepConfig>> result = validator.validate(new StepConfig());
    assertThat(result.size(), is(greaterThan(2)));
  }
}

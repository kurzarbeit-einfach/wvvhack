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

import static de.udo.editor.entities.testOnlyHelper.StepHelper.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class FunctionCallTest {

  private FunctionCall functionCall;


  @BeforeEach
  void init() throws IOException {
    functionCall = ResourceHelper.getFromResources().getSteps().get(SCHRITT_007).getInteraction().getNumberInput().getForwardFunctionCall();
        assertThat(functionCall, is(notNullValue()));
  }

  //
//
  @Test
  void test_functionCall() {
    assertThat(functionCall.getName(), is(equalTo("functionToCall")));
  }


  @Test
  void testValidate(){
    functionCall.validate();
  }

  @Test
  void test_internal_validate_negative(){
    ValidatorException validatorException = null;
    try {
      new FunctionCall().validate();
    } catch (ValidatorException ex){
      validatorException = ex;
    }
    assertThat(validatorException, is(nullValue()));
  }


  @Test
  void test_internal_validate_negative_Standard_ValidationFramework() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    Set<ConstraintViolation<MonthInput>> result = validator.validate(new MonthInput());
    assertThat(result.size(), is(greaterThan(3)));
  }

}
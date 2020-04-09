package de.udo.editor.entities;

import de.udo.editor.entities.testOnlyHelper.ResourceHelper;
import de.udo.editor.exceptions.ValidatorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableValidator;
import java.io.IOException;
import java.util.Set;

import static de.udo.editor.entities.testOnlyHelper.StepHelper.SCHRITT_007;
import static javax.validation.Validation.buildDefaultValidatorFactory;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class TextTest {
  private Text text = null;

  @BeforeEach
  void init() throws IOException {
    text = ResourceHelper.getFromResources().getSteps().get(SCHRITT_007).getText();
  }


  @Test
  void testValidate() {
    text.validate();
  }

  @Test
  void testChilds() {
    assertThat(text.getChilds(), is(notNullValue()));
    assertThat(text.getChilds().size(), is(0));
  }


  @Test
  void test_internal_validate_negative() {

    ValidatorException validatorException = null;
    try {
      new Text().validate();
    } catch (ValidatorException ex) {
      validatorException = ex;
    }
    assertThat(validatorException, is(nullValue()));
  }



  @Test
  void test_internal_validate_negative_Standard_ValidationFramework() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    Set<ConstraintViolation<Text>> result = validator.validate(new Text());
    assertThat(result.size(), is(greaterThan(0)));
  }


}
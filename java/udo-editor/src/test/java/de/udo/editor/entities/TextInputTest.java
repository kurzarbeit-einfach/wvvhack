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

import static de.udo.editor.entities.testOnlyHelper.StepHelper.SCHRITT_002;
import static de.udo.editor.entities.testOnlyHelper.StepHelper.SCHRITT_008;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class TextInputTest {

  private TextInput textInput;

  @BeforeEach
  void init() throws IOException {
    textInput = ResourceHelper.getFromResources().getSteps().get(SCHRITT_002).getInteraction().getTextInput();
    assertThat(textInput, is(notNullValue()));
  }


  @Test
  void test_TextInput() {
    assertThat(textInput.getDefaultValue(), is(equalTo("Standard")));
    assertThat(textInput.getRequired(), is(equalTo(Boolean.FALSE)));
  }


  @Test
  void test_TextInputWithRegExPattern() throws IOException {
    textInput = ResourceHelper.getFromResources().getSteps().get(SCHRITT_008).getInteraction().getTextInput();
    assertThat(textInput, is(notNullValue()));;
    assertThat(textInput.getRegExPattern(), is(equalTo("[0-9]+")));
  }

  @Test
  void test_TextInput_with_maxLength() throws IOException {
    textInput = ResourceHelper.getFromResources().getSteps().get(SCHRITT_008).getInteraction().getTextInput();
    assertThat(textInput, is(notNullValue()));;
    assertThat(textInput.getMaxLength(), is(equalTo(100)));
  }

  @Test
  void test_TextInput_with_minLength() throws IOException {
    textInput = ResourceHelper.getFromResources().getSteps().get(SCHRITT_008).getInteraction().getTextInput();
    assertThat(textInput, is(notNullValue()));;
    assertThat(textInput.getMinLength(), is(equalTo(30)));
  }

  @Test
  void test_TextInput_with_placeholder() throws IOException {
    textInput = ResourceHelper.getFromResources().getSteps().get(SCHRITT_008).getInteraction().getTextInput();
    assertThat(textInput, is(notNullValue()));;
    assertThat(textInput.getPlaceholder(), is(equalTo("PackWatRein")));
  }

  @Test
  void test_TextInput_with_rows() throws IOException {
    textInput = ResourceHelper.getFromResources().getSteps().get(SCHRITT_008).getInteraction().getTextInput();
    assertThat(textInput, is(notNullValue()));;
    assertThat(textInput.getRows(), is(equalTo(24)));
  }

  @Test
  void test_validate() throws IOException {
    textInput = ResourceHelper.getFromResources().getSteps().get(SCHRITT_008).getInteraction().getTextInput();
    textInput.validate();
  }

  @Test
  void test_internal_validate_negative() {
    ValidatorException validatorException = null;
    try {
      new TextInput().validate();
    } catch (ValidatorException ex) {
      validatorException = ex;
    }
    assertThat(validatorException, is(notNullValue()));
    assertThat(validatorException.getValidationErrorType(), is(ValidatorException.ValidationErrorType.TEXT_INPUT_INVALID));
  }


  @Test
  void test_internal_validate_negative_Standard_ValidationFramework() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    Set<ConstraintViolation<TextInput>> result = validator.validate(new TextInput());
    assertThat(result.size(), is(greaterThan(0)));
  }







}
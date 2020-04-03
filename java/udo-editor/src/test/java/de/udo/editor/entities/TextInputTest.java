package de.udo.editor.entities;

import de.udo.editor.entities.testOnlyHelper.ResourceHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

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
    assertThat(textInput.getParameter().get("defaultValue"), is(equalTo("Standard")));
    assertThat(textInput.getRequired(), is(equalTo(Boolean.FALSE)));
  }


  @Test
  void test_TextInputWithValidations() throws IOException {
    textInput = ResourceHelper.getFromResources().getSteps().get(SCHRITT_008).getInteraction().getTextInput();
    assertThat(textInput, is(notNullValue()));
    assertThat(textInput.getValidations(), is(notNullValue()));
    assertThat(textInput.getValidations().length, is(equalTo(2)));
  }

  @Test
  void test_TextInputWithRegExPattern() throws IOException {
    textInput = ResourceHelper.getFromResources().getSteps().get(SCHRITT_008).getInteraction().getTextInput();
    assertThat(textInput, is(notNullValue()));;
    assertThat(textInput.getParameter().get("regExPattern"), is(equalTo("[0-9]+")));
  }

}
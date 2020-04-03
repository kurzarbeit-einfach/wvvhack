package de.udo.editor.entities.testOnlyHelper;

import de.udo.editor.entities.Validation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static de.udo.editor.entities.testOnlyHelper.StepHelper.SCHRITT_008;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ValidationTest {

  private Validation validation;
  @BeforeEach
  void init() throws IOException {
    validation  = ResourceHelper.getFromResources().getSteps().get(SCHRITT_008).getInteraction().getTextInput().getValidations()[0];
    assertThat(validation, is(notNullValue()));
  }


  @Test
  void test_TextInput() {
    assertThat(validation.getType(), is(equalTo("minLength")));
    assertThat(validation.getNextStepKey(), is(equalTo("siebterSchrittA")));
    assertThat(validation.getParameter().get("length"), is(equalTo("30")));

  }
}

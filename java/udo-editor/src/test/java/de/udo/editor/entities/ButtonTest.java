package de.udo.editor.entities;

import de.udo.editor.entities.testOnlyHelper.ResourceHelper;
import de.udo.editor.entities.testOnlyHelper.StepHelper;
import org.apache.commons.lang3.NotImplementedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

class ButtonTest {


  private Button button;


  @BeforeEach
  void init() throws IOException {
    final StepConfig stepConfig = ResourceHelper.getFromResources();
    assertThat(stepConfig, is(notNullValue()));
    button = stepConfig.getSteps().get(StepHelper.SCHRITT_003).getInteraction().buttons[1];
  }

  @Test
  void test_button() {

    assertThat(button, is(notNullValue()));
    assertThat(button.getParameter().get("icon"), is("sitemap"));
    assertThat(button.getParameter().get("text"), is("schoenerText"));
    assertThat(button.getParameter().get("value"), is("value2"));
    assertThat(button.getParameter().get("nextStepKey"), is("value2"));
  }

}
package de.udo.editor.entities;

import de.udo.editor.entities.testOnlyHelper.ResourceHelper;
import org.apache.commons.lang3.NotImplementedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static de.udo.editor.entities.testOnlyHelper.StepHelper.SCHRITT_005;
import static de.udo.editor.entities.testOnlyHelper.StepHelper.SCHRITT_006;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class MonthInputRelativeTest {

  private MonthInputRelative inputRelative;

  Map<String, Step> steps;

  @BeforeEach
  void init() throws IOException {
    steps = ResourceHelper.getFromResources().getSteps();
    assertThat(steps, is(notNullValue()));
  }

  @Test
  void test_MonthInputRelative() {
    inputRelative = steps.get(SCHRITT_005).getInteraction().getMonthInputRelative();
    assertThat(inputRelative, is(notNullValue()));
    assertThat(inputRelative.getMinMonthFromRelative(), is(equalTo(0)));
    assertThat(inputRelative.getMaxMonthFromRelative(), is(equalTo(18)));
    assertThat(inputRelative.getRelative(), is(equalTo("vierterSchritt")));
  }


  @Test
  void test_MonthInput_with_newDate() throws IOException {
    inputRelative = steps.get(SCHRITT_006).getInteraction().getMonthInputRelative();
    assertThat(inputRelative, is(notNullValue()));
    assertThat(inputRelative.getMinMonthFromRelative(), is(equalTo(-1)));
    assertThat(inputRelative.getMaxMonthFromRelative(), is(equalTo(6)));
    assertThat(inputRelative.getRelative(), is(equalTo("TODO:nowNewFieldInState")));
  }
}

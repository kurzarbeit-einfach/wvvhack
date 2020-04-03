package de.udo.editor.entities;


import de.udo.editor.entities.testOnlyHelper.ResourceHelper;
import de.udo.editor.entities.testOnlyHelper.StepHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class StepTest {


  private Map<String, Step> steps = null;

  @BeforeEach
  void init() throws IOException {
    steps = ResourceHelper.getFromResources().getSteps();
  }

  @Test
  void test_contains_ersterSchritt() {
    assertThat(steps,
      hasKey(StepHelper.SCHRITT_001)
    );
  }

  @Test
  void test_nextStepKeys() {
    assertThat(steps.get(StepHelper.SCHRITT_001).getNextStepKeys(),
      is(notNullValue())
    );
  }

  @Test
  void test_nextType() {
    assertThat(steps.get(StepHelper.SCHRITT_001).getType(),
      is(notNullValue())
    );
    assertThat(steps.get(StepHelper.SCHRITT_001).getType(),
      is(equalTo("textAndInteraction"))
    );
  }

  @Test
  void test_request() {
    assertThat(steps.get(StepHelper.SCHRITT_001).getRequest(),
      is(notNullValue())
    );
  }

  @Test
  void test_interaction() {
    assertThat(steps.get(StepHelper.SCHRITT_001).getInteraction(),
      is(notNullValue())
    );
  }
}

package de.udo.editor.entities;

import de.udo.editor.entities.testOnlyHelper.ResourceHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

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
}

package de.udo.editor.entities;

import de.udo.editor.entities.testOnlyHelper.ResourceHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static de.udo.editor.entities.testOnlyHelper.StepHelper.SCHRITT_003;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class NextStepKeysTest {

  NextStepKeys nextStepKeys;

  @BeforeEach
  void init() throws IOException {
    nextStepKeys = ResourceHelper.getFromResources().getSteps().get(SCHRITT_003).getNextStepKeys();
    assertThat(nextStepKeys, is(notNullValue()));
  }

  @Test
  void test(){
    assertThat(nextStepKeys.getParameter(), is(notNullValue()));
    assertThat(nextStepKeys.getParameter().get("value2"),is(equalTo("dritterSchrittB")));
  }
}

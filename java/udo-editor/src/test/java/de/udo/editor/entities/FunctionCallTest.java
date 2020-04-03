package de.udo.editor.entities;

import de.udo.editor.entities.testOnlyHelper.ResourceHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static de.udo.editor.entities.testOnlyHelper.StepHelper.SCHRITT_007;
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
    assertThat(functionCall.getParameter().get("param1"), is(equalTo("achterSchritt")));
  }

}
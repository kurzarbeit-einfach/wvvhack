package de.udo.editor.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.udo.editor.entities.testOnlyHelper.ResourceHelper;
import de.udo.editor.entities.testOnlyHelper.StepHelper;
import org.apache.commons.lang3.NotImplementedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class ConfirmButtonTest {

  private ConfirmButton confirmButton;

  @BeforeEach
  void init() throws IOException {
    final StepConfig stepConfig = ResourceHelper.getFromResources();
    assertThat(stepConfig, is(notNullValue()));
    confirmButton = stepConfig.getSteps().get(StepHelper.SCHRITT_001).getInteraction().getConfirmButton();
  }


  @Test
  void test_confirmButton() {
    assertThat(confirmButton, is(notNullValue()));
    assertThat(confirmButton.getParameter().get("defaultValue"),is(nullValue()));
    assertThat(confirmButton.getRequired(),is(equalTo(Boolean.TRUE)));
  }

}
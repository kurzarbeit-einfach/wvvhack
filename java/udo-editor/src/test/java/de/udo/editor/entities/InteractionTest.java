package de.udo.editor.entities;

import de.udo.editor.entities.testOnlyHelper.ResourceHelper;
import de.udo.editor.entities.testOnlyHelper.StepHelper;
import org.apache.commons.lang3.NotImplementedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class InteractionTest {

  Map<String, Step> stepMap;



  @BeforeEach
  void init() throws IOException {
    stepMap = ResourceHelper.getFromResources().getSteps();
    assertThat(stepMap, is(notNullValue()));
  }

  Interaction interaction;


  @Test
  void test_confirmButton()  {
    interaction = stepMap.get(StepHelper.SCHRITT_001).getInteraction();
    assertThat(interaction, is(notNullValue()));
    assertThat(interaction.getConfirmButton(), is(notNullValue()));
  }

  @Test
  void test_textInput()  {
    interaction = stepMap.get(StepHelper.SCHRITT_002).getInteraction();
    assertThat(interaction, is(notNullValue()));
    assertThat(interaction.getTextInput(), is(notNullValue()));
  }

  @Test
  void test_IntegerInput()  {
    interaction = stepMap.get(StepHelper.SCHRITT_004).getInteraction();
    assertThat(interaction, is(notNullValue()));
    assertThat(interaction.getNumberInput(), is(notNullValue()));
  }



  @Test
  void test_textButtons()  {
    interaction = stepMap.get(StepHelper.SCHRITT_003).getInteraction();
    assertThat(interaction, is(notNullValue()));
    assertThat(interaction.getButtons(), is(notNullValue()));
    assertThat(interaction.getButtons().length, is(equalTo(2)));
  }

  @Test
  void test_monthInputRelative()  {
    interaction = stepMap.get(StepHelper.SCHRITT_005).getInteraction();
    assertThat(interaction, is(notNullValue()));
    assertThat(interaction.getMonthInputRelative(), is(notNullValue()));
  }

}
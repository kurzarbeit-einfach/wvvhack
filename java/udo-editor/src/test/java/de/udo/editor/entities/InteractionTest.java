package de.udo.editor.entities;

import de.udo.editor.entities.testOnlyHelper.ResourceHelper;
import de.udo.editor.entities.testOnlyHelper.StepHelper;
import de.udo.editor.exceptions.ValidatorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import static de.udo.editor.exceptions.ValidatorException.ValidationErrorType.INTERACTION_INCOMPLETE;
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
    assertThat(interaction.getMonthInput(), is(notNullValue()));
  }

  @Test
  void test_internal_validate_positive(){
    stepMap.values().stream().forEach(
      step -> {
        step.getInteraction().validate();
      }
    );
  }


  @Test
  void testChilds() {
    interaction = stepMap.get(StepHelper.SCHRITT_005).getInteraction();
    assertThat(interaction.getChilds(), is(notNullValue()));
    assertThat(interaction.getChilds().size(), is(greaterThan(0)));
  }


  @Test
  void test_internal_validate_negative(){
    ValidatorException validatorException = null;
    try {
      new Interaction().validate();
    } catch (ValidatorException ex){
      validatorException = ex;
    }
    assertThat(validatorException.getValidationErrorType(), is(INTERACTION_INCOMPLETE));
  }

  @Test
  void test_internal_validate_negative_Standard_ValidationFramework() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    Set<ConstraintViolation<Interaction>> result = validator.validate(new Interaction());
    assertThat(result.size(), is(equalTo(0)));
  }


}
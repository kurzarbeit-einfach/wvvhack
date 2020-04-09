package de.udo.editor.entities;

import de.udo.editor.entities.testOnlyHelper.ResourceHelper;
import de.udo.editor.entities.testOnlyHelper.StepHelper;
import de.udo.editor.exceptions.ValidatorException;
import org.apache.commons.lang3.NotImplementedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.util.Set;

import static de.udo.editor.entities.testOnlyHelper.StepHelper.SCHRITT_005;
import static de.udo.editor.entities.testOnlyHelper.StepHelper.SCHRITT_006;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

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
    assertThat(button.getIcon(), is("sitemap"));
    assertThat(button.getText(), is("schoenerText"));
    assertThat(button.getValue(), is("value2"));
    assertThat(button.getNextStepKey(), is("value2"));
  }


  @Test
  void testValidate(){
    button.validate();
  }

  @Test
  void test_internal_validate_negative(){

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    Set<ConstraintViolation<Button>> result = validator.validate(new Button());
    assertThat(result.size(), is(greaterThan(2)));
  }
}
package de.udo.editor.entities;


import de.udo.editor.entities.testOnlyHelper.ResourceHelper;
import de.udo.editor.entities.testOnlyHelper.StepHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

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
  void test_text() {
    assertThat(steps.get(StepHelper.SCHRITT_001).getText(),
      is(notNullValue())
    );
  }

  @Test
  void test_interaction() {
    assertThat(steps.get(StepHelper.SCHRITT_001).getInteraction(),
      is(notNullValue())
    );
  }

  @Test
  void test_internal_validate_negative_Standard_ValidationFramework() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    Set<ConstraintViolation<Step>> result = validator.validate(new Step());
    assertThat(result.size(), is(greaterThan(3)));
  }
}

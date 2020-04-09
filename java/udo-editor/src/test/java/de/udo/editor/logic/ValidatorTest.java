package de.udo.editor.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.udo.editor.entities.StepConfig;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

class ValidatorTest {

  private Validator validator;

  public static StepConfig getFromResources() throws IOException {

    final ObjectMapper objectMapper = new ObjectMapper();
    final String stepConfigJson;
    final StepConfig result;
    try
      (final InputStream resourceAsStream = StepConfig.class.getResourceAsStream("/static/stepconfig.json")) {
      MatcherAssert.assertThat(resourceAsStream, Matchers.is(Matchers.not(Matchers.nullValue())));
      stepConfigJson = new String(resourceAsStream.readAllBytes());
    }
    MatcherAssert.assertThat(stepConfigJson, Matchers.is(Matchers.not(Matchers.emptyOrNullString())));
    result = objectMapper.readValue(stepConfigJson, StepConfig.class);
    MatcherAssert.assertThat(result, Matchers.is(Matchers.notNullValue()));
    MatcherAssert.assertThat(result.getSteps(), Matchers.is(Matchers.notNullValue()));

    return result;


  }

  @BeforeEach
  void init ()  {
    validator = new Validator();
  }

  @Test
  void test()throws IOException {
    validator.validate(getFromResources());
  }

}
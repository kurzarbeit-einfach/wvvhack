package de.udo.editor.entities.testOnlyHelper;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.udo.editor.entities.StepConfig;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

import java.io.IOException;
import java.io.InputStream;

public final class ResourceHelper {

  final static ObjectMapper objectMapper = new ObjectMapper();

  public static StepConfig getFromResources() throws IOException {


    final String stepConfigJson;
    final StepConfig result;
    try
      (final InputStream resourceAsStream = StepConfig.class.getResourceAsStream("stepconfig.json")) {
      MatcherAssert.assertThat(resourceAsStream, Matchers.is(Matchers.not(Matchers.nullValue())));
      stepConfigJson = new String(resourceAsStream.readAllBytes());
    }
    MatcherAssert.assertThat(stepConfigJson, Matchers.is(Matchers.not(Matchers.emptyOrNullString())));
    MatcherAssert.assertThat(stepConfigJson.length(), Matchers.is(Matchers.greaterThan(10)));
    result = objectMapper.readValue(stepConfigJson, StepConfig.class);
    MatcherAssert.assertThat(result, Matchers.is(Matchers.notNullValue()));
    MatcherAssert.assertThat(result.getSteps(), Matchers.is(Matchers.notNullValue()));

    return result;


  }
}
package de.udo.editor;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.udo.editor.entities.NextStepKeys;
import de.udo.editor.entities.Step;
import de.udo.editor.entities.StepConfig;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CheckRealStepConfigTest {

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


  private StepConfig config = null;

  @BeforeEach
  void init() throws IOException {
    config = getFromResources();
  }

  @Test
  void test_load(){
    assertThat(config, Matchers.is(Matchers.notNullValue()));
  }

  @Test
  void check_StepCount() {
    assertThat(config.getSteps().size(), is(greaterThan(1)));
  }

  @Test
  void checkStartStep() {
    assertThat(config.getStartStepKey(), is(equalTo("hallo")));
  }

  @Test
  void check_exitSteps() {
    assertThat(config.getExitSteps()[0], is(equalTo("ende_ohne_pdf")));
  }

  @Test
  @Disabled("not working")
  void test_step_links_no_backLinks(){
    final Step startStep = config.getSteps().get(config.getStartStepKey());

    final List<Step> processed = new ArrayList<>();



    Map<String, Set<String>>  mappingKeyToNextKeys = config.getSteps().entrySet().stream().collect(Collectors.toMap(
      entry -> entry.getKey(),
      entry -> entry.getValue().getNextStepKeys().getParameter().values().stream().collect(Collectors.toSet())));


    final List<String> validExits = new ArrayList<>();
    validExits.addAll(Arrays.asList(config.getExitSteps()));

    for (Map.Entry<String, Set<String>> entry : mappingKeyToNextKeys.entrySet() ) {

      final Set<String> collect = entry.getValue();
      collect.removeAll(validExits);
      if (collect.isEmpty()) {
        validExits.add(entry.getKey());
      }
      System.out.println(entry);
      System.out.println("validExits: " + validExits);
    }






  }
}

package de.udo.editor;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.udo.editor.entities.StepConfig;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
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
  void test_load() {
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
  void test_no_deadEnds() {

    Set<String> knownSteps = config.getSteps().keySet();
    List<String> exitSteps = Arrays.asList(config.getExitSteps());

    Map<String, Collection<String>>
      step2NextStep =
      config.getSteps().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().getNextStepKeys().getParameter().values()));

    Map<String, Collection<String>>
      stepWithDeadEnd =
      step2NextStep.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().stream().filter(stepkey -> isUnknown(knownSteps, stepkey))
        .filter(stepKey -> isNotAExitStep(exitSteps, stepKey))
        .collect(Collectors.toSet()))).entrySet().
        stream().filter(e -> !e.getValue().isEmpty()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue
      ));


    assertThat("noEntries expected: " + stepWithDeadEnd.toString(), stepWithDeadEnd.size(), is(equalTo(0)));

  }


  private boolean notContains(Collection<String> steps, String stepKey) {
    return !steps.contains(stepKey);
  }

  private boolean isUnknown(Set<String> knownSteps, String stepKey) {
    return notContains(knownSteps, stepKey);

  }

  private boolean isNotAExitStep(List<String> exitSteps, String stepKey) {
    return notContains(exitSteps, stepKey);
  }
}

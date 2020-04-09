package de.udo.editor.entities;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class JacksonMappingTest {


  @Test
  public void test_flat_map() throws JsonProcessingException {
    NextStepKeys keys = new NextStepKeys();
    keys.getParameter().put("a","1");
    keys.getParameter().put("b","2");
    keys.getParameter().put("c","3");
    String valueBefore = keys.toString();
    ObjectMapper mapper = new ObjectMapper();
    String keysAsString = mapper.writeValueAsString(keys);
    NextStepKeys keys2 = mapper.readValue(keysAsString, NextStepKeys.class);
    String valueAfter = keys2.toString();
    assertThat(valueAfter, is(Matchers.equalTo(valueBefore)));
  }

  @Test
  public void test_empty_map() throws JsonProcessingException {
    NextStepKeys keys = new NextStepKeys();
    String valueBefore = keys.toString();
    System.out.println(valueBefore);
    ObjectMapper mapper = new ObjectMapper();
    String keysAsString = mapper.writeValueAsString(keys);

    NextStepKeys keys2 = mapper.readValue(keysAsString, NextStepKeys.class);
    String valueAfter = keys2.toString();
    assertThat(valueAfter, is(Matchers.equalTo(valueBefore)));
  }


}
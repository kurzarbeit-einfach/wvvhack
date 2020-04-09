package de.udo.editor.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import de.udo.editor.entities.StepConfig;
import de.udo.editor.logic.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@RestController("/editor")
public class EditorController {

  @Autowired
  private MinimalStorage minimalStorage;

  @Autowired
  private Validator validator;

  /**
   * will be removed in future
   * @param config
   * @param externalName
   * @return
   */
  @PostMapping(value = "/stepConfig/upload", produces = {MediaType.APPLICATION_JSON_VALUE})
  ResponseEntity<Handle> upload(@RequestBody StepConfig config, @RequestParam String externalName) {
    final Handle handle = Handle.builder().externalName(externalName).build();
    return new ResponseEntity<>(minimalStorage.add(handle, config), HttpStatus.CREATED);
  }

  @PostMapping(value="/stepConfig/validateAndUpload", produces = {MediaType.APPLICATION_JSON_VALUE})
  ResponseEntity<?> validateAndUpload(
    @Valid
    @RequestBody StepConfig config, @RequestParam String externalName) {
    validator.validate(config);
    return upload(config, externalName);
  }


  @PostMapping(value="/stepConfig/fetchValidateAndUpload", produces = {MediaType.APPLICATION_JSON_VALUE})
  ResponseEntity<?> validateAndUpload(
    @RequestParam String stepConfigUrl, @RequestParam String externalName) throws IOException {
    final ObjectMapper objectMapper = new ObjectMapper();
    @Valid
    final StepConfig stepConfig = objectMapper.readValue(new URL(stepConfigUrl), StepConfig.class);
    validator.validate(stepConfig);
    return upload(stepConfig, externalName);
  }

  @RequestMapping(value = "/stepConfig/{handleId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  ResponseEntity<StepConfig> download(@PathVariable String handleId) {
    return getStepConfigResponseEntity(getById(handleId));
  }

  @RequestMapping(value = "/stepConfig/{handleId}/validate", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  ResponseEntity<StepConfig> validate(@PathVariable String handleId) {
    final StepConfig stepConfig = getById(handleId);
    validator.validate(stepConfig);
    return getStepConfigResponseEntity(stepConfig);
  }


  private ResponseEntity<StepConfig> getStepConfigResponseEntity(StepConfig stepConfig) {
    return new ResponseEntity<>(stepConfig, HttpStatus.OK);
  }

  private StepConfig getById(final String handleId) {
    return minimalStorage.getById(handleId);
  }
}

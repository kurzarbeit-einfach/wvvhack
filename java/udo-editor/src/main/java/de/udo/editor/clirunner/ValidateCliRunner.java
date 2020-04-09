package de.udo.editor.clirunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.udo.editor.entities.StepConfig;
import de.udo.editor.logic.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class ValidateCliRunner implements CommandLineRunner {

  private static final Logger logger = LoggerFactory.getLogger(ValidateCliRunner.class);

  @Autowired
  private StepConfig stepConfig;

  @Autowired
  private Validator validator;

  @Override
  public void run(String... args) throws Exception {
    validator.validate(stepConfig);

  }
}

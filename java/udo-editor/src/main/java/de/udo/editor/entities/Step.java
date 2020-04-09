package de.udo.editor.entities;

import de.udo.editor.exceptions.ValidatorException;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;

@Data
public class Step implements SelfValidation{
  @Valid @NotNull
  private NextStepKeys nextStepKeys;
  @NotEmpty
  private String type;
  @Valid @NotNull
  private Text text;
  @Valid @NotNull
  private Interaction interaction;

  @Override
  public List<SelfValidation> getChilds() {
    final ArrayList<SelfValidation> selfValidations = new ArrayList<>();
    selfValidations.add(interaction);
    selfValidations.add(text);
    selfValidations.add(nextStepKeys);
    return selfValidations;
  }
}

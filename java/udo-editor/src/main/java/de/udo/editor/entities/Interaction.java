package de.udo.editor.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.udo.editor.exceptions.ValidatorException;
import lombok.Data;
import org.apache.commons.lang3.ArrayUtils;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static de.udo.editor.exceptions.ValidatorException.ValidationErrorType.INTERACTION_INCOMPLETE;

@Data
public class Interaction implements SelfValidation {
  @Valid
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  Button[] buttons;
  @Valid
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  TextInput textInput;
  @Valid
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  NumberInput numberInput;
  @Valid
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  MonthInput monthInput;

  public void internalValidate() {
    boolean noButton = ArrayUtils.isEmpty(buttons);
    boolean noTextInput = null == textInput;
    boolean noNumberInput = null == numberInput;
    boolean noMonthInput = null == monthInput;

    if (noButton && noTextInput && noNumberInput && noMonthInput) {
      throw new ValidatorException(INTERACTION_INCOMPLETE, "no interaction specified");
    }
  }

  @Override
  public List<SelfValidation> getChilds() {
    final ArrayList<SelfValidation> childs = new ArrayList<>();
    Optional.ofNullable(buttons).ifPresent(e ->
      childs.addAll(Arrays.asList(buttons))
    );
    Optional.ofNullable(textInput).ifPresent(e -> childs.add(e));
    Optional.ofNullable(numberInput).ifPresent(e -> childs.add(e));
    Optional.ofNullable(monthInput).ifPresent(e -> childs.add(e));
    return childs;
  }
}

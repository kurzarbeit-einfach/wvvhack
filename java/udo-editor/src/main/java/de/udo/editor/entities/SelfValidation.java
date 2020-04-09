package de.udo.editor.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Collections;
import java.util.List;


public interface SelfValidation {

  /**
   * extension point if some very special validation is needed
   * throws an ValidatorException if invalid
   */
  default void internalValidate() {
    return;//is wanted here,
  }

  /**
   * throws an ValidatorException if invalid
   */
  default void validate(){
    internalValidate();
    getChilds().forEach(SelfValidation::validate);
  }

  @JsonIgnore
  default List<SelfValidation> getChilds(){
    return Collections.emptyList();
  }
}

package de.udo.editor.controller;


import lombok.*;
import org.springframework.http.HttpEntity;

import java.util.UUID;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Handle {
  @Builder.Default
  private String handleId = UUID.randomUUID().toString();
  @Builder.Default
  private String externalName = "NotSetFromOutside";
}

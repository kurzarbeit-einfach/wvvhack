package de.udo.editor.controller;

import de.udo.editor.entities.StepConfig;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MinimalStorage {

  private Map<Handle, StepConfig> poorMansStoreMap;

  private Map<String, Handle> poorMansIdMap;

  public MinimalStorage(){
    poorMansStoreMap = new HashMap<>();
    poorMansIdMap = new HashMap<>();
  }

  public Handle add(final Handle handle, final  StepConfig stepConfig){
    poorMansStoreMap.put(handle, stepConfig);
    poorMansIdMap.put(handle.getHandleId(), handle);
    return handle;
  }

  public StepConfig getById(String handleId){
    return poorMansStoreMap.get(poorMansIdMap.get(handleId));
  }
}

package com.ccsw.gtemanager.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author pajimene
 *
 */
@RequestMapping(value = "/utils")
@RestController
public class UtilsController {

  @Autowired
  UtilsService utilsService;

  /**
   * Método para recuperar la versión de la aplicación
   *
   * @return
   */
  @RequestMapping(path = "/version", method = RequestMethod.GET)
  public Map<String, Object> getVersion() {

    Map<String, Object> json = new HashMap<>();
    String version = this.utilsService.getVersion();
    json.put("version", version);
    return json;
  }

}

package com.ccsw.gtemanager.center;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ccsw.gtemanager.center.model.CenterDto;
import com.ccsw.gtemanager.config.mapper.BeanMapper;

/**
 * CenterController: Controlador REST para interacción con datos. Contiene
 * métodos de acceso a servicio de datos, asociados a Requests HTTP.
 */
@RequestMapping(value = "/center")
@RestController
public class CenterController {

    @Autowired
    private CenterService centerService;

    @Autowired
    private BeanMapper beanMapper;

    /**
     * GET: Devuelve un listado con todos los centros almacenados en base de datos.
     */
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<CenterDto> findAll() {
        return beanMapper.mapList(centerService.findAll(), CenterDto.class);
    }
}

package com.ccsw.gtemanager.evidenceerror;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ccsw.gtemanager.config.mapper.BeanMapper;
import com.ccsw.gtemanager.evidenceerror.model.EvidenceError;
import com.ccsw.gtemanager.evidenceerror.model.EvidenceErrorDto;

/**
 * EvidenceErrorController: Controlador REST para interacción con datos.
 * Contiene métodos de acceso a servicio de datos, asociados a Requests HTTP.
 *
 */
@RequestMapping(value = "/evidence-error")
@RestController
public class EvidenceErrorController {

    @Autowired
    private EvidenceErrorService errorService;

    @Autowired
    private BeanMapper beanMapper;

    /**
     * GET: Devuelve un listado de errores
     */
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<EvidenceErrorDto> findAll() {

        List<EvidenceError> errors = errorService.getEvidenceErrors();
        return this.beanMapper.mapList(errors, EvidenceErrorDto.class);
    }
}

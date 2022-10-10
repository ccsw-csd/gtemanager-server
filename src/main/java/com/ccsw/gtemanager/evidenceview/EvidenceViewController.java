package com.ccsw.gtemanager.evidenceview;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ccsw.gtemanager.config.mapper.BeanMapper;
import com.ccsw.gtemanager.evidenceview.model.EvidenceView;
import com.ccsw.gtemanager.evidenceview.model.EvidenceViewDto;

@RequestMapping(value = "/evidence-view")
@RestController
public class EvidenceViewController {

    @Autowired
    private EvidenceViewService evidenceViewService;

    @Autowired
    private BeanMapper beanMapper;

    /**
     * GET: Devuelve el listado de evidencias filtrado por geografía
     */
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<EvidenceViewDto> findByGeography(@RequestParam(value = "geography", required = false) String idGeography) {
        List<EvidenceView> evidences = evidenceViewService.findByGeography(idGeography);

        return beanMapper.mapList(evidences, EvidenceViewDto.class);
    }
}

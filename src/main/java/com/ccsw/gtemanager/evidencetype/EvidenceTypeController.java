package com.ccsw.gtemanager.evidencetype;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ccsw.gtemanager.config.mapper.BeanMapper;
import com.ccsw.gtemanager.evidencetype.model.EvidenceType;
import com.ccsw.gtemanager.evidencetype.model.EvidenceTypeDto;

@RequestMapping(value = "/evidence-type")
@RestController
public class EvidenceTypeController {
	
	@Autowired
	private EvidenceTypeService evidenceTypeService;
	
	@Autowired
	private BeanMapper beanMapper;
	
	/**
	 * GET: Devuelve el listado de tipos
	 */
	@RequestMapping(path = "", method = RequestMethod.GET)
	public List<EvidenceTypeDto> findAll() {
		List<EvidenceType> evidences = evidenceTypeService.getEvidenceTypes();
		
		return beanMapper.mapList(evidences, EvidenceTypeDto.class);
	}
}
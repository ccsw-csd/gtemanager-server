package com.ccsw.gtemanager.center;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ccsw.gtemanager.center.model.CenterDto;
import com.ccsw.gtemanager.config.mapper.BeanMapper;

/**
 * TODO DOCS
 *
 */
@RequestMapping(value = "/center")
@RestController
public class CenterController {

    @Autowired
    private CenterService centerService;

    @Autowired
    BeanMapper beanMapper;

    /**
     * TODO DOCS
     * 
     */
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<CenterDto> findAll() {
        return this.beanMapper.mapList(this.centerService.findAll(), CenterDto.class);
    }
}

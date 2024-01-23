package com.ccsw.gtemanager.blacklist;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ccsw.gtemanager.blacklist.model.Blacklist;
import com.ccsw.gtemanager.blacklist.model.BlacklistCommentRequest;
import com.ccsw.gtemanager.blacklist.model.BlacklistDto;
import com.ccsw.gtemanager.blacklist.model.BlacklistSaveRequest;
import com.ccsw.gtemanager.config.mapper.BeanMapper;

@RequestMapping(value = "/blacklist")
@RestController
public class BlacklistController {

    @Autowired
    private BlacklistService service;

    @Autowired
    private BeanMapper beanMapper;

    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<BlacklistDto> findByGeography(@RequestParam(value = "geography", required = false) String idGeography) {
        List<Blacklist> items = service.findByGeography(idGeography);

        return beanMapper.mapList(items, BlacklistDto.class);
    }

    @RequestMapping(path = "", method = RequestMethod.PUT)
    public void save(@RequestBody BlacklistSaveRequest dto) {
        service.save(dto);
    }

    @RequestMapping(path = "/save-comment", method = RequestMethod.PUT)
    public void saveComment(@RequestBody BlacklistCommentRequest dto) {
        service.saveComment(dto);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void saveComment(@PathVariable Long id) {
        service.delete(id);
    }
}

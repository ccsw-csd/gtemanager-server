package com.ccsw.gtemanager.center;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccsw.gtemanager.center.model.Center;

/**
 * CenterServiceImpl: clase de implementación de CenterService.
 */
@Service
@Transactional
public class CenterServiceImpl implements CenterService {

    @Autowired
    private CenterRepository centerRepository;

    @Override
    public List<Center> findAll() {
        return centerRepository.findAll();
    }
}

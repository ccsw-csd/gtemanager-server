package com.ccsw.gtemanager.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ccsw.gtemanager.dashboard.model.DashboardResponse;

/**
 */
@RequestMapping(value = "/dashboard")
@RestController
public class DashboardController {

    @Autowired
    DashboardService service;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public DashboardResponse getDasboardData() {

        return service.getDasboardData();
    }
}

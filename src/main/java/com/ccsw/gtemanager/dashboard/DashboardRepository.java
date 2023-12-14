package com.ccsw.gtemanager.dashboard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ccsw.gtemanager.dashboard.model.Dashboard;

@Repository
public interface DashboardRepository extends JpaRepository<Dashboard, Long> {

}

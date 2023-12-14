package com.ccsw.gtemanager.dashboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccsw.gtemanager.dashboard.model.Dashboard;
import com.ccsw.gtemanager.dashboard.model.DashboardData;
import com.ccsw.gtemanager.dashboard.model.DashboardResponse;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    DashboardRepository repository;

    @Override
    public DashboardResponse getDasboardData() {

        List<Dashboard> listDataRaw = repository.findAll();

        List<DashboardData> persons = new ArrayList<>();
        Map<String, DashboardData> clients = new HashMap<>();

        for (Dashboard data : listDataRaw) {

            DashboardData person = new DashboardData();
            person.setName(data.getPerson().getName() + " " + data.getPerson().getLastName());
            person.setWeeks(data.getEvidenceWeeks());

            persons.add(person);

            String clientList[] = data.getClient().split("; ");
            for (String clientName : clientList) {
                DashboardData client = clients.get(clientName);

                if (client == null) {
                    client = new DashboardData();
                    clients.put(clientName, client);
                    client.setName(clientName);
                    client.setWeeks(0L);
                }

                client.setWeeks(client.getWeeks() + data.getEvidenceWeeks());
            }

        }

        DashboardResponse response = new DashboardResponse();
        response.setClients(new ArrayList<>(clients.values()));
        response.setPersons(persons);

        return response;
    }

}

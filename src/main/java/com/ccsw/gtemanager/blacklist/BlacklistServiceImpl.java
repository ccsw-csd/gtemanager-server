package com.ccsw.gtemanager.blacklist;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ccsw.gtemanager.blacklist.model.Blacklist;
import com.ccsw.gtemanager.blacklist.model.BlacklistCommentRequest;
import com.ccsw.gtemanager.blacklist.model.BlacklistSaveRequest;
import com.ccsw.gtemanager.common.criteria.SearchCriteria;
import com.ccsw.gtemanager.evidenceview.EvidenceViewRepository;
import com.ccsw.gtemanager.evidenceview.model.EvidenceView;

@Service
@Transactional(readOnly = true)
public class BlacklistServiceImpl implements BlacklistService {

    @Autowired
    private BlacklistRepository repository;

    @Autowired
    private EvidenceViewRepository evidenceRepository;

    @Override
    public List<Blacklist> findByGeography(String idGeography) {
        BlacklistSpecification geography = new BlacklistSpecification(new SearchCriteria("center", "", idGeography));
        Specification<Blacklist> specification = Specification.where(geography);
        return this.repository.findAll(specification, Sort.by(Sort.Direction.ASC, "person.center.name"));
    }

    @Override
    @Transactional(readOnly = false)
    public void saveComment(BlacklistCommentRequest dto) {

        Blacklist item = repository.findById(dto.getId()).orElse(null);

        if (item == null)
            return;

        if (StringUtils.hasText(dto.getComment()))
            item.setComment(dto.getComment());
        else
            item.setComment(null);

        repository.save(item);
    }

    @Override
    @Transactional(readOnly = false)
    public void save(BlacklistSaveRequest dto) {

        LocalDate date = LocalDate.of(dto.getYear(), dto.getMonth() + 1, dto.getDay());

        BlacklistSpecification year = new BlacklistSpecification(new SearchCriteria("year", "", date.getYear()));
        BlacklistSpecification month = new BlacklistSpecification(new SearchCriteria("month", "", date.getMonthValue()));

        Specification<Blacklist> specification = Specification.where(year).and(month);
        List<Blacklist> blacklistInDDBB = this.repository.findAll(specification);

        List<EvidenceView> evidences = evidenceRepository.findByPersonIdIn(Arrays.asList(dto.getPersons()));

        for (EvidenceView evidence : evidences) {

            Blacklist blacklistFound = blacklistInDDBB.stream().filter((item) -> item.getPerson().getId().equals(evidence.getPerson().getId())).findFirst().orElse(null);
            if (blacklistFound != null)
                continue;

            Blacklist entity = new Blacklist();

            entity.setDate(Date.from(date.atStartOfDay().toInstant(ZoneOffset.UTC)));
            entity.setPerson(evidence.getPerson());

            entity.setComment(null);
            entity.setManager(evidence.getManager());
            entity.setProject(evidence.getProject());
            entity.setClient(evidence.getClient());

            repository.save(entity);
        }

    }
}

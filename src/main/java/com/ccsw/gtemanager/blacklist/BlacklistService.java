package com.ccsw.gtemanager.blacklist;

import java.util.List;

import com.ccsw.gtemanager.blacklist.model.Blacklist;
import com.ccsw.gtemanager.blacklist.model.BlacklistCommentRequest;
import com.ccsw.gtemanager.blacklist.model.BlacklistSaveRequest;

public interface BlacklistService {

    List<Blacklist> findByGeography(String idGeography);

    void saveComment(BlacklistCommentRequest dto);

    void save(BlacklistSaveRequest dto);

}

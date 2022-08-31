package com.ccsw.gtemanager.personsagatranscode;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccsw.gtemanager.personsagatranscode.model.PersonSagaTranscode;

/**
 * PersonSagaTranscodeServiceImpl: clase de implementación de
 * PersonSagaTranscodeService.
 */
@Service
@Transactional
public class PersonSagaTranscodeServiceImpl implements PersonSagaTranscodeService {

    @Autowired
    private PersonSagaTranscodeRepository personSagaTranscodeRepository;

    @Override
    public List<PersonSagaTranscode> getPersonSagaTranscodes() {
        return personSagaTranscodeRepository.findAll();
    }

}

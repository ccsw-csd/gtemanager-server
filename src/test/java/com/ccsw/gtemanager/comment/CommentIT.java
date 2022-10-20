package com.ccsw.gtemanager.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import com.ccsw.gtemanager.comment.model.CommentDto;
import com.ccsw.gtemanager.config.BaseITAbstract;
import com.ccsw.gtemanager.person.model.PersonDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CommentIT extends BaseITAbstract {

    public static final String SERVICE_PATH = "/comment";

    public static final Integer TOTAL_COMMENT = 2;
    public static final Long EXISTS_ID = 1L;
    public static final Long EXISTS_PERSON_ID = 1L;
    public static final Date EXISTS_DATE = new Date(1640991600000L);
    public static final String EXISTS_AUTHOR = "Author1";
    public static final String NEW_COMMENT = "New";

    ParameterizedTypeReference<List<CommentDto>> responseTypeComment = new ParameterizedTypeReference<List<CommentDto>>() {
    };

    @Test
    public void saveExistsCommentShouldUpdate() {

        CommentDto dto = new CommentDto();
        PersonDto personDto = new PersonDto();

        personDto.setId(EXISTS_PERSON_ID);

        dto.setId(EXISTS_ID);
        dto.setPerson(personDto);
        dto.setComment(NEW_COMMENT);

        HttpEntity<?> httpEntity = new HttpEntity<>(getHeaders());

        var save = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT,
                new HttpEntity<>(dto, getHeaders()), Void.class);

        ResponseEntity<List<CommentDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.GET, httpEntity, responseTypeComment);

        assertNotNull(response.getBody());
        assertEquals(TOTAL_COMMENT, response.getBody().size());

        CommentDto commentSearch = response.getBody().stream().filter(item -> item.getId().equals(EXISTS_ID))
                .findFirst().orElse(null);
        assertNotNull(commentSearch);
        assertEquals(NEW_COMMENT, commentSearch.getComment());
        assertNotEquals(EXISTS_AUTHOR, commentSearch.getLastEditAuthor());
        assertNotEquals(EXISTS_DATE, commentSearch.getLastEditDate());
    }
}

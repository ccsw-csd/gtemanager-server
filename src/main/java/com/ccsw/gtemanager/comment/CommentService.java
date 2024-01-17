package com.ccsw.gtemanager.comment;

import java.util.List;

import com.ccsw.gtemanager.comment.model.Comment;
import com.ccsw.gtemanager.comment.model.CommentDto;
import com.ccsw.gtemanager.common.exception.EntityNotFoundException;

public interface CommentService {
    /**
     * Obtener un listado de todos los comentarios de evidencias.
     * 
     * @return Listado de EvidenceComments
     */
    List<Comment> getComments();

    /**
     * Obtiene un comentario según su id
     * 
     * @param id
     * @return
     * @throws EntityNotFoundException
     */
    Comment get(Long id) throws EntityNotFoundException;

    /**
     * Editar un comentario
     * 
     * @param dto
     * @throws EntityNotFoundException
     */
    void save(CommentDto dto) throws EntityNotFoundException;

    /**
     * Eliminar todos los registros de Comment.
     */
    void clear();

    void delete(Long id);
}

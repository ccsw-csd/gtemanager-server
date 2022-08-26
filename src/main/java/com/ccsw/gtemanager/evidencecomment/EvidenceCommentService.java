package com.ccsw.gtemanager.evidencecomment;

import java.util.List;

import com.ccsw.gtemanager.evidencecomment.model.EvidenceComment;

/**
 * EvidenceCommentService: servicio de datos de comentarios de evidencias.
 */
public interface EvidenceCommentService {

	/**
	 * Obtener un listado de todos los comentarios de evidencias.
	 * 
	 * @return Listado de EvidenceComments
	 */
	List<EvidenceComment> getAll();

	/**
	 * Eliminar todos los registros de EvidenceComment.
	 */
	void clear();

}

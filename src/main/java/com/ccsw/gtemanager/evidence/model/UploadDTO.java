package com.ccsw.gtemanager.evidence.model;

import org.springframework.web.multipart.MultipartFile;

/**
 * UploadDTO: DTO para datos de archivos subidos a través de backend. Contiene
 * atributo para almacenar fichero subido y booleano de borrar comentarios.
 * además de getters y setters.
 * 
 * @author cavire
 *
 */
public class UploadDTO {

	/** Archivo enviado por el usuario */
	private MultipartFile file;
	/** Casilla de eliminación de comentarios en las evidencias */
	private boolean deleteComments;

	/**
	 * Obtener archivo recibido.
	 * 
	 * @return Fichero (MultipartFile)
	 */
	public MultipartFile getFile() {
		return file;
	}

	/**
	 * Obtener estado de boolean deleteComments.
	 * 
	 * @return true si se ha elegido borrar comentarios, false si no se ha elegido
	 *         borrar comentarios
	 */
	public boolean isDeleteComments() {
		return deleteComments;
	}

	/**
	 * Almacenar archivo recibido.
	 * 
	 * @param file
	 */
	public void setFile(MultipartFile file) {
		this.file = file;
	}

	/**
	 * Almacenar estado de deleteComments.
	 * 
	 * @param deleteComments
	 */
	public void setDeleteComments(boolean deleteComments) {
		this.deleteComments = deleteComments;
	}

}

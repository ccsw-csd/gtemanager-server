package com.ccsw.gtemanager.evidence.model;

import org.springframework.web.multipart.MultipartFile;

/**
 * FormDataDto: DTO para datos de archivos subidos a través de backend. Contiene
 * atributo para almacenar fichero subido y booleano de borrar comentarios,
 * además de getters y setters.
 */
public class FormDataDto {

    private MultipartFile file;
    private Boolean deleteComments;

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
    public Boolean isDeleteComments() {
        return deleteComments;
    }

    /**
     * Almacenar archivo recibido.
     * 
     * @param file Archivo recibido a almacenar
     */
    public void setFile(MultipartFile file) {
        this.file = file;
    }

    /**
     * Almacenar estado de deleteComments.
     * 
     * @param deleteComments Variable de control de borrado de comentarios en
     *                       evidencias
     */
    public void setDeleteComments(Boolean deleteComments) {
        this.deleteComments = deleteComments;
    }

}

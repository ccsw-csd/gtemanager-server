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
    private Boolean deleteColors;

    /**
     * Obtener archivo recibido.
     * 
     * @return Fichero (MultipartFile)
     */
    public MultipartFile getFile() {
        return file;
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
     * Obtener estado de boolean deleteComments.
     * 
     * @return true si se ha elegido borrar comentarios, false si no se ha elegido borrar comentarios
     */
    public Boolean isDeleteComments() {
        return deleteComments;
    }

    /**
     * Almacenar estado de deleteComments.
     * 
     * @param deleteComments Variable de control de borrado de comentarios en evidencias
     */
    public void setDeleteComments(Boolean deleteComments) {
        this.deleteComments = deleteComments;
    }

    /**
     * Obtener estado de boolean deleteColors.
     *
     * @return true si se ha elegido borrar colores, false si no se ha elegido borrar colores
     */
    public Boolean isDeleteColors() {
        return deleteColors;
    }

    /**
     * Almacenar estado de deleteColors.
     *
     * @param deleteColors Variable de control de borrado de colores en evidencias
     */
    public void setDeleteColors(Boolean deleteColors) {
        this.deleteColors = deleteColors;
    }

}

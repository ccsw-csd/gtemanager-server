package com.ccsw.gtemanager.person;

import java.util.List;

import com.ccsw.gtemanager.person.model.Person;

public interface PersonService {

    List<Person> findAllPersonsFromFilters(String filter);

    /**
     * Obtener listado de todas las personas en la base de datos.
     * 
     * Se hace comparación con PersonSagaTranscode para reemplazar los códigos saga
     * desactualizados en el registro de personas.
     * 
     * @return Listado de Person
     */
    List<Person> getPeople();

    /**
     * Leer y deducir código saga de la persona implicada.
     * 
     * @param saga Código a procesar
     * @return Código truncado y validado en formato numérico, o en longitud de 4
     *         caracteres alfanuméricos
     * @throws IllegalArgumentException No se ha introducido un código admisible
     */
    String parseSaga(String saga) throws IllegalArgumentException;

}

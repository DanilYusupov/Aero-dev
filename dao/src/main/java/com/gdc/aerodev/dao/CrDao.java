package com.gdc.aerodev.dao;

import com.gdc.aerodev.model.Cr;

public interface CrDao extends GenericDao<Cr, Long> {
    /**
     * Checks inserted email on existence in database
     *
     * @param userEmail email to check
     * @return (0) {@param userName} of {@code User} with existent email <br>
     * (1) {@code null} if there is no such email
     */
    String existentEmail(String userEmail);
}

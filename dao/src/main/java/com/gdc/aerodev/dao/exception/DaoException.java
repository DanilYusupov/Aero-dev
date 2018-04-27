package com.gdc.aerodev.dao.exception;

/**
 * Internal exception for 'dao' module. Using this exception will be helpful during debugging.
 *
 * @author Yusupov Danil
 */
public class DaoException extends RuntimeException {
    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.gdc.aerodev.dao.logging;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This interface contains public static {@code Log} entity. <br>
 *     This is convenient way to use logging in api.
 */
public interface LoggingDao {
    Log log = LogFactory.getLog(LoggingDao.class);
}

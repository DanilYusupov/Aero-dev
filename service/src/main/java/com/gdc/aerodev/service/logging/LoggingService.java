package com.gdc.aerodev.service.logging;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Convenient interface for make logging
 */
public interface LoggingService {
    Log log = LogFactory.getLog(LoggingService.class);
}

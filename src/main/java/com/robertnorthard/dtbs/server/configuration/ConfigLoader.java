package com.robertnorthard.dtbs.server.configuration;

import java.util.Properties;

/**
 * This interface represents a configuration loader.
 * @author robertnorthard
 */
public interface ConfigLoader {

    /**
     * 
     * @param conf configuration implementation, e.g. file, url etc.
     * @return the associated properties object.
     * @throws RuntimeException if unable to load application configuration.
     */
    public Properties getConfig(String conf) throws RuntimeException;
}
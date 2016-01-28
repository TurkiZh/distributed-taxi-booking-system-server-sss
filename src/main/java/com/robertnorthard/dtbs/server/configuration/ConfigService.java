package com.robertnorthard.dtbs.server.configuration;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a configuration service.
 * @author robertnorthard
 */
public class ConfigService {

    private static final Logger LOGGER = Logger.getLogger(
            ConfigService.class.getName());
    
    /**
     * Represents configuration loaders
     */
    private static final ConfigLoaderStrategy[] LOADERS = new ConfigLoaderStrategy[] { 
        new ClassPathConfigLoaderStrategy() 
    };

    private ConfigService() {}

    public static Properties getConfig(String conf) {
        try {
            for (ConfigLoaderStrategy configLoader : LOADERS) {
                Properties properties = configLoader.getConfig(conf);

                if (properties != null)
                    return properties;
            }
        } catch (RuntimeException e) {
            LOGGER.log(Level.SEVERE, e.toString());
        }

        return null;
    }
}
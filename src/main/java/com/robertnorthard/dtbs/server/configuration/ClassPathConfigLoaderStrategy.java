package com.robertnorthard.dtbs.server.configuration;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class facilitates reading properties from files on the class path.
 * @author robertnorthard
 */
public class ClassPathConfigLoaderStrategy implements ConfigLoaderStrategy {

      private static final Logger LOGGER = Logger.getLogger(
            ClassPathConfigLoaderStrategy.class.getName());
    
    /**
     * Return properties from file on the class path.
     * @param file file to read properties from.
     */
    @Override
    public Properties getConfig(String file) {

        Properties properties = new Properties();

        try {
            try (Reader reader = new InputStreamReader(Thread.currentThread()
                    .getContextClassLoader().getResourceAsStream(file))) {
                properties.load(reader);
                return properties;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null,e);
            return null;
        }
    }
}
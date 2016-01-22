package com.robertnorthard.dtbs.server.configuration;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

/**
 * This class facilitates reading properties from files on the class path.
 * @author robertnorthard
 */
public class ClassPathConfigLoader implements ConfigLoader {

    /**
     * Return properties from file on the class path.
     * @param file file to read properties from.
     */
    @Override
    public Properties getConfig(String file) throws RuntimeException {

        Properties properties = new Properties();

        try {
            try (Reader reader = new InputStreamReader(Thread.currentThread()
                    .getContextClassLoader().getResourceAsStream(file))) {
                properties.load(reader);
                return properties;
            }
        } catch (Throwable e) {
            throw new RuntimeException(String.format(
                    "Unable to find properties file - [%s]", file));
        }
    }
}
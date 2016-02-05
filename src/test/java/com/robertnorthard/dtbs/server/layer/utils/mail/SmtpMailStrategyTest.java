package com.robertnorthard.dtbs.server.layer.utils.mail;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * SmtpMailStrategyTests unit tests.
 * @author robertnorthard
 */
public class SmtpMailStrategyTest {
    
    private final MailStrategy mailStrategy;
    
    public SmtpMailStrategyTest() throws IOException {
        Properties p = new Properties();
        p.load(new FileReader(
                new File("src/main/webapp/WEB-INF/classes/application.properties")));
        
        mailStrategy = new SmtpMailStrategy(p);
    }
    
    @Test
    public void isValidEmailTest(){
        assertFalse(this.mailStrategy.isValidEmail("example.com"));
        assertTrue(this.mailStrategy.isValidEmail("robertnorthard@googlemail.com"));
    }
}

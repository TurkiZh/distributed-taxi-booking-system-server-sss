package com.robertnorthard.dtbs.server.layer.utils.encryption;

import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.Properties;
import org.junit.Before;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author robertnorthard
 */
public class EncryptedPropertiesTest {
    
    private KeyStore keystore;
    private Key key;
    private EncryptedProperties properties;
    

    @Before
    public void setUp() throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException {
        
        this.keystore = SymmetricEncryptionUtil.loadKeyStore("src/test/resources/dtbs.store", "dtbsproject", "JCEKS");
        this.key = this.keystore.getKey("dtbssecret", "dtbsproject".toCharArray());
      
        Properties p = new Properties();
        p.put("encrypted.value", "E{uOh/abFZOkFTHEw8JJg+8Q==}");
        
        this.properties = new EncryptedProperties(key, p);
    }
    
    /**
     * Test decrypt for encrypted property.
     */
    @Test
    public void testDecrypt(){
        assertEquals("password", this.properties.getKey("encrypted.value"));
    }
}

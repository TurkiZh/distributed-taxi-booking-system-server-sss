package com.robertnorthard.dtbs.server.layer.utils.encryption;

import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for class SymmetricEncryptionUtil utility class.
 * 
 * @author robertnorthard
 */
public class SymmetricEncryptionUtilTest {

    private KeyStore keystore;
    private Key key;

    @Before
    public void setUp() throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException {
        this.keystore = SymmetricEncryptionUtil.loadKeyStore("src/test/resources/dtbs.store", "dtbsproject", "JCEKS");
        this.key = this.keystore.getKey("dtbssecret", "dtbsproject".toCharArray());
    }
    
    /**
     * Test of encrypt method, of class SymmetricEncryptionUtil.
     */
    @Test
    public void encryptValues() {

        System.out.println(SymmetricEncryptionUtil.encrypt("AIzaSyDPF994xud0CTSnZOuxwRTdpSFc_UQgVM0", "AES", key));
        System.out.println(SymmetricEncryptionUtil.encrypt("${~~:x3Nwb_ZE.<z", "AES", key));
    }
}

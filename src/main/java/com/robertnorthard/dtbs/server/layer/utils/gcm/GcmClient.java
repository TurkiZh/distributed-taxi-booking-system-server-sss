package com.robertnorthard.dtbs.server.layer.utils.gcm;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.robertnorthard.dtbs.server.configuration.ConfigService;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for sending messages via Google Cloud Messenger.
 * 
 * @author robertnorthard
 */
public class GcmClient {
    
    // Google cloud messenger API key
    private String gcmApiKey;
    
    /**
     * Default constructor for class GcmClient.
     * Reads Google Cloud Messenger API key from application.properties 
     * on the Java class path.
     */
    public GcmClient(){
        this.gcmApiKey = ConfigService
                .getConfig("application.properties")
                .getProperty("google.gcm.api.key");
    }
    
    /**
     * Constructor for GcmClient.
     * 
     * @param gcmApiKey Google Cloud Messenger API key.
     */
    public GcmClient(String gcmApiKey){
        this.gcmApiKey = gcmApiKey;
    }
    
    /**
     * Send messenger to a Google Cloud Messenger connected device.
     * 
     * @param message message to send.
     * @param gcmRegistrationId Google Cloud Messenger registration id of device. 
     */
    public void sendMessage(String message, String gcmRegistrationId){
        Sender sender = new Sender(this.gcmApiKey);
        Message outboundMessage = new Message.Builder()
                .addData("data", message)
                .build();
        try {
            Result result = sender.send(outboundMessage, gcmRegistrationId, 10);
        } catch (IOException ex) {
            Logger.getLogger(GcmClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Set Google Cloud Messenger API key.
     * 
     * @param gcmApiKey Google Cloud Messenger API key. 
     */
    public void setGcmApiKey(String gcmApiKey) {
        this.gcmApiKey = gcmApiKey;
    }
}

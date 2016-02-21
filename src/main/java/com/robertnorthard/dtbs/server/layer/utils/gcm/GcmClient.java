package com.robertnorthard.dtbs.server.layer.utils.gcm;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.robertnorthard.dtbs.server.configuration.ConfigService;
import com.robertnorthard.dtbs.server.layer.utils.datamapper.DataMapper;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for sending messages via Google Cloud Messenger.
 * 
 * @author robertnorthard
 */
public class GcmClient {
    
    private static final int GCM_SEND_RETRIES = 10;   
    
    // Google cloud messenger API key
    private String gcmApiKey;
   
    private DataMapper dataMapper;
    
    /**
     * Default constructor for class GcmClient.
     * Reads Google Cloud Messenger API key from application.properties 
     * on the Java class path.
     */
    public GcmClient(){
        this.gcmApiKey = ConfigService
                .getConfig("application.properties")
                .getProperty("google.gcm.api.key");
        
        this.dataMapper = DataMapper.getInstance();
    }
    
    /**
     * Constructor for GcmClient.
     * 
     * @param gcmApiKey Google Cloud Messenger API key.
     * @param datamapper data mapper for object to JSON conversion. 
     */
    public GcmClient(String gcmApiKey, DataMapper datamapper){
        this.gcmApiKey = gcmApiKey;
        this.dataMapper = datamapper;
    }
    
    /**
     * Send messenger to a Google Cloud Messenger connected device.
     * 
     * @param status status to send alongside message.
     * @param eventType notification type.
     * @param object object to encapsulate as JSON and send.
     * @param gcmRegistrationId Google Cloud Messenger registration id of device. 
     */
    public void sendMessage(String status, String eventType, Object object, String gcmRegistrationId){
        Sender sender = new Sender(this.gcmApiKey);
        Message outboundMessage = new Message.Builder()
                .addData("status", status)
                .addData("event", eventType)
                .addData("data", this.dataMapper.getObjectAsJson(object))
                .build();
        try {
            Result result = sender.send(outboundMessage, gcmRegistrationId, GcmClient.GCM_SEND_RETRIES);
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robertnorthard.dtbs.server.configuration;

import com.robertnorthard.dtbs.server.layer.utils.encryption.EncryptedProperties;
import java.util.Properties;

/**
 *
 * @author robertnorthard
 */
public class URLTokenParserPotentialToken extends URLTokenParserState {

    public URLTokenParserPotentialToken(URLTokenParser context) {
        super(context);
    }

    @Override
    public void addLeftBracket() {
        
    }

    @Override
    public void addRightBracket() {
    
        String value = this.getContext().getKey(this.getContext().getTemp());
        
        // look in default application properties file.
        if(value == null){
            Properties properties = ConfigService.getConfig("application.properties");
            
            value = properties.getProperty(this.getContext().getTemp());
            
            if(value == null){
                EncryptedProperties eProperties = new EncryptedProperties(properties);
                
                value = eProperties.getKey(this.getContext().getTemp());
                
                if(value == null){
                    throw new IllegalStateException("Property not found");
                }
                
            }
        }
        
        this.getContext().setToken(value);
        this.getContext().addToURL();
        this.getContext().changeState(new URLTokenParserNoBracesState(this.getContext()));
    }
}


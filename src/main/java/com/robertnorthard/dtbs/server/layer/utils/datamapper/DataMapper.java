package com.robertnorthard.dtbs.server.layer.utils.datamapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class represents a DataMapper singleton.
 * @author robertnorthard
 */
public class DataMapper extends ObjectMapper {
    
    private static DataMapper dataMapper;
    
    /**
     * Return an instance of data mapper. 
     * If it is null create a new instance.
     * @return a instance of data mapper.
     */
    public static DataMapper getInstance(){
        if(DataMapper.dataMapper == null){
            synchronized(DataMapper.class){
                DataMapper.dataMapper = new DataMapper();
            }
        }
        
        return DataMapper.dataMapper;
    }
    
    public String getObjectAsJson(Object obj){
        try {
            return this.writeValueAsString(obj);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(DataMapper.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}

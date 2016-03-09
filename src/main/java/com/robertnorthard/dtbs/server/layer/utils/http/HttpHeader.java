package com.robertnorthard.dtbs.server.layer.utils.http;

/**
 * Http header constants.
 *
 * @author robertnorthard
 */
public enum HttpHeader {

    AUTHORIZATION("Authorization");

    private String header;

    HttpHeader(String header) {
        this.header = header;
    }

    @Override
    public String toString() {
        return this.header;
    }

}

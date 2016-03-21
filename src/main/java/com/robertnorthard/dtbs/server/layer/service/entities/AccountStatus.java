package com.robertnorthard.dtbs.server.layer.service.entities;

/**
 * State of an account.
 *
 * @author robertnorthard
 */
public enum AccountStatus {

    ACTIVE("ACTIVE"), INACTIVE("INACTIVE");

    private String status;

    AccountStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return this.status;
    }
}

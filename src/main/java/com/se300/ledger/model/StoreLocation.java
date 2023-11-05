package com.se300.ledger.model;

/**
 * Store Location class implementation consisting of Store id and the Aisle id
 *
 * @author  Sergey L. Sundukovskiy
 * @version 1.0
 * @since   2023-10-11
 */
public class StoreLocation {

    private Long storeId;
    private Long aisleId;

    /**
     * Store Location Constructor
     *
     * @param storeId
     * @param aisleId
     */
    public StoreLocation(Long storeId, Long aisleId) {
        this.storeId = storeId;
        this.aisleId = aisleId;
    }

    /**
     * Getter method for Store Location Store id
     *
     * @return
     */
    public Long getStoreId() {
        return storeId;
    }

    /**
     * Setter method for Store Location Store id
     *
     * @param storeId
     */
    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    /**
     * Getter method for Store Location Aisle id
     *
     * @return
     */
    public Long getAisleId() {
        return aisleId;
    }

    /**
     * Setter method for Store Location Store id
     *
     * @param aisleId
     */
    public void setAisleId(Long aisleId) {
        this.aisleId = aisleId;
    }

    @Override
    public String toString() {
        return "StoreLocation{" +
                "storeId='" + storeId + '\'' +
                ", aisleId='" + aisleId + '\'' +
                '}';
    }
}

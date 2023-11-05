package com.se300.ledger.model;


/**
 * InventoryLocation class implementation representing locations of the items in the Store, Isle and the Shelf
 *
 * @author  Sergey L. Sundukovskiy
 * @version 1.0
 * @since   2023-10-11
 */
public class InventoryLocation extends StoreLocation {

    private Long shelfId;

    public InventoryLocation(Long storeId, Long aisleId, Long shelfId) {
        super(storeId, aisleId);
        this.shelfId = shelfId;
    }

    public Long getShelfId() {
        return shelfId;
    }

    public void setShelfId(Long shelfId) {
        this.shelfId = shelfId;
    }

    @Override
    public String toString() {
        return "InventoryLocation{" +
                "storeLocation='" + super.toString() + '\'' +
                ", shelfId='" + shelfId + '\'' +
                '}';
    }
}

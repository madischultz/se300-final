package com.se300.ledger.model;

/**
 * Inventory class implementation representing inventory on the shelf of the store
 *
 * @author  Sergey L. Sundukovskiy
 * @version 1.0
 * @since   2023-10-11
 */
public class Inventory {

    private Long id;
    private InventoryLocation inventoryLocation;
    private int capacity;
    private int count;
    private Long productId;

    /**
     * Constructor for the Inventory class
     *
     * @param id
     * @param inventoryLocation
     * @param capacity
     * @param count
     * @param productId
     */
    public Inventory(Long id, InventoryLocation inventoryLocation, int capacity, int count, Long productId) {
        this.id = id;
        this.inventoryLocation = inventoryLocation;
        this.capacity = capacity;
        this.count = count;
        this.productId = productId;
    }

    /**
     * Getter method for Inventory id
     *
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter method for Inventory id
     *
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter method for Inventory Location on the shelf of the store
     *
     * @return
     */
    public InventoryLocation getInventoryLocation() {
        return inventoryLocation;
    }

    /**
     * Setter method for Inventory Location on the shelf of the store
     *
     * @param inventoryLocation
     */
    public void setInventoryLocation(InventoryLocation inventoryLocation) {
        this.inventoryLocation = inventoryLocation;
    }

    /**
     * Getter method for Inventory capacity on the shelf of the store
     *
     * @return
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Setter method for Inventory capacity on the shelf of the store
     *
     * @param capacity
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Getter method for Inventory count on the shelf of the store
     *
     * @return
     */
    public int getCount() {
        return count;
    }

    /**
     * Setter method for Inventory count on the shelf of the store
     *
     * @param count
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * Getter method for the Product associated with the Inventory item
     *
     * @return
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * Setter method for the Product associated with the Inventory item
     *
     * @param productId
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * Method for updating (incrementing or decrementing) Inventory on the shelf of the store.
     * Throws StoreModelException if count does not stay within allowable bounds.
     * Method is synchronized to avoid a race condition
     *
     * @param count
     * @throws StoreModelException
     */
    synchronized public void updateInventory(int count) throws StoreModelException {

        //Check to see that count will remain within proper bounds
        if ((this.count + count < 0) || (this.count + count) > this.capacity)
            throw new StoreModelException("Update Inventory", "Inventory Count Is Smaller Than O " +
                    "or Larger Than Shelf Capacity");

        this.count += count;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "id='" + id + '\'' +
                ", inventoryLocation=" + inventoryLocation +
                ", capacity=" + capacity +
                ", count=" + count +
                ", productId='" + productId + '\'' +
                '}';
    }

}

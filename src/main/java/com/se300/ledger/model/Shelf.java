package com.se300.ledger.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Shelf class implementation representing shelf of the Aisle
 *
 * @author  Sergey L. Sundukovskiy
 * @version 1.0
 * @since   2023-10-11
 */
public class Shelf {

    private Long id;
    private String name;
    private ShelfLevel level;
    private String description;
    private Temperature temperature;
    private final Map<Long, Inventory> inventoryMap;

    /**
     * Constructor for the Shelf class
     *
     * @param id
     * @param name
     * @param level
     * @param description
     * @param temperature
     */
    public Shelf(Long id, String name, ShelfLevel level, String description, Temperature temperature) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.description = description;
        this.temperature = temperature;
        this.inventoryMap = new HashMap<>();
    }

    /**
     * Getter method for Shelf id
     *
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter method for Shelf id
     *
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter method for Shelf name
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for Shelf name
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method for Shelf level
     *
     * @return
     */
    public ShelfLevel getLevel() {
        return level;
    }

    /**
     * Setter method for Shelf level
     *
     * @param level
     */
    public void setLevel(ShelfLevel level) {
        this.level = level;
    }

    /**
     * Getter method for Shelf description
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter method for Shelf description
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter method for Shelf temperature
     *
     * @return
     */
    public Temperature getTemperature() {
        return temperature;
    }

    /**
     * Setter method for Shelf temperature
     *
     * @param temperature
     */
    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    /**
     * Method for adding Inventory to a particular Store Shelf. If Inventory already exist or
     * count is not within proper bound throw and StoreModelException
     * count
     *
     * @param inventoryId
     * @param storeId
     * @param aisleNumber
     * @param shelfId
     * @param capacity
     * @param count
     * @param productId
     * @return
     * @throws StoreModelException
     */
    public Inventory addInventory(Long inventoryId, Long storeId, Long aisleNumber,
                                  Long shelfId, int capacity, int count, Long productId) throws StoreModelException {

        //Check to see if count within proper bounds
        if (count < 0 || count > capacity)
            throw new StoreModelException("Add Inventory", "Inventory Is Smaller Than O " +
                    "or Larger Than Shelf Capacity");

        InventoryLocation location = new InventoryLocation(storeId, aisleNumber, shelfId);
        Inventory inventory = new Inventory(inventoryId, location, capacity, count, productId);

        //Make sure already does not exist in the store
        if (inventoryMap.putIfAbsent(inventoryId, inventory) != null)
            throw new StoreModelException("Add Inventory", "Inventory Already Exists");

        return inventory;
    }

    /**
     * Method for getting a Map of Inventory items that belong to the Store
     *
     * @return
     */
    public Map<Long, Inventory> getInventoryMap() {
        return this.inventoryMap;
    }

    @Override
    public String toString() {
        return "Shelf{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", level=" + level +
                ", description='" + description + '\'' +
                ", temperature=" + temperature +
                ", inventoryMap=" + inventoryMap +
                '}';
    }
}

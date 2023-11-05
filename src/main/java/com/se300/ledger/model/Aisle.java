package com.se300.ledger.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Aisle class implementation representing aisle in the Store
 *
 * @author  Sergey L. Sundukovskiy
 * @version 1.0
 * @since   2023-10-11
 */
public class Aisle {

    private Long number;
    private String name;
    private String description;
    private AisleLocation aisleLocation;
    private final Map<Long, Shelf> shelfMap;

    /**
     * Constructor for Aisle class
     *
     * @param number
     * @param name
     * @param description
     * @param aisleLocation
     */
    public Aisle(Long number, String name, String description, AisleLocation aisleLocation) {
        this.number = number;
        this.name = name;
        this.description = description;
        this.aisleLocation = aisleLocation;
        this.shelfMap = new HashMap<>();
    }

    /**
     * Getter Method for Aisle number
     *
     * @return
     */
    public Long getNumber() {
        return number;
    }

    /**
     * Setter Method for Aisle number
     *
     * @param number
     */
    public void setNumber(Long number) {
        this.number = number;
    }

    /**
     * Getter Method for Aisle name
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Setter Method for Aisle name
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter Method for Aisle description
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter Method for Aisle description
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter Method for AisleLocation
     *
     * @return
     */
    public AisleLocation getAisleLocation() {
        return aisleLocation;
    }

    /**
     * Setter Method for AisleLocation
     *
     * @param aisleLocation
     */
    public void setAisleLocation(AisleLocation aisleLocation) {
        this.aisleLocation = aisleLocation;
    }

    /**
     * Method for adding Shelf to the Aisle. If Shelf already exists in the Aisle throws StoreModelException
     *
     * @param id
     * @param name
     * @param level
     * @param description
     * @param temperature
     * @return
     * @throws StoreModelException
     */
    public Shelf addShelf(Long id, String name, ShelfLevel level, String description,
                          Temperature temperature) throws StoreModelException {
        Shelf shelf = new Shelf(id, name, level, description, temperature);

        //Check to see if Shelf already exists at the given level
        List<Shelf> shelfList = this.shelfMap.values()
                .stream()
                .filter(tempShelf -> level.equals(tempShelf.getLevel()))
                .collect(Collectors.toList());

        if (!shelfList.isEmpty()) {
            throw new StoreModelException("Add Shelf", "Shelf Already Exists at This Level");
        }

        //If Shelf already exists throw and exception
        if (this.shelfMap.putIfAbsent(id, shelf) != null)
            throw new StoreModelException("Add Shelf", "Shelf Already Exists");

        return shelf;
    }

    /**
     * Get Shelf from the Aisle. If Aisle does not exist throw StoreModelException
     *
     * @param shelfId
     * @return
     */
    public Shelf getShelf(Long shelfId) {
        return this.shelfMap.get(shelfId);
    }

    /**
     * Method for returning a map of all the Shelves in the Aisle
     *
     * @return
     */
    public Map<Long, Shelf> getShelfMap() {
        return this.shelfMap;
    }

    @Override
    public String toString() {
        return "Aisle{" +
                "number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", aisleLocation=" + aisleLocation +
                ", shelfMap=" + shelfMap +
                '}';
    }
}
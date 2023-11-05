package com.se300.ledger.model;


import jakarta.persistence.*;


import java.util.*;

/**
 * Store class implementation representing store of the Store Model Service
 *
 * @author  Sergey L. Sundukovskiy
 * @version 1.0
 * @since   2023-10-11
 */
@Entity
public class Store {

    @Id
    private Long id;
    private String address;
    private String description;
    @Transient
    private final Map<Long, Aisle> aislesMap;
    @Transient
    private final Map<Long, Device> deviceMap;
    @Transient
    private final Map<Long, Customer> customerMap;
    @Transient
    private final Map<Long, Inventory> inventoryMap;
    @Transient
    private final Map<Long, Basket> basketMap;

    /**
     * Constructor for the Store class
     *
     * @param id
     * @param address
     * @param description
     */
    public Store(Long id, String address, String description) {
        this.id = id;
        this.address = address;
        this.description = description;
        this.aislesMap = new HashMap<>();
        this.deviceMap = new HashMap<>();
        this.customerMap = new HashMap<>();
        this.inventoryMap = new HashMap<>();
        this.basketMap = new HashMap<>();
    }

    public Store() {
        this.aislesMap = new HashMap<>();
        this.deviceMap = new HashMap<>();
        this.customerMap = new HashMap<>();
        this.inventoryMap = new HashMap<>();
        this.basketMap = new HashMap<>();
    }

    /**
     * Getter method for Store id
     *
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter method for Store id
     *
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter method for Store address
     *
     * @return
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter method for the Store address
     *
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Getter method for the Store description
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter method for the Store description
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Add aisle to the Store. If Aisle already exists in the store throw StoreModelException
     *
     * @param aisleNumber
     * @param name
     * @param description
     * @param aisleLocation
     * @return
     * @throws StoreModelException
     */
    public Aisle addAisle(Long aisleNumber, String name, String description, AisleLocation aisleLocation) throws StoreModelException {

        //Check to see if Aisle already exists
        Aisle aisle = new Aisle(aisleNumber, name, description, aisleLocation);
        if (this.aislesMap.putIfAbsent(aisleNumber, aisle) != null)
            throw new StoreModelException("Add Aisle", "Aisle Already Exists");

        return aisle;
    }

    /**
     * Get Aisle from the Store. If Aisle does not exist throw StoreModelException
     *
     * @param aisleNumber
     * @return
     * @throws StoreModelException
     */
    public Aisle getAisle(Long aisleNumber) throws StoreModelException {

        Aisle aisle = this.aislesMap.get(aisleNumber);
        //Check to see if Aisle exists in the Store
        if (aisle == null) {
            throw new StoreModelException("Get Aisle", "Aisle Does Not Exist");
        }

        return this.aislesMap.get(aisleNumber);
    }

    /**
     * Method for keeping local reference of the Inventory in the Store.
     * If Inventory already exists in the Store throw StoreModelException
     *
     * @param inventory
     */
    public void addInventory(Inventory inventory) throws StoreModelException {

        //Check to see if Inventory already exists in the Store
        if (this.inventoryMap.putIfAbsent(inventory.getId(), inventory) != null)
            throw new StoreModelException("Add Inventory", "Inventory Already Exists");

    }

    /**
     * Method for keeping local reference of the Customer in the Store.
     * If Customer already exists in the Store throw StoreModelException
     *
     * @param customer
     * @throws StoreModelException
     */
    public void addCustomer(Customer customer) throws StoreModelException {

        //Check to see if Customer already exists in the Store
        if (this.customerMap.putIfAbsent(customer.getId(), customer) != null)
            throw new StoreModelException("Add Customer", "Customer Already Exists");
    }

    /**
     * Method for keeping local reference of the Device in the Store.
     * If Device already exists in the Store throw StoreModelException
     *
     * @param device
     * @throws StoreModelException
     */
    public void addDevice(Device device) throws StoreModelException {

        //Check to see if Device already exists in the Store
        if (this.deviceMap.putIfAbsent(device.getId(), device) != null)
            throw new StoreModelException("Add Device", "Device Already Exists");

    }

    /**
     * Method for keeping local reference of the Basket in the Store.
     * If Basket already exists in the Store throw StoreModelException
     *
     * @param basket
     * @throws StoreModelException
     */
    public void addBasket(Basket basket) throws StoreModelException {
        //Check to see if basket already exists in the Store
        if (this.basketMap.putIfAbsent(basket.getId(), basket) != null)
            throw new StoreModelException("Add Device", "Device Already Exists");

    }

    /**
     * Method getting Customer by Id
     *
     * @param customerId
     * @return
     */
    public Customer getCustomer(Long customerId) {
        return this.customerMap.get(customerId);
    }

    /**
     * Method for removing Customer from the Store
     *
     * @param customer
     */
    public void removeCustomer(Customer customer) {

        if (this.customerMap.get(customer.getId()) == null) {
            this.customerMap.remove(customer.getId());
        }
    }

    @Override
    public String toString() {
        return "Store{" +
                "id='" + id + '\'' +
                ", address='" + address + '\'' +
                ", description='" + description + '\'' +
                ", aislesMap=" + aislesMap +
                ", deviceMap=" + deviceMap +
                ", customerMap=" + customerMap +
                ", inventoryMap=" + inventoryMap +
                ", basketMap=" + basketMap +
                '}';
    }
}

package com.se300.ledger.service;
import com.se300.ledger.model.*;

/**
 * StoreModelAPI interface representing StoreModel API for external entities such as StoreModelController
 *
 * @author  Sergey L. Sundukovskiy
 * @version 1.0
 * @since   2023-10-11
 */
public interface StoreModelAPI {

    /**
     * Method for Store provisioning
     *
     * @param storeId
     * @param address
     * @param description
     * @param token
     * @return
     * @throws StoreModelException
     */
    public Store provisionStore(Long storeId, String address, String description, String token) throws StoreModelException;

    /**
     * Method for display Store information
     *
     * @param storeId
     * @param token
     * @return
     * @throws StoreModelException
     */
    public Store showStore(Long storeId, String token) throws StoreModelException;

    /**
     * Method for provisioning Aisle in the Store
     *
     * @param storeId
     * @param aisleNumber
     * @param name
     * @param description
     * @param location
     * @param token
     * @return
     * @throws StoreModelException
     */
    public Aisle provisionAisle(Long storeId, Long aisleNumber, String name,
                                String description, AisleLocation location, String token) throws StoreModelException;

    /**
     * Method for displaying Aisle information
     *
     * @param storeId
     * @param aisleNumber
     * @param token
     * @return
     * @throws StoreModelException
     */
    public Aisle showAisle(Long storeId, Long aisleNumber, String token) throws StoreModelException;

    /**
     * Method for provisioning Shelf in the Aisle
     *
     * @param storeId
     * @param aisleNumber
     * @param shelfId
     * @param name
     * @param level
     * @param description
     * @param temperature
     * @param token
     * @return
     * @throws StoreModelException
     */
    public Shelf provisionShelf(Long storeId, Long aisleNumber, Long shelfId,
                                String name, ShelfLevel level, String description,
                                Temperature temperature, String token) throws StoreModelException;

    /**
     * Method for displaying Shelf information
     *
     * @param storeId
     * @param aisleNumber
     * @param shelfId
     * @param token
     * @return
     * @throws StoreModelException
     */
    public Shelf showShelf(Long storeId, Long aisleNumber, Long shelfId, String token) throws StoreModelException;

    /**
     * Method for provisioning Inventory on the Shelf
     *
     * @param inventoryId
     * @param storeId
     * @param aisleNumber
     * @param shelfId
     * @param capacity
     * @param count
     * @param productId
     * @param token
     * @return
     * @throws StoreModelException
     */
    public Inventory provisionInventory(Long inventoryId, Long storeId, Long aisleNumber,
                                        Long shelfId, int capacity, int count, Long productId,
                                        String token) throws StoreModelException;

    /**
     * Method for displaying Inventory information
     *
     * @param inventoryId
     * @param token
     * @return
     * @throws StoreModelException
     */
    public Inventory showInventory(Long inventoryId, String token) throws StoreModelException;

    /**
     * Method for updating Inventory information
     *
     * @param inventoryId
     * @param count
     * @param token
     * @return
     * @throws StoreModelException
     */
    public Inventory updateInventory(Long inventoryId, int count, String token) throws StoreModelException;

    /**
     * Method for provisioning Product
     *
     * @param productId
     * @param name
     * @param description
     * @param size
     * @param category
     * @param price
     * @param temperature
     * @param token
     * @return
     * @throws StoreModelException
     */
    public Product provisionProduct(Long productId, String name, String description, String size,
                                    String category, double price, Temperature temperature,
                                    String token) throws StoreModelException;

    /**
     * Method for displaying Product information
     *
     * @param productId
     * @param token
     * @return
     * @throws StoreModelException
     */
    public Product showProduct(Long productId, String token) throws StoreModelException;

    /**
     * Method for provisioning a Customer
     *
     * @param customerId
     * @param firstName
     * @param lastName
     * @param type
     * @param email
     * @param address
     * @param token
     * @return
     * @throws StoreModelException
     */
    public Customer provisionCustomer(Long customerId, String firstName, String lastName,
                                      CustomerType type, String email, String address, String token) throws StoreModelException;

    /**
     * Method for updating Customer information
     *
     * @param customerId
     * @param storeId
     * @param aisleNumber
     * @param token
     * @return
     * @throws StoreModelException
     */
    public Customer updateCustomer(Long customerId, Long storeId, Long aisleNumber, String token)
            throws StoreModelException;

    /**
     * Method for displaying Customer information
     *
     * @param customerId
     * @param token
     * @return
     * @throws StoreModelException
     */
    public Customer showCustomer(Long customerId, String token) throws StoreModelException;

    /**
     * Method for provisioning Basket
     *
     * @param basketId
     * @param token
     * @return
     * @throws StoreModelException
     */
    public Basket provisionBasket(Long basketId, String token) throws StoreModelException;

    /**
     * Method for assigning Basket to the Customer
     *
     * @param customerId
     * @param basketId
     * @param token
     * @return
     * @throws StoreModelException
     */
    public Basket assignCustomerBasket(Long customerId, Long basketId, String token) throws StoreModelException;

    /**
     * Method for displaying Customer Basket information
     *
     * @param customerId
     * @param token
     * @return
     * @throws StoreModelException
     */
    public Basket getCustomerBasket(Long customerId, String token) throws StoreModelException;

    /**
     * Method for adding Product to the Customer Basket
     *
     * @param basketId
     * @param productId
     * @param count
     * @param token
     * @return
     * @throws StoreModelException
     */
    public Basket addBasketProduct(Long basketId, Long productId, int count, String token)
            throws StoreModelException;

    /**
     * Method for removing Product from Customer Basket
     *
     * @param basketId
     * @param productId
     * @param count
     * @param token
     * @return
     * @throws StoreModelException
     */
    public Basket removeBasketProduct(Long basketId, Long productId, int count, String token) throws
            StoreModelException;

    /**
     * Method for removing all Products from the Customer Basket
     *
     * @param basketId
     * @param token
     * @return
     * @throws StoreModelException
     */
    public Basket clearBasket(Long basketId, String token) throws StoreModelException;

    /**
     * Method for displaying Customer Basket information
     *
     * @param basketId
     * @param token
     * @return
     * @throws StoreModelException
     */
    public Basket showBasket(Long basketId, String token) throws StoreModelException;

    /**
     * Method for provisioning Device in the Store
     *
     * @param deviceId
     * @param name
     * @param deviceType
     * @param storeId
     * @param aisleNumber
     * @param token
     * @return
     * @throws StoreModelException
     */
    public Device provisionDevice(Long deviceId, String name, String deviceType,
                                  Long storeId, Long aisleNumber, String token) throws StoreModelException;

    /**
     * Method for displaying Store Device information
     *
     * @param deviceId
     * @param token
     * @return
     * @throws StoreModelException
     */
    public Device showDevice(Long deviceId, String token) throws StoreModelException;

    /**
     * Method for triggering Store Device event
     *
     * @param deviceId
     * @param event
     * @param token
     * @throws StoreModelException
     */
    public void raiseEvent(Long deviceId, String event, String token) throws StoreModelException;

    /**
     * Method for issuing Store Appliance command
     *
     * @param deviceId
     * @param command
     * @param token
     * @throws StoreModelException
     */
    public void issueCommand(Long deviceId, String command, String token) throws StoreModelException;
}

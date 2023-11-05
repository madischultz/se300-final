package com.se300.ledger.service;

import com.se300.ledger.repository.BasketRepository;
import com.se300.ledger.repository.CustomerRepository;
import com.se300.ledger.repository.StoreRepository;
import com.se300.ledger.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * This is the main service of the system implementing Command API for processing CLI commands and
 * ModelService API for processing Store events
 *
 * @author  Sergey L. Sundukovskiy
 * @version 1.0
 * @since   2023-10-11
 */
@Service
public class StoreModelService implements StoreModelAPI {

    @Autowired
    StoreRepository storeRepository;
    @Autowired
    BasketRepository basketRepository;
    @Autowired
    CustomerRepository customerRepository;

    private static final Map<Long, Store> storeMap;
    private static final Map<Long, Customer> customerMap;
    private static final Map<Long, Product> productMap;
    private static final Map<Long, Inventory> inventoryMap;
    private static final Map<Long, Basket> basketMap;
    private static final Map<Long, Device> deviceMap;

    private static StoreModelService storeModelService;

    // Initialize genesis block and the account list
    static {
        storeMap = new HashMap<>();
        customerMap = new HashMap<>();
        productMap = new HashMap<>();
        inventoryMap = new HashMap<>();
        basketMap = new HashMap<>();
        deviceMap = new HashMap<>();
    }

    /**
     * Singleton Pattern implementation
     *
     * @return
     */
    public static synchronized StoreModelService getInstance() {
        if (storeModelService == null) {
            storeModelService = new StoreModelService();
        }
        return storeModelService;
    }

    @Override
    public Store provisionStore(Long storeId, String address, String description, String token)
            throws StoreModelException {

        //Store store = new Store(storeId, name, address);
        Store store = storeRepository.save(new Store(storeId,address, description));

        //If Store already exists throw and exception
        if (storeMap.putIfAbsent(storeId, store) != null) {
            throw new StoreModelException("Provision Store", "Store Already Exists");
        }
        return store;
    }

    @Override
    public Store showStore(Long storeId, String token) throws StoreModelException {

        //If Store does not exist throw and exception
        Store store = storeMap.get(storeId);
        if (store == null)
            throw new StoreModelException("Show Store", "Store Does Not Exist");

        return store;
    }

    @Override
    public Aisle provisionAisle(Long storeId, Long aisleNumber, String name, String description,
                                AisleLocation location, String token) throws StoreModelException {

        Store store = storeMap.get(storeId);
        Aisle aisle;

        //Check to see if Store already exists;
        if (store == null) {
            throw new StoreModelException("Provision Aisle", "Store Does Not Exist");
        } else {
            aisle = store.addAisle(aisleNumber, name, description, location);
        }

        return aisle;
    }

    @Override
    public Aisle showAisle(Long storeId, Long aisleNumber, String token) throws StoreModelException {
        Store store = storeMap.get(storeId);
        Aisle aisle;
        //Check to see if Store exists
        if (store == null) {
            throw new StoreModelException("Show Aisle", "Store Does Not Exist");
        } else {
            //Check to see if Aisle already exists
            aisle = store.getAisle(aisleNumber);
            if (aisle == null) {
                throw new StoreModelException("Show Aisle", "Aisle Does Not Exist");
            }
        }
        return aisle;
    }

    @Override
    public Shelf provisionShelf(Long storeId, Long aisleNumber, Long shelfId, String name,
                                ShelfLevel level, String description, Temperature temperature, String token) throws StoreModelException {

        Store store = storeMap.get(storeId);
        Shelf shelf;

        //Check to see if Store exists
        if (store == null) {
            throw new StoreModelException("Provision Shelf", "Store Does Not Exist");
        } else {
            Aisle aisle = store.getAisle(aisleNumber);
            //Check to see if Aisle exists
            if (aisle == null) {
                throw new StoreModelException("Provision Shelf", "Aisle Does Not Exist");
            } else {
                shelf = aisle.getShelf(shelfId);
                //Check to see if Shelf exists
                if (shelf != null) {
                    throw new StoreModelException("Provision Shelf", "Shelf Already Exists");
                }

                //Add Shelf to the Aisle
                shelf = aisle.addShelf(shelfId, name, level, description, temperature);
            }
        }
        return shelf;
    }

    @Override
    public Shelf showShelf(Long storeId, Long aisleNumber, Long shelfId, String token) throws StoreModelException {
        Store store = storeMap.get(storeId);
        Shelf shelf;

        //Check to see if Store exists
        if (store == null) {
            throw new StoreModelException("Show Shelf", "Store Does Not Exist");
        } else {
            //Check to see if Aisle exists
            Aisle aisle = store.getAisle(aisleNumber);
            if (aisle == null) {
                throw new StoreModelException("Show Shelf", "Aisle Does Not Exist");
            } else {
                //Check to see if Shelf exists
                shelf = aisle.getShelf(shelfId);
                if (shelf == null) {
                    throw new StoreModelException("Show Shelf", "Shelf Does Not Exist");
                }
            }
        }
        return shelf;
    }

    @Override
    public Inventory provisionInventory(Long inventoryId, Long storeId, Long aisleNumber, Long shelfId,
                                        int capacity, int count, Long productId, String token) throws StoreModelException {

        Store store = storeMap.get(storeId);
        Product product = productMap.get(productId);
        Inventory inventory;

        //Check to see if Store exists
        if (store == null) {
            throw new StoreModelException("Provision Inventory", "Store Does Not Exist");
        } else {
            //Check to see if Aisle exists
            Aisle aisle = store.getAisle(aisleNumber);
            if (aisle == null) {
                throw new StoreModelException("Provision Inventory", "Aisle Does Not Exist");
            } else {
                //Check to see if Shelf exists
                Shelf shelf = aisle.getShelf(shelfId);
                if (shelf == null) {
                    throw new StoreModelException("Provision Inventory", "Shelf Does Not Exist");
                } else if (product == null) {
                    //Check to see if Product exists
                    throw new StoreModelException("Provision Inventory", "Product Does Not Exist");
                } else if (!shelf.getTemperature().equals(product.getTemperature())) {
                    //Make sure that Product Temperature and Shelf Temperature are consistent
                    throw new StoreModelException("Provision Inventory", "Product and Shelf Temperature " +
                            "Is Not Consistent");
                }

                //Add Inventory to the Shelf
                inventory = shelf.addInventory(inventoryId, storeId, aisleNumber, shelfId,
                        capacity, count, productId);

                //Add Inventory to the global Inventory Map
                inventoryMap.put(inventoryId, inventory);

                //Add Inventory to the Store
                store.addInventory(inventory);

            }
        }

        return inventory;
    }

    @Override
    public Inventory showInventory(Long inventoryId, String token) throws StoreModelException {

        Inventory inventory = inventoryMap.get(inventoryId);
        //Check to see if Inventory exists
        if (inventory == null)
            throw new StoreModelException("Show Inventory", "Inventory Does Not Exist");
        return inventory;
    }

    @Override
    public Inventory updateInventory(Long inventoryId, int count, String token) throws StoreModelException {
        Inventory inventory = inventoryMap.get(inventoryId);
        //Check to see if Inventory exists
        if (inventory == null)
            throw new StoreModelException("Update Inventory", "Inventory Does Not Exist");

        //Update Inventory count
        inventory.updateInventory(count);

        return inventory;
    }

    @Override
    public Product provisionProduct(Long productId, String name, String description, String size, String category,
                                    double price, Temperature temperature, String token) throws StoreModelException {
        Product product = new Product(productId, name, description, size, category, price, temperature);

        //Check to see if Product already exists
        if (productMap.putIfAbsent(productId, product) != null)
            throw new StoreModelException("Provision Product", "Product Already Exists");

        return product;
    }

    @Override
    public Product showProduct(Long productId, String token) throws StoreModelException {
        Product product = productMap.get(productId);
        //Check to see if Product exists
        if (product == null)
            throw new StoreModelException("Show Product", "Product Does Not Exist");
        return product;
    }

    @Override
    public Customer provisionCustomer(Long customerId, String firstName, String lastName,
                                      CustomerType type, String email, String address, String token)
            throws StoreModelException {

        //Customer customer = new Customer(customerId, firstName, lastName, type, email, address);
        Customer customer = customerRepository.save(new Customer(customerId, firstName, lastName,
                            type, email, address));


        //Check to see if the Customer already exists
        if (customerMap.putIfAbsent(customerId, customer) != null)
            throw new StoreModelException("Provision Customer", "Customer Already Exists");

        return customer;
    }

    @Override
    public Customer updateCustomer(Long customerId, Long storeId, Long aisleNumber, String token)
            throws StoreModelException {
        Store store = storeMap.get(storeId);
        Customer customer;

        //Check to see if the Store exists
        if (store == null) {
            throw new StoreModelException("Update Customer", "Store Does Not Exist");
        } else {
            //Check to see if Aisle exists
            Aisle aisle = store.getAisle(aisleNumber);
            if (aisle == null) {
                throw new StoreModelException("Update Customer", "Aisle Does Not Exist");
            } else {
                //Check to see if Customer exists
                customer = customerMap.get(customerId);
                if (customer == null) {
                    throw new StoreModelException("Update Customer", "Customer Does Not Exist");
                }
            }
        }

        //Check to see if Customer changing Stores
        if (customer.getStoreLocation() != null && !customer.getStoreLocation().getStoreId().equals(storeId)) {
            //Check to see if Customer already exists in other Stores
            Map<Store, Customer> customerStores = storeMap.entrySet()
                    .stream()
                    .filter(tempStore -> (tempStore.getValue().getCustomer(customerId) != null && tempStore.getValue().getCustomer(customerId).getId().equals(customerId)))
                    .collect(Collectors.toMap(Map.Entry::getValue, tempStore -> tempStore.getValue().
                            getCustomer(customerId)));

            //If Customer exist in other stores remove him/her
            customerStores.forEach((key, value) -> key.removeCustomer(customer));

            //Before Customer can change the Store he/she must clear the Basket
            if (customer.getBasket() != null)
                customer.getBasket().clearBasket();

            //If the Customer moves to a different Store clear out the basket and the time seen
            customer.assignBasket(null);
            customer.setLastSeen(null);

            //Add Customer to another store
            store.addCustomer(customer);
        } else {

            customer.setStoreLocation(new StoreLocation(storeId, aisleNumber));
            customer.setLastSeen(new Date(System.currentTimeMillis()));
        }

        return customer;
    }

    @Override
    public Customer showCustomer(Long customerId, String token) throws StoreModelException {

        //Check to see if the Customer exists
        Customer customer = customerMap.get(customerId);
        if (customer == null)
            throw new StoreModelException("Show Customer", "Customer Does Not Exist");

        return customer;
    }

    @Override
    public Basket provisionBasket(Long basketId, String token) throws StoreModelException {

        //Basket basket = new Basket(basketId);
        Basket basket = basketRepository.save(new Basket(basketId));

        //Check if Basket already exists
        if (basketMap.putIfAbsent(basketId, basket) != null)
            throw new StoreModelException("Provision Basket", "Basket Already Exists");

        return basket;
    }

    @Override
    public Basket assignCustomerBasket(Long customerId, Long basketId, String token) throws StoreModelException {

        Customer customer = customerMap.get(customerId);
        Basket basket = basketMap.get(basketId);

        //Check to see Customer and the Basket already exist
        if (customer == null) {
            throw new StoreModelException("Assign Customer Basket", "Customer Does Not Exist");
        } else {
            if (basket == null) {
                throw new StoreModelException("Assign Customer Basket", "Basket Does Not Exist");
            }
        }

        //Assign Basket to the Customer
        customer.assignBasket(basket);
        //Keep the global copy of all the baskets
        basketMap.put(basketId, basket);

        Store store = storeMap.get(customerMap.get(customerId).getId());

        //Associate basket with the customer
        basket.setCustomer(customer);
        //Create bidirectional association between Store and the Basket
        basket.setStore(store);
        store.addBasket(basket);

        Customer persistentCustomer = this.customerRepository.findById(customerId).get();
        Basket persistentBasket = this.basketRepository.findById(basketId).get();
        List<Store> stores = StreamSupport.stream(this.storeRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());

        Store persistentStore = stores.get(ThreadLocalRandom.current().nextInt(0, stores.size()));

        persistentBasket.setCustomer(persistentCustomer);
        persistentBasket.setStore(persistentStore);
        persistentStore.addBasket(persistentBasket);
        persistentCustomer.assignBasket(persistentBasket);

        this.customerRepository.save(persistentCustomer);
        this.basketRepository.save(persistentBasket);

        return basket;
    }

    @Override
    public Basket getCustomerBasket(Long customerId, String token) throws StoreModelException {
        Customer customer = customerMap.get(customerId);
        Basket basket;

        //Check if Customer exists
        if (customer == null) {
            throw new StoreModelException("Get Customer Basket", "Customer Does Not Exist");
        } else {
            basket = customer.getBasket();
            //Check to see if Customer has been assigned the Basket
            if (basket == null) {
                throw new StoreModelException("Get Customer Basket", "Customer Does Not Have a Basket");
            }
        }
        return basket;
    }

    @Override
    public Basket addBasketProduct(Long basketId, Long productId, int count, String token)
            throws StoreModelException {
        Basket basket = basketMap.get(basketId);
        Product product = productMap.get(productId);

        //Check to see if basket already exists product we are trying to add to the basket
        //exists as well and basket has been assigned to the customer
        if (basket == null) {
            throw new StoreModelException("Add Basket Product", "Basket Does Not Exist");
        } else if (product == null) {
            throw new StoreModelException("Add Basket Product", "Product Does Not Exist");
        } else if (basket.getCustomer() == null) {
            throw new StoreModelException("Add Basket Product", "Basket Has Not Being Assigned");
        }
        //Add a product to the basket
        basket.addProduct(productId, count);

        return basket;
    }

    @Override
    public Basket removeBasketProduct(Long basketId, Long productId, int count, String token) throws StoreModelException {
        Basket basket = basketMap.get(basketId);
        Product product = productMap.get(productId);

        //Check to see if basket already exists product we are trying to add to the basket
        //exists as well and basket has been assigned to the customer
        if (basket == null) {
            throw new StoreModelException("Remove Basket Product", "Basket Does Not Exist");
        } else if (product == null) {
            throw new StoreModelException("Remove Basket Product", "Product Does Not Exist");
        } else if (basket.getCustomer() == null) {
            throw new StoreModelException("Remove Basket Product", "Basket Has Not Being Assigned");
        }
        //Remove product from the basket
        basket.removeProduct(productId, count);

        return basket;
    }

    @Override
    public Basket clearBasket(Long basketId, String token) throws StoreModelException {

        Basket basket = basketMap.get(basketId);

        //Check to see if basket already exists and basket has been assigned to the customer
        if (basket == null) {
            throw new StoreModelException("Clear Basket", "Basket Does Not Exist");
        } else if (basket.getCustomer() == null) {
            throw new StoreModelException("Clear Basket", "Basket Has Not Being Assigned");
        }
        basket.clearBasket();

        return basket;
    }

    @Override
    public Basket showBasket(Long basketId, String token) throws StoreModelException {
        Basket basket = basketMap.get(basketId);

        //Check to see if basket already exists and basket has been assigned to the customer
        if (basket == null) {
            throw new StoreModelException("Show Basket Product", "Basket Does Not Exist");
        } else if (basket.getCustomer() == null) {
            throw new StoreModelException("Show Basket Product", "Basket Has Not Being Assigned");
        }

        return basket;
    }

    @Override
    public Device provisionDevice(Long deviceId, String name, String deviceType, Long storeId,
                                  Long aisleNumber, String token) throws StoreModelException {

        Store store = storeMap.get(storeId);
        Device device;
        StoreLocation storeLocation;

        //Check to see if store exists
        if (store == null) {
            throw new StoreModelException("Provision Device", "Store Does Not Exist");
        } else {

            //Check to see if aisle exists
            Aisle aisle = store.getAisle(aisleNumber);
            if (aisle == null) {
                throw new StoreModelException("Provision Device", "Aisle Does Not Exist");
            } else {
                storeLocation = new StoreLocation(storeId, aisleNumber);

                //Check to see if device already exists
                device = deviceMap.get(deviceId);
                if (device != null) {
                    throw new StoreModelException("Provision Device", "Device Already Exists");
                }

                //Determine wha type of device we are trying to add
                for (SensorType sensor : SensorType.values()) {
                    if (sensor.name().equals(deviceType)) {
                        device = new Sensor(deviceId, name, storeLocation, deviceType);
                    }
                }
                for (ApplianceType appliance : ApplianceType.values()) {
                    if (appliance.name().equals(deviceType)) {
                        device = new Appliance(deviceId, name, storeLocation, deviceType);
                    }
                }

                //Add device to the global map
                deviceMap.put(deviceId, device);
                //Add device to the local store
                store.addDevice(device);

            }
        }
        return device;
    }

    @Override
    public Device showDevice(Long deviceId, String token) throws StoreModelException {
        Device device = deviceMap.get(deviceId);

        //Check to see if device exists
        if (device == null)
            throw new StoreModelException("Show Device", "Device Does Not Exist");

        return device;
    }

    @Override
    public void raiseEvent(Long deviceId, String event, String token) throws StoreModelException {
        Device device = deviceMap.get(deviceId);

        //Check to see if device exists
        if (device == null) {
            throw new StoreModelException("Raise Event", "Device Does Not Exist");
        }
        device.processEvent(event);

    }

    @Override
    public void issueCommand(Long deviceId, String command, String token) throws StoreModelException {

        Appliance appliance = (Appliance) deviceMap.get(deviceId);

        //Check to see if appliance exists
        if (appliance == null) {
            throw new StoreModelException("Issue Command", "Device Does Not Exist");
        }
        appliance.processCommand(command);
    }
}

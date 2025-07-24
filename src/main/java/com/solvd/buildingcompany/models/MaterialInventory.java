package com.solvd.buildingcompany.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class MaterialInventory<T extends BuildingMaterial> {
    private static final Logger LOGGER = LogManager.getLogger(MaterialInventory.class);

    private Map<String, T> materialCatalog;
    private Set<String> suppliers;
    private Queue<T> pendingOrders;
    private List<T> stockItems;

    public MaterialInventory() {
        // Using different collection types
        this.materialCatalog = new HashMap<>();
        this.suppliers = new HashSet<>();
        this.pendingOrders = new LinkedList<>();
        this.stockItems = new ArrayList<>();
        LOGGER.debug("Created new material inventory");
    }

    public void addMaterial(T material) {
        materialCatalog.put(material.getName(), material);
        stockItems.add(material);
        LOGGER.debug("Added material to inventory: {}", material.getName());
    }

    public T getMaterial(String name) {
        LOGGER.debug("Retrieving material: {}", name);
        return materialCatalog.get(name);
    }

    public void addSupplier(String supplier) {
        suppliers.add(supplier);
        LOGGER.debug("Added supplier: {}", supplier);
    }

    public Set<String> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(Set<String> suppliers) {
        this.suppliers = suppliers;
        LOGGER.debug("Updated suppliers list");
    }

    public void orderMaterial(T material) {
        pendingOrders.add(material);
        LOGGER.debug("Added material to order queue: {}", material.getName());
    }

    public T processPendingOrder() {
        T material = pendingOrders.poll();
        if (material != null) {
            LOGGER.debug("Processed pending order: {}", material.getName());
        } else {
            LOGGER.debug("No pending orders to process");
        }
        return material;
    }

    public Map<String, T> getMaterialCatalog() {
        return materialCatalog;
    }

    public void setMaterialCatalog(Map<String, T> materialCatalog) {
        this.materialCatalog = materialCatalog;
        LOGGER.debug("Updated material catalog");
    }

    public Queue<T> getPendingOrders() {
        return pendingOrders;
    }

    public void setPendingOrders(Queue<T> pendingOrders) {
        this.pendingOrders = pendingOrders;
        LOGGER.debug("Updated pending orders queue");
    }

    public List<T> getStockItems() {
        return stockItems;
    }

    public void setStockItems(List<T> stockItems) {
        this.stockItems = stockItems;
        LOGGER.debug("Updated stock items list");
    }

    public int getTotalInventoryCount() {
        return stockItems.size();
    }

    public double calculateTotalInventoryValue() {
        double total = stockItems.stream()
                .mapToDouble(BuildingMaterial::calculateCost)
                .sum();
        LOGGER.debug("Total inventory value: ${}", total);
        return total;
    }

    public boolean removeMaterial(String name) {
        T material = materialCatalog.remove(name);
        if (material != null) {
            stockItems.removeIf(item -> item.getName().equals(name));
            LOGGER.debug("Removed material from inventory: {}", name);
            return true;
        }
        LOGGER.debug("Material not found in inventory: {}", name);
        return false;
    }

    public void clearInventory() {
        materialCatalog.clear();
        stockItems.clear();
        pendingOrders.clear();
        LOGGER.debug("Cleared inventory");
    }
}

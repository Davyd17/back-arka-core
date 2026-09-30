package com.arka.inventory.warehouse;

public interface LowStockInventoryView {
    Long getProductId();
    int getStock();
    String getProductName();
    String getSku();
    String getProductCategory();
}

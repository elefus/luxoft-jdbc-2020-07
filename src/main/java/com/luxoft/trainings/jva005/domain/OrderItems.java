package com.luxoft.trainings.jva005.domain;

public class OrderItems {

    private String sku;
    private long orderId;
    private int quantity;

    public OrderItems(String sku, long orderId, int quantity) {
        this.sku = sku;
        this.orderId = orderId;
        this.quantity = quantity;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderItems{" +
                "sku='" + sku + '\'' +
                ", orderId=" + orderId +
                ", quantity=" + quantity +
                '}';
    }
}
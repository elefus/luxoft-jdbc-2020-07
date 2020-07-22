package com.luxoft.trainings.jva005.domain;

public class Order {

    private long orderId;
    private long userId;
    private String status;
    private String shippingAddress;

    public Order(long orderId, long userId, String status, String shippingAddress) {
        this.orderId = orderId;
        this.userId = userId;
        this.status = status;
        this.shippingAddress = shippingAddress;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}

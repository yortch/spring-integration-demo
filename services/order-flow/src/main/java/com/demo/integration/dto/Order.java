package com.demo.integration.dto;

public class Order {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;

    public com.integration.demo.webservice.client.Order toWebServiceType() {
        com.integration.demo.webservice.client.Order order = new com.integration.demo.webservice.client.Order();
        order.setId(this.getId());
        return order;
    }
}

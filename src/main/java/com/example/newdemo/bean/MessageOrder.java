package com.example.newdemo.bean;

public class MessageOrder {
    private int order_id;
    private int order_type;
    private int order_statu;
    private String order_message;
    private String order_photoone;
    private String order_phototwo;

    public MessageOrder(int order_id, int order_type, int order_statu, String order_message, String order_photoone, String order_phototwo) {
        this.order_id = order_id;
        this.order_type = order_type;
        this.order_statu = order_statu;
        this.order_message = order_message;
        this.order_photoone = order_photoone;
        this.order_phototwo = order_phototwo;
    }

    public MessageOrder() {
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getOrder_type() {
        return order_type;
    }

    public void setOrder_type(int order_type) {
        this.order_type = order_type;
    }

    public int getOrder_statu() {
        return order_statu;
    }

    public void setOrder_statu(int order_statu) {
        this.order_statu = order_statu;
    }

    public String getOrder_message() {
        return order_message;
    }

    public void setOrder_message(String order_message) {
        this.order_message = order_message;
    }

    public String getOrder_photoone() {
        return order_photoone;
    }

    public void setOrder_photoone(String order_photoone) {
        this.order_photoone = order_photoone;
    }

    public String getOrder_phototwo() {
        return order_phototwo;
    }

    public void setOrder_phototwo(String order_phototwo) {
        this.order_phototwo = order_phototwo;
    }
}

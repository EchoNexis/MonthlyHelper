package com.backend.monthlyHelper.Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class SubscriptionEntity {

    @Id
    String service;

    String date;
    float amount;

    public SubscriptionEntity(){}

    public SubscriptionEntity(String service, String date, float amount){
        this.service = service;
        this.date = date;
        this.amount = amount;
    }

    public String getService(){
        return service;
    }
    public String getDate(){
        return date;
    }
    public float getAmount(){
        return amount;
    }


}

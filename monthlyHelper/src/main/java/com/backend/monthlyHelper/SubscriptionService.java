package com.backend.monthlyHelper;

import com.backend.monthlyHelper.Entity.SubscriptionEntity;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Log
public class SubscriptionService{

    private SubscriptionEntity itemToEdit;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public SubscriptionEntity addSubscription(SubscriptionEntity subscription){
        return subscriptionRepository.save(subscription);
    }

    public List<SubscriptionEntity> getAllSubscriptions(){
        return subscriptionRepository.findAll();
    }

    public void removeSubscription(String id){
        subscriptionRepository.deleteById(id);
    }

    public List<SubscriptionEntity> getSubscriptionToPay(){
        String day = (String.valueOf(new Date().getDate()));
        return subscriptionRepository.findByDateAfter(day);
    }

    public List<SubscriptionEntity> getSubscriptionPayed(){
        String day = (String.valueOf(new Date().getDate()));
        return subscriptionRepository.findByDateBefore(day);
    }

    public void updateSubscription(SubscriptionEntity subscription){
        subscriptionRepository.deleteById(itemToEdit.getService());
        subscriptionRepository.save(subscription);
    }

    public SubscriptionEntity getItemToEdit() {
        return itemToEdit;
    }

    public void setItemToEdit(SubscriptionEntity itemToEdit) {
        this.itemToEdit = itemToEdit;
    }
}

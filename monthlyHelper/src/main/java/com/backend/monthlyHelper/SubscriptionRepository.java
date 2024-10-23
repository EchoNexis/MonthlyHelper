package com.backend.monthlyHelper;

import com.backend.monthlyHelper.Entity.SubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, String> {

    List<SubscriptionEntity> findByDateBefore(String date);

    List<SubscriptionEntity> findByDateAfter(String date);

}

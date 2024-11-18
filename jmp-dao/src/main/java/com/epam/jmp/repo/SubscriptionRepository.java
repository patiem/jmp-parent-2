package com.epam.jmp.repo;

import com.epam.jmp.dto.Subscription;

import java.util.List;
import java.util.function.Predicate;

public interface SubscriptionRepository {

    boolean addSubscription(Subscription subscription);
    boolean addSubscriptions(List<Subscription> subscriptions);
    List<Subscription> getAllSubscriptions();
    List<Subscription> findSubscriptionsByCondition(Predicate<Subscription> condition);
}

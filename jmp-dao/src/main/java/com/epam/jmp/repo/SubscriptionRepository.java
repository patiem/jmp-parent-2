package com.epam.jmp.repo;

import com.epam.jmp.dto.Subscription;

import java.util.List;
import java.util.function.Predicate;

public interface SubscriptionRepository {

    void addSubscription(Subscription subscription);
    void addSubscriptions(List<Subscription> subscriptions);
    List<Subscription> getAllSubscriptions();
}

package com.epam.jmp.repo;

import com.epam.jmp.dto.Subscription;

import java.util.List;

public interface SubscriptionRepository {

    void addSubscription(Subscription subscription);
    List<Subscription> getAllSubscriptions();
}

package com.epam.jmp.repo.impl;

import com.epam.jmp.dto.Subscription;
import com.epam.jmp.repo.SubscriptionRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class SubscriptionRepoImpl implements SubscriptionRepository {
    private final List<Subscription> subscriptions = new ArrayList<>();

    @Override
    public boolean addSubscription(Subscription subscription) {
        subscriptions.add(subscription);
        return false;
    }

    @Override
    public boolean addSubscriptions(List<Subscription> subscriptionsToAdd) {
        var newSubscriptions = subscriptionsToAdd.stream().filter(u -> !subscriptions.contains(u)).toList();
        subscriptions.addAll(newSubscriptions);
        return newSubscriptions.size() == subscriptionsToAdd.size();
    }

    @Override
    public List<Subscription> getAllSubscriptions() {
        return Collections.unmodifiableList(subscriptions);
    }

    @Override
    public List<Subscription> findSubscriptionsByCondition(Predicate<Subscription> condition) {
        return subscriptions.stream().filter(condition).toList();
    }
}

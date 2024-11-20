package com.epam.jmp.repo.impl;

import com.epam.jmp.dto.Subscription;
import com.epam.jmp.repo.SubscriptionRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SubscriptionRepoImpl implements SubscriptionRepository {
    private final List<Subscription> subscriptions = new ArrayList<>();

    @Override
    public void addSubscription(Subscription subscription) {
        subscriptions.add(subscription);
    }

    @Override
    public List<Subscription> getAllSubscriptions() {
        return Collections.unmodifiableList(subscriptions);
    }

}

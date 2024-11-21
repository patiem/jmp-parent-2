package com.epam.jmp.impl;

import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.Subscription;
import com.epam.jmp.dto.User;
import com.epam.jmp.repo.SubscriptionRepository;
import com.epam.jmp.repo.UserRepository;
import com.epam.jmp.repo.impl.SubscriptionNotFoundException;
import com.epam.jmp.service.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;


public class ServiceImpl implements Service {

    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;


    public ServiceImpl(UserRepository userRepository, SubscriptionRepository subscriptionRepository) {
        this.userRepository = userRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public void subscribe(BankCard bankCard) {
        getAllSubscriptionsByCondition(sub -> bankCard.getNumber().equals(sub.getBankcard()))
                .stream()
                .findFirst()
                .ifPresentOrElse(
                        this::logExistingSubscription,
                        () -> addSubscription(bankCard));
    }

    private void logExistingSubscription(Subscription sub) {
        System.out.println("Subscription already exists for: " + sub.getBankcard());
    }

    private void addSubscription(BankCard bankCard) {
        subscriptionRepository.addSubscription(new Subscription(bankCard.getNumber(), LocalDate.now()));
    }

    @Override
    public List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> condition) {
        return subscriptionRepository.getAllSubscriptions()
                .stream()
                .filter(condition)
                .toList();
    }

    @Override
    public Optional<Subscription> getSubscriptionByBankCardNumber(String cardNumber) {
        return Optional.ofNullable(
                getAllSubscriptionsByCondition(sub -> cardNumber.equals(sub.getBankcard()))
                        .stream()
                        .findFirst()
                        .orElseThrow(() -> new SubscriptionNotFoundException("No subscription found for bank card: " + cardNumber)));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }
}

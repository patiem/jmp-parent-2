package com.epam.jmp.service;

import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.Subscription;
import com.epam.jmp.dto.User;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

public interface Service {
    double MATURITY_AGE = 18;

    void subscribe(BankCard bankCard);

    List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> condition);

    Optional<Subscription> getSubscriptionByBankCardNumber(String cardNumber);

    List<User> getAllUsers();

    default double getAverageUsersAge() {
        return getAllUsers()
                .stream()
                .filter(Objects::nonNull)
                .filter(u -> Objects.nonNull(u.getBirthday()))
                .mapToDouble(u -> ChronoUnit.YEARS.between(u.getBirthday(), LocalDate.now()))
                .average()
                .orElse(0d);
    }

    static boolean isPayableUser(User user) {
        var birthday = Optional.ofNullable(user)
                .map(User::getBirthday)
                .orElseThrow(() -> new IllegalArgumentException("user age is unknown"));
        return ChronoUnit.YEARS.between(birthday, LocalDate.now()) >= MATURITY_AGE;
    }
}

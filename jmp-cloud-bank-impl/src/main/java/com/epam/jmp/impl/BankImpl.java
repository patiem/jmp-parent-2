package com.epam.jmp.impl;

import com.epam.jmp.dto.*;
import com.epam.jmp.service.Bank;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

public class BankImpl implements Bank {

    private final Map<BankCardType, BiFunction<String, User, BankCard>> cardCreators = new EnumMap<>(BankCardType.class);

    public BankImpl() {
        cardCreators.put(BankCardType.CREDIT, CreditBankCard::new);
        cardCreators.put(BankCardType.DEBIT, DebitBankCard::new);
    }

    @Override
    public BankCard createBankCard(User user, BankCardType cardType) {
        var biFunction = Optional.ofNullable(cardCreators.get(cardType))
                .orElseThrow(() -> new IllegalArgumentException("Invalid car type"));
        return biFunction.apply(Bank.generateCardNumber(user), user);
    }
}

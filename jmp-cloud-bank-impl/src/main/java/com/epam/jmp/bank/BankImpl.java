package com.epam.jmp.bank;

import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.BankCardType;
import com.epam.jmp.dto.User;
import com.epam.jmp.service.bank.Bank;
import com.epam.jmp.service.bank.CardNumberGenerator;

import java.util.List;
import java.util.Optional;

public class BankImpl implements Bank {

    private final CardNumberGenerator generator;
    private final List<BankCardType> cardTypesForBank;

    public BankImpl(CardNumberGenerator generator, List<BankCardType> cardTypesForBank) {
        this.generator = generator;
        this.cardTypesForBank = cardTypesForBank;
    }

    @Override
    public BankCard createBankCard(User user, BankCardType cardType) {
        Optional.ofNullable(user).orElseThrow(() -> new IllegalArgumentException("User cannot be null"));

        var biFunction = Optional.ofNullable(cardType)
                .filter(cardTypesForBank::contains)
                .map(type -> type.function)
                .orElseThrow(() -> new IllegalArgumentException("Invalid card type"));
        return biFunction.apply(generator.generateNumber(), user);
    }
}

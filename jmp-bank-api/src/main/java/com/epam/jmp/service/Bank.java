package com.epam.jmp.service;

import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.BankCardType;
import com.epam.jmp.dto.User;

import java.util.UUID;

public interface Bank {
    BankCard createBankCard(User user, BankCardType cardType);

    static String generateCardNumber() {
        return UUID.randomUUID().toString();
    }
}

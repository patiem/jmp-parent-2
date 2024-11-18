package com.epam.jmp.service;

import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.BankCardType;
import com.epam.jmp.dto.User;

import java.util.Random;

public interface Bank {
    BankCard createBankCard(User user, BankCardType cardType);

    static String generateCardNumber(User user) {
        return new Random().nextInt(10) + user.getBirthday().toEpochDay() + "";
    }
}

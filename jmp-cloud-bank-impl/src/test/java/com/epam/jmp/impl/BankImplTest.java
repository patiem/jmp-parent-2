package com.epam.jmp.impl;


import com.epam.jmp.bank.BankImpl;
import com.epam.jmp.dto.BankCardType;
import com.epam.jmp.dto.CreditBankCard;
import com.epam.jmp.dto.User;
import com.epam.jmp.service.bank.CardNumberGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class BankImplTest {

    private static final String CARD_NUMBER = "111222333";

    @Mock
    private CardNumberGenerator generator;
    @Mock
    private User user;
    private BankImpl bank;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        bank = new BankImpl(generator, List.of(BankCardType.CREDIT));
        when(generator.generateNumber()).thenReturn(CARD_NUMBER);
    }

    @Test
    public void throwsIllegalArgumentExceptionWhenUserIsNull() {
        var exception = assertThrows(IllegalArgumentException.class,
                () -> bank.createBankCard(null, BankCardType.CREDIT));
        var message = "User cannot be null";
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void throwsIllegalArgumentExceptionWhenCardTypeIsNull() {
        var exception = assertThrows(IllegalArgumentException.class,
                () -> bank.createBankCard(user, null));
        var message = "Invalid card type";
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void throwsIllegalArgumentExceptionWhenCardTypeNotListedInBank() {
        var exception = assertThrows(IllegalArgumentException.class,
                () -> bank.createBankCard(user, BankCardType.DEBIT));
        var message = "Invalid card type";
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void createValidBankCardWithTypeAndNumber() {
        var bankCard = bank.createBankCard(user, BankCardType.CREDIT);

        assertEquals(CARD_NUMBER, bankCard.getNumber());
        assertEquals(CreditBankCard.class, bankCard.getClass());
    }




}
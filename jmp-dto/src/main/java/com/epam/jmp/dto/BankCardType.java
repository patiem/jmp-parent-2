package com.epam.jmp.dto;

import java.util.function.BiFunction;

public enum BankCardType {
    CREDIT (CreditBankCard::new),
    DEBIT (DebitBankCard::new);

    public final BiFunction<String, User, BankCard> function;

    BankCardType(BiFunction<String, User, BankCard> function) {
        this.function = function;
    }


}

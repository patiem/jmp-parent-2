package com.epam.jmp.bank;

import com.epam.jmp.service.bank.CardNumberGenerator;

import java.util.Random;
import java.util.stream.Collectors;

public class RandomCardNumberGenerator implements CardNumberGenerator {

    private Random random;
    private int cardNumberLength;

    public RandomCardNumberGenerator(int cardNumberLength) {
        this.cardNumberLength = cardNumberLength;
        random = new Random();
    }

    @Override
    public String generateNumber() {
        return random.ints(0, 10)
                .limit(cardNumberLength)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining());
    }
}

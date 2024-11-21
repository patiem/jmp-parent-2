package com.epam.jmp.impl;

import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.Subscription;
import com.epam.jmp.dto.User;
import com.epam.jmp.repo.SubscriptionRepository;
import com.epam.jmp.repo.UserRepository;
import com.epam.jmp.repo.impl.SubscriptionNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class ServiceImplTest {

    private static final String CARD_NUMBER = "1234567890";
    @Mock
    private SubscriptionRepository subscriptionRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BankCard bankCard;
    @Captor
    private ArgumentCaptor<Subscription> subscriptionCaptor;

    private ServiceImpl service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new ServiceImpl(userRepository, subscriptionRepository);
    }

    @Test
    public void addNewSubscription() {
        //given
        when(bankCard.getNumber()).thenReturn(CARD_NUMBER);
        when(subscriptionRepository.getAllSubscriptions())
                .thenReturn(Collections.emptyList());

        //when
        service.subscribe(bankCard);

        //then
        verify(subscriptionRepository).addSubscription(subscriptionCaptor.capture());
        var subscription = subscriptionCaptor.getValue();
        assertEquals(CARD_NUMBER, subscription.getBankcard());
    }

    @Test
    public void doNotAddNewSubscriptionWhenCardNumberAlreadyExistsInRepository() {
        //given
        var subscription = new Subscription(CARD_NUMBER, LocalDate.now());
        when(bankCard.getNumber()).thenReturn(CARD_NUMBER);
        when(subscriptionRepository.getAllSubscriptions())
                .thenReturn(Collections.singletonList(subscription));

        //when
        service.subscribe(bankCard);

        //then
        verify(subscriptionRepository, never()).addSubscription(any(Subscription.class));
    }


    @Test
    public void getSubscriptionByBankCardNumberIfExistsInRepository() {
        //given
        var subscription = new Subscription(CARD_NUMBER, LocalDate.now());
        when(subscriptionRepository.getAllSubscriptions())
                .thenReturn(Collections.singletonList(subscription));

        //when
        var result = service.getSubscriptionByBankCardNumber(CARD_NUMBER);

        //then
        assertNotNull(result.get());
        assertEquals(CARD_NUMBER, result.get().getBankcard());
    }

    @Test
    public void trowsSubscriptionNotFoundExceptionIfSubscriptionNotExist() {
        //given
        when(subscriptionRepository.getAllSubscriptions())
                .thenReturn(Collections.emptyList());

        //when-then
        var exception = assertThrows(SubscriptionNotFoundException.class,
                () -> service.getSubscriptionByBankCardNumber(CARD_NUMBER));
        var message = "No subscription found for bank card: " + CARD_NUMBER;
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void getSubscriptionsByCondition() {
        //given
        var now = LocalDate.now();
        Predicate<Subscription> condition = c -> c.getStartDate().isAfter(LocalDate.now().minusYears(1));
        var subscription1 = new Subscription(CARD_NUMBER, now);
        var subscription2 = new Subscription(CARD_NUMBER + "1", now.plusYears(2));
        var subscription3 = new Subscription(CARD_NUMBER + "2", now.minusYears(2));

        when(subscriptionRepository.getAllSubscriptions())
                .thenReturn(List.of(subscription1, subscription2, subscription3));

        //when
        var result = service.getAllSubscriptionsByCondition(condition);

        //then
        assertEquals(2, result.size());
        var numbers = result.stream().map(Subscription::getBankcard).toList();
        assertTrue(numbers.containsAll(List.of(CARD_NUMBER + "1", CARD_NUMBER)));
    }

    @Test
    public void getAllUsers() {
        //given
        var user = Mockito.mock(User.class);
        var user2 = Mockito.mock(User.class);
        when(userRepository.getAllUsers()).thenReturn(List.of(user, user2));

        //when
        var result = service.getAllUsers();

        //then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.containsAll(List.of(user, user2)));
    }

}
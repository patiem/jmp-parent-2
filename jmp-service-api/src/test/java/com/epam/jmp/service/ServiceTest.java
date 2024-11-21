package com.epam.jmp.service;

import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.Subscription;
import com.epam.jmp.dto.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ServiceTest {

    private static final LocalDate TODAY =  LocalDate.now();
    private final IntUnaryOperator unOp = n -> n + 7;


    @Test
    void shouldCalculateAverageUsersAge() {
        // given
        var users = IntStream.iterate(10, unOp)
                .limit(10)
                .mapToObj(this::mockUserWithAge)
                .collect(Collectors.toList());

        var service = createServiceWithUsers(users);

        // when
        var averageFromService = service.getAverageUsersAge();

        //then
        var expected = IntStream.iterate(10, unOp)
                .limit(10)
                .average()
                .orElse(0d);
        assertEquals(expected, averageFromService);
    }

    @Test
    void shouldIgnoreNullsUsersWhenCalculatingAverageAge() {
        // given
        var users = IntStream.iterate(10, unOp)
                .limit(10)
                .mapToObj(this::mockUserWithAge)
                .collect(Collectors.toList());

        users.add(users.size()/2, null);
        users.add(null);
        var service = createServiceWithUsers(users);

        // when
        var actual = service.getAverageUsersAge();

        // then
        var expected = IntStream.iterate(10, unOp)
                .limit(10)
                .average()
                .orElse(0d);
        assertEquals(expected, actual);
    }

    @Test
    void shouldIgnoreUsersWithNullBirthdayWhenCalculatingAverageAge() {
        // given
        var users = IntStream.iterate(10, unOp)
                .limit(10)
                .mapToObj(this::mockUserWithAge)
                .collect(Collectors.toList());


        users.add(users.size()/2, Mockito.mock(User.class));
        users.add(Mockito.mock(User.class));
        var service = createServiceWithUsers(users);

        // when
        double actual = service.getAverageUsersAge();

        // then
        double expected = IntStream.iterate(10, unOp)
                .limit(10)
                .average()
                .orElse(0d);
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturn0WhenAllBirthdaysAreNullWhenCalculatingAverageAge() {
        // given
        List<User> users = new ArrayList<>();
        users.add(Mockito.mock(User.class));
        users.add(Mockito.mock(User.class));
        var service = createServiceWithUsers(users);

        // when
        double actual = service.getAverageUsersAge();

        // then
        assertEquals(0, actual);
    }

    @Test
    void isPayableUserTrueForAge18() {
        var user = mockUserWithAge(18);
        assertTrue(Service.isPayableUser(user));
    }

    @Test
    void isPayableUserTrueForAgeOver18() {
        var user = mockUserWithAge(35);
        assertTrue(Service.isPayableUser(user));
    }

    @Test
    void isPayableUserFalseForAgeLessThan18() {
        assertFalse(Service.isPayableUser(mockUserWithAge(10)));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionForIsPayableWhenUserIsNull() {
        var exception = assertThrows(IllegalArgumentException.class,
                () -> Service.isPayableUser(null));
        var message = "User or user age is unknown";
        assertEquals(message, exception.getMessage());
    }

    @Test
    void shouldThrowIllegalArgumentExceptionForIsPayableWhenUsersBirthdayIsNull() {
        var exception = assertThrows(IllegalArgumentException.class,
                () -> Service.isPayableUser(Mockito.mock(User.class)));
        var message = "User or user age is unknown";
        assertEquals(message, exception.getMessage());
    }


    private User mockUserWithAge(int age) {
        var mockUser = Mockito.mock(User.class);
        var birthday = TODAY.minusYears(age);
        when(mockUser.getBirthday()).thenReturn(birthday);
        return mockUser;
    }

    private Service createServiceWithUsers(List<User> users) {
        return new TestServiceImpl() {

            @Override
            public List<User> getAllUsers() {
                return users;
            }
        };
    }

    private abstract static class TestServiceImpl implements Service {

        @Override
        public void subscribe(BankCard bankCard) {
        }

        @Override
        public Optional<Subscription> getSubscriptionByBankCardNumber(String cardNumber) {
            return Optional.empty();
        }

        @Override
        public List<User> getAllUsers() {
            return List.of();
        }

        @Override
        public List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> condition) {
            return List.of();
        }
    }
}
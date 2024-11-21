package com.epam.jmp;

import com.epam.jmp.dto.BankCardType;
import com.epam.jmp.dto.Subscription;
import com.epam.jmp.dto.User;
import com.epam.jmp.bank.BankImpl;
import com.epam.jmp.bank.RandomCardNumberGenerator;
import com.epam.jmp.impl.ServiceImpl;
import com.epam.jmp.repo.impl.SubscriptionRepoImpl;
import com.epam.jmp.repo.impl.UserRepoImpl;

import java.time.LocalDate;
import java.util.List;


public class App 
{
    public static void main( String[] args ) {
        var userRepository = new UserRepoImpl();
        var subscriptionRepository = new SubscriptionRepoImpl();

        var service = new ServiceImpl(userRepository, subscriptionRepository);
        var bank = new BankImpl(new RandomCardNumberGenerator(10), List.of(BankCardType.CREDIT, BankCardType.DEBIT));

        var user1 = new User("Elton", "John", LocalDate.of(1980, 5, 14));
        var user2 = new User("Billy", "Joel", LocalDate.of(1982, 7, 16));
        var user3 = new User("Paul", "McCartney", LocalDate.of(1984, 7, 16));

        userRepository.addUser(user1);
        userRepository.addUser(user2);
        userRepository.addUser(user3);

        var creditCardE = bank.createBankCard(user1, BankCardType.CREDIT);
        var debitCardB = bank.createBankCard(user2, BankCardType.DEBIT);
        var debitCardP = bank.createBankCard(user3, BankCardType.DEBIT);

        double averageUsersAge = service.getAverageUsersAge();
        System.out.println("Average users age: " + averageUsersAge);


        service.subscribe(creditCardE);
        service.subscribe(debitCardP);
        service.subscribe(debitCardB);

        System.out.println("All Users:");
        var users = userRepository.getAllUsers();
        users.forEach(user -> System.out.println(user.getName() + " " + user.getSurname()));

        System.out.println("All Subscriptions:");
        var subscriptions = subscriptionRepository.getAllSubscriptions();
        subscriptions.forEach(subscription -> System.out.println("Card Number: " + subscription.getBankcard() + ", Start Date: " + subscription.getStartDate()));

        System.out.println("Subscription Details for Card Number: " + creditCardE.getNumber());
        var subOpt = service.getSubscriptionByBankCardNumber(creditCardE.getNumber());
        if (subOpt.isPresent()) {
            Subscription subscription = subOpt.get();
            System.out.println("Card Number: " + subscription.getBankcard() + ", Start Date: " + subscription.getStartDate());
        }
    }
}

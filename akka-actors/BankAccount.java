package com.example;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;


public class BankAccount extends AbstractActor {

    private float balance;

    public BankAccount() {
        this.balance = 100;
        System.out.println("New bank account created.\nCurrent Balance: GBP " + this.balance + "\n");
    }


    // Props is used to define the configuration settings for each Actor that specifies the Actors job like dispatcher, mailbox, router, etc.
    public static Props props() {
        return Props.create(BankAccount.class, BankAccount::new);
    }

    @Override
    public Receive createReceive() {       // Required by AbstractActor
        // receiveBuilder object encapsulates the actor's behaviour by defining message handling logic
        return receiveBuilder()
                .match(Deposit.class, this::onDeposit)    // maps Deposit to method onDeposit
                .match(Withdrawal.class, this::onWithdrawal)    // this::method is a method reference that points to a method in the same class
                .build();       // finalizes the construction of the Receive object, setting up the actor's behaviour based on the matches defined above
    }

    private void onDeposit(Deposit msg) {
        this.balance += msg.amount;
        System.out.println("GBP " + msg.amount + " was just deposited into your account.");
        System.out.println("Current Balance: GBP " + this.balance + "\n");
    }

    private void onWithdrawal(Withdrawal msg) {
        float withdrawnAmount = msg.amount;
        // If amount is negative than this makes it positive
        if(withdrawnAmount < 0){
            withdrawnAmount = withdrawnAmount*-1;
        }

        this.balance -= withdrawnAmount;
        System.out.println("GBP " + withdrawnAmount + " was just withdrawn from your account.");
        System.out.println("Current Balance: GBP " + this.balance + "\n");
    }
}
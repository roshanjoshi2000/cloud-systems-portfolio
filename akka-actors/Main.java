package com.example;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import java.io.IOException;
import java.util.Random;

class Main {
    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create();
        ActorRef bankAccountRef = system.actorOf(Props.create(BankAccount.class));

        for(int i=1; i <=10; i++){
            Random random = new Random();
            int randomValue = random.nextInt(2001) - 1000;      // Generates random value between -1000 and 1000
            if(randomValue>=0){
                bankAccountRef.tell(new Deposit(randomValue),bankAccountRef);
            }
            else{
                bankAccountRef.tell(new Withdrawal(randomValue),bankAccountRef);
            }
        }

        try {
            System.out.println("Press ENTER twice to end program.");
            System.in.read();       // Required with system.terminate: To read the enter press twice before terminating so that all actors can work till this action is performed
        }
        catch (IOException ignored) { }
        finally {
            system.terminate();
            System.out.println("Terminated.");
        }
    }

}

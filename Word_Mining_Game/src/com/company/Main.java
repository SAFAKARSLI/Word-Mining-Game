package com.company;

import java.util.ArrayList;
import java.util.Scanner;



public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nWELCOME TO 'WORD MINING' GAME\n");

        //Introduction to the game
        System.out.println("In this game you will be needing 3 player, 2 to play 1 to guide (to be admin). After game chooses which one of the\n" +
                "players is going to goes first, you will see a table fully with letters picked randomly up by computer. All you need to win the game\n" +
                "is creating words by using given letters. Of course longer word means more score. Game will continue until both players gave up or made mistake\n" +
                "for 3 times. If one of the players gave up, other player will keep playing until he or she gave up or reach the allowed mistake limit.\n" +
                "which player find the longest word, him or her score will multiply by 1.5 end of the game. Let's mine words...");
        while(true) {
            //Naming part
            //Player 1
            System.out.println("\nPlayer 1 Name = ");
            String p1Name = scanner.next();

            //Player 2
            System.out.println("Player 2 Name = ");
            String p2Name = scanner.next();

            //Admin
            System.out.println("Admin name = ");
            String adminName = scanner.next();
            Player admin = new Player(adminName.substring(0,1).toUpperCase() + adminName.substring(1));

            //Language choosing
            String language;
            boolean isEnglish = true;
            while(true) {
                System.out.println("\n1-ENGLISH\n" +
                        "2-TURKISH\n" +
                        "Please choose which language do you wanna play?(1 or 2)");
                language = scanner.next();
                if((language.equals("1")) || (language.equals("2"))){
                    switch (language) {
                        case "1":
                            System.out.println("You chose ENGLISH");
                            break;
                        case "2":
                            System.out.println("You chose TURKISH");
                            isEnglish = false;
                            break;
                    }
                    break;
                }
                System.out.println("Invalid Input!!");
            }


            //Who goes first?
            System.out.println("\nGame will chose which one of you gonna go the first...");
            sleep(1500);
            System.out.println("Coin flipping...");
            String firstPlayer = whoGoesFirst(p1Name,p2Name);

            //Who goes second? (ternary for the first time)
            String secondPlayer = firstPlayer.equals(p1Name) ? p2Name : p1Name;
            sleep(2000);

            //Capitalizing first letters of the names
            Player first = new Player(firstPlayer.substring(0,1).toUpperCase() + firstPlayer.substring(1));
            Player second = new Player(secondPlayer.substring(0,1).toUpperCase() + secondPlayer.substring(1));

            //Game starts
            Game game = new Game(isEnglish);
            ArrayList<Character> letters = game.letterChoosing();

            System.out.println("=============================\n"+
                    "Winner of the coin flip is "+first.getName()+"\nGame starts with " + first.getName() + " !!!\n");

            while(true) {
                //First player's turn
                if(first.getMiss() < 3) {
                    int result = game.move(first,second,admin,letters);
                    first.addMiss(checkResult(result));
                } else if (second.getMiss() == 3){
                    break;
                }

                //Second player's turn
                if(second.getMiss() < 3) {
                    int result = game.move(second,first,admin,letters);
                    second.addMiss(checkResult(result));
                } else if (first.getMiss() == 3) {
                    break;
                }
            }
            //Winner
            winner(first,second);
            //Wanna play again?
            if(again()) { continue; }
            break;

        }

    }

    public static void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String whoGoesFirst(String name1, String name2) {
        int random = (int) Math.round(Math.random());
        if(random == 0) {
            return name1;
        }
        return name2;
    }

    public static int checkResult(int result) {
        switch (result) {
            case 0:// accepted
                return 0;
            case 1:// refused
                return 1;
            case 2:// gave up
                return 3;
        }
        return checkResult(result);
    }

    public static String winner(Player player1, Player player2) {
        System.out.println("\n===================");
        sleep(2000);
        if(player1.getScore() == player2.getScore()) {
            System.out.println("Tied!!!!\n" +
                    "Both player has exact same score...");
            return "tied";
        }
        String winner = player1.getScore() > player2.getScore() ? player1.getName() : player2.getName();
        sleep(500);
        System.out.println("Winner of the game is...");
        sleep(2000);
        System.out.println(winner.toUpperCase() + " has won the game!!!\n");

        System.out.println(player1.getName()+"'s score " + player1.getScore() +
                "\n"+player2.getName()+"'s score "  + player2.getScore());
        return winner;
    }

    public static boolean again() {
        Scanner scan = new Scanner(System.in);
        System.out.println("\nDo you wanna play again?(y/n)");
        String answer = scan.nextLine();
        switch (answer) {
            case "y":
            case "Y":
                return true;
            case "n":
            case "N":
                return false;
            default:
                System.out.println("Invalid Input!!!");
                return again();
        }
    }
}








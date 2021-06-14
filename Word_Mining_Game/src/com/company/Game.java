package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Game {

    private int mehmet;
    private ArrayList<Character> vowels;
    private ArrayList<Character> consonants;
    boolean isEnglish;

    //Constructor
    public Game(boolean isEnglish) {

        if(isEnglish) {
            this.vowels = new ArrayList<Character>(Arrays.asList(
                    'a','e','i','o','u'
            ));
            this.consonants = new ArrayList<Character>(Arrays.asList(
                    'b','c','d','f','g','h','j','k','l','m','n','p','q','r','s','t','v','w','x','y','z'
            ));
        }
        else{
            this.vowels = new ArrayList<Character>(Arrays.asList(
                    'a','e','ı','i','o','ö','u','ü'
            ));
            this.consonants = new ArrayList<Character>(Arrays.asList(
                    'b','c','ç','d','f','g','ğ','h','j','k','l','m','n','p','r','s','ş','t','v','y','z'
            ));
        }
    }

    public ArrayList<Character> letterChoosing() {
        ArrayList<Character> letters = new ArrayList<Character>();
        int vowelCounter = 0;
        while(letters.size()<16) {
            //random vowel
            if(vowelCounter < 6) {
                char randomVowel = randomChars(this.vowels);
                letters.add(randomVowel);
                vowelCounter++;
            }

            //random consonant
            while(true) {
                char randomConsonant = randomChars(this.consonants);
                //if includes that consonant, chooses another number randomly again
                if(letters.contains(randomConsonant)) { continue; }
                letters.add(randomConsonant);
                break;
            }
        }
        return letters;
    }

    public char randomChars(ArrayList<Character> array) {
        int number = (int) Math.floor(Math.random() * array.size());
        return array.get(number);
    }


    public void printLetters(ArrayList<Character> array) {
        int PL = 0;
        while(PL < array.size()) {
            System.out.println(array.get(PL) + "  "+array.get(PL+1)+ "  "+array.get(PL+2)+ "  "+array.get(PL+3));
            PL += 4;
        }
        System.out.println("-----------------");
    }

    public boolean doYouAccept(Player player, String word) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Does "+ player.getName()+" (admin) accept the word ("+word+")? (y/n)");
        String answer = scanner.next();

        switch (answer) {
            case "y":
            case "Y":
                System.out.println(player.getName() + " (admin) accepted the word\n");
                return true;
            case "n":
            case "N":
                System.out.println(player.getName()+ " (admin) refused the word\n");
                return false;
            default:
                System.out.println("Invalid Input\n");
                return doYouAccept(player, word);
        }
    }

    public String askForWord(Player player, ArrayList<Character> array) {
        //To not mutate the given array
        ArrayList<Character> subArr = new ArrayList<Character>(array);

        //Asks and gets word from player
        Scanner scan = new Scanner(System.in);
        System.out.println(player.getName() +", please make a sensible word by using given letters...(length of the answer must be at least 2)\n" +
                "(If you didn't find a word, type 1)");
        String answer = scan.next();

        //Checks each char whether array contains or is there enough for preferred word
        for(int i = 0; i<answer.length();i++) {
            if(!subArr.contains(answer.charAt(i))){
                if(answer.equals("1") || (answer.equals(" "))) continue;
                System.out.println("Invalid Input!!!\n");
                return askForWord(player,array);
            }
            subArr.remove(subArr.indexOf(answer.charAt(i)));
        }

        //Checks if the word is too short
        if((answer.length() < 2) && (!answer.equals("1"))) {
            System.out.println("Your answer is too short or includes spaces!!!\n");
            return askForWord(player,array);
        }
        return answer;
    }

    public void score(Player player, String word) {
        player.getMined().add(word);

        //Scoring according to the length of the word
        if(word.length() <= 3) { player.addScore(word.length()*2); }
        else player.addScore(word.length()*3);

    }

    public int move(Player first, Player second, Player admin, ArrayList<Character> letters) {
        printLetters(letters);
        String word = askForWord(first,letters);

        //Checks if the word mined before
        if( (first.getMined().contains(word)) || (second.getMined().contains(word))) {
            System.out.println("This word is mined before!! Please enter another word...\n");
            return move(first,second,admin,letters);
        }

        //Checks if the player gave up
        if(word.equals("1") && (second.getMiss() < 3)) {
            System.out.println(">> "+  first.getName() +" disqualified. If "+second.getName() +" also gives up, game will over <<\n");
            return 2;
        } else if (word.equals("1") && (second.getMiss() >= 3)){
            System.out.println(first.getName() + " disqualified too...\n");
            return 2;
        }

        // Asks whether admin accepts?
        if (doYouAccept(admin, word)) {
            score(first,word);
            return 0;
        }

        else return 1;
    }
}

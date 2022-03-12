package spell;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class SpellCorrector implements ISpellCorrector {
    private Trie myTrie;
    private String currentGuess;

    public SpellCorrector() {
        myTrie = new Trie();
        currentGuess = null;
    }

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        Scanner sc = new Scanner(new File(dictionaryFileName));
        while (sc.hasNext()) {
            myTrie.add(sc.next());
        }
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        currentGuess = null;
        if (inputWord == null) {
            return inputWord;
        }
        if (myTrie.find(inputWord) != null) {
            currentGuess = inputWord;
            return inputWord;
        }
        for (int dist = 1; dist < 2 && currentGuess == null; dist++) {
            analyse(dist, inputWord);
        }
        return currentGuess;
    }

    private void analyse(int distance, String inputWord) {
        if (distance == 0) {
            compare(inputWord);
        }
        //For (int i=0; i < 2 && string == null; i++)
        //for (char addChar = 'a'; addChar <= 'z'; addChar++)
        /*for (int i = 0; i < 26; i++) { //Need to remove one character
            analyse(distance-1,inputWord.substring(0,i));
        }*/
        for (int a = 0; a < 26; a++) { //transposition
            for (int b = a+1; b < 26; b++) {
                analyse(distance - 1, inputWord.substring(0, a) + inputWord.charAt(b) + inputWord.substring(a + 1, b) + inputWord.charAt(a) + inputWord.substring(b + 1));
            }
        }
        for (int i = 0; i < 26; i++) { //deletion
            for (char addChar = 'a'; addChar <= 'z'; addChar++) {
                analyse(distance - 1, inputWord.substring(0, i) + addChar + inputWord.substring(i));
            }
        }
        for (int i = 0; i < 26; i++) { //alteration
            for (char addChar = 'a'; addChar <= 'z'; addChar++) {
                analyse(distance - 1, inputWord.substring(0, i) + addChar + inputWord.substring(i+1));
            }
        }
    }
    private void compare (String possible) {
        if (currentGuess == null) {
            currentGuess = possible;
            return;
        }/*
        if (myTrie.find(currentGuess).getValue() < myTrie.find(possible).getValue()) {
            currentGuess = possible;
            return;
        }*/
        currentGuess = possible;
       //needs to do it alphabetically as well
    }
}
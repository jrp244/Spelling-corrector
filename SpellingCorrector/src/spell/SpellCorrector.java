package spell;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class SpellCorrector implements ISpellCorrector {
    private Trie myTrie;
    private String correctWord;

    public SpellCorrector() {
        myTrie = new Trie();
        correctWord = null;
    }

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        //scanner class
        Scanner sc = new Scanner(new File(dictionaryFileName));
        while (sc.hasNext()) {
            myTrie.add(sc.next());
        }
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        correctWord = null;
        Set<String> bigSetString = new HashSet<>();
        Set<String> bigSetString2 = new HashSet<>();
        inputWord = inputWord.toLowerCase(); //Lowercase the word
        StringBuilder builder = new StringBuilder();
        builder.append(inputWord);
        //see if inputWord is valid:
        if (myTrie.find(inputWord) != null) {
            return inputWord;
        }
        alteration(inputWord,bigSetString);
        insert(inputWord, bigSetString);
        deletion(inputWord, bigSetString);
        transposition(inputWord, bigSetString);
        for(String s : bigSetString) {
            if (myTrie.find(s) != null) {
                /*if(s.equals("yea")) {
                    System.out.printf("yea: \n %d",myTrie.find(s).getValue());
                }
                if(s.equals("yeah")) {
                    System.out.printf("\n yeah: \n %d",myTrie.find(s).getValue());
                }*/
                if (correctWord == null) {
                    correctWord = s;
                    System.out.printf("%s: \n %d",correctWord,myTrie.find(correctWord).getValue());

                }
                else {
                    System.out.printf("%s: \n %d",correctWord,myTrie.find(correctWord).getValue());
                    compareFrequency(s);
                    System.out.printf("%s: \n %d",correctWord,myTrie.find(correctWord).getValue());
                }
            }
        }
        if (correctWord != null) {
            return correctWord;
        }
        //distance = 2;
        if (correctWord == null) {
            for(String s : bigSetString) {
                alteration(s, bigSetString2);
                insert(s, bigSetString2);
                deletion(s, bigSetString2);
                transposition(s, bigSetString2);
            }
            for(String s : bigSetString2) {
                if (myTrie.find(s) != null) {
                    if (correctWord == null) {
                        correctWord = s;
                    } else {
                        compareFrequency(s);
                    }
                }
            }
        }
        bigSetString.clear();
        bigSetString2.clear();
        return correctWord;
    }

    public void compareFrequency (String possibleWord) {
        if (myTrie.find(possibleWord).getValue() > myTrie.find(correctWord).getValue()) {
            System.out.printf("\n%s \n %d\n",possibleWord, myTrie.find(possibleWord).getValue());
            System.out.printf("\n%s \n %d\n",correctWord, myTrie.find(correctWord).getValue());
            correctWord = possibleWord;
            System.out.printf("\n%s \n %d\n",correctWord, myTrie.find(correctWord).getValue());
        }
        else if ((possibleWord.compareTo(correctWord) < 0 )&& myTrie.find(possibleWord).getValue() == myTrie.find(correctWord).getValue()) {
            correctWord = possibleWord;
        }
    }

    public Set transposition(String inputWord, Set setString) {
        //transposition 1 distance
        StringBuilder builder = new StringBuilder();
        builder.append(inputWord);
        StringBuilder newBuilder = new StringBuilder(); //Only holds one letter at a time
        for (int j = 0; j < inputWord.length()-1; j++) {
            char temp = builder.charAt(j+1);
            newBuilder.append(builder.charAt(j));
            builder.replace(j+1,j+1, newBuilder.toString());
            newBuilder.delete(0, newBuilder.length()); //resets newBuilder
            newBuilder.append(temp);//resets newBuilder
            builder.replace(j,j, newBuilder.toString());
            builder.delete(j+2, j+4);
            setString.add(builder.toString());
            newBuilder.delete(0, newBuilder.length()); //resets newBuilder
            builder.delete(0, builder.length()); //resets builder
            builder.append(inputWord);//resets builder
        }
        return setString;
    }
    public Set insert (String inputWord, Set setString) {
        StringBuilder builder = new StringBuilder();
        builder.append(inputWord);
        //insert 1 letter
        for (int i = 0; i < inputWord.length()+1; i++) {
            builder.delete(0, builder.length()); //resets builder
            builder.append(inputWord);//resets builder
            for (int j = 0; j < 26; j++) {
                StringBuilder newbuilder = builder;
                newbuilder.insert((i), (char)('a' + j));
                setString.add(newbuilder.toString());
                builder.deleteCharAt(i);
            }
        }
        return setString;
    }
    public Set deletion (String inputWord, Set setString) {
        StringBuilder builder = new StringBuilder();
        builder.append(inputWord);
        //Deletion of letter
        for (int i = 0; i < inputWord.length(); i++) {
            builder.delete(0, builder.length()); //resets builder
            builder.append(inputWord); //resets builder
            builder.deleteCharAt((i));
            setString.add(builder.toString());
        }
        return setString;
    }
    public Set alteration (String inputWord, Set setString) {
        StringBuilder builder = new StringBuilder();
        builder.append(inputWord);
        //1 distance change one letter
        for (int i = 0; i < inputWord.length(); i++) {
            builder.delete(0, builder.length()); //resets builder
            builder.append(inputWord);//resets builder
            for (int j = 0; j < 26; j++) {
                builder.setCharAt((i), (char)('a' + j));
                //System.out.println(builder.toString());
                setString.add(builder.toString());
            }
        }
        return setString;
    }
    //End of class
}

package spell;

import java.security.cert.PolicyNode;
import java.util.Locale;

public class Trie implements ITrie{
    private Node root;
    private int wordCount;
    private int nodeCount;
    public int numberofWords;
public Trie () {
    root = new Node();
    wordCount = 0;
    nodeCount = 1;
    numberofWords = 0;
}
    @Override
    public void add(String word) {
        if (word == null || word.isEmpty()) { //If word doesn't exist
            throw new IllegalArgumentException("Invalid input");
        }
        word = word.toLowerCase(); //Lowercase the word
        Node current = root;
        for (int i=0; i < word.length(); i++) {
            char c = word.charAt(i);
            int index = c-'a';
            if(current.getChildren()[index] == null) {
                Node newNode = new Node();
                current.getChildren()[index] = newNode;
                //current = newNode;
                nodeCount++;
                current = (Node)current.getChildren()[index];
            }
            else {
                current = (Node)current.getChildren()[index];
            }
        }
        numberofWords++;
        if (current.getValue() == 0) {
            wordCount++; //unique words
        }
        current.incrementValue();
    }

    @Override
    public INode find(String word) {
        // Maybe?:
        int level;
        int length = word.length();
        int index;
        Node pCrawl = root;

        for (level = 0; level < length; level++)
        {
            index = word.charAt(level) - 'a';

            if (pCrawl.children[index] == null) {
                return null;
            }
            pCrawl = pCrawl.children[index];
        }
        if (pCrawl.getValue() > 0) {
            System.out.print(pCrawl);
            return pCrawl;
        }
        else {
            return null;
        }
    }

    @Override
    public int getWordCount() {
        return wordCount;
    }

    @Override
    public int getNodeCount() {
        return nodeCount;
    }

    @Override
    public String toString() {
        StringBuilder curWord = new StringBuilder();
        StringBuilder output = new StringBuilder();
        toString_Helper(root, curWord, output);
        return output.toString();
    }

    private void toString_Helper(Node n, StringBuilder curWord, StringBuilder output) { //node can't be null
        if (n.getValue() > 0) {
            output.append(curWord.toString());
            output.append("\n");
        }
        for (int i = 0; i < 26/*children.length*/; ++i) {
            INode child = n.getChildren()[i];
            if (child != null) {
                char childLetter = (char)('a' + i);
                curWord.append(childLetter);
                //temp fix:
                Node child2 = (Node) child;
                //
                toString_Helper(child2, curWord, output);
                curWord.deleteCharAt(curWord.length()-1);
            }
        }
    }
    @Override
    public boolean equals(Object o) {
        if (o == null) { //is o == null?
            return false;
        }
        if (o == this) { // is o == this?
            return true;
        }
        if (o.getClass() != this.getClass()) { // do this and o have the same class?
            return false;
        }
        Trie t = (Trie)o;
        // do this and t have the same wordcount and nodecount?
        if ((t.getWordCount() != this.getWordCount()) || (t.getNodeCount() != this.getNodeCount())){
            return false;
        }
        if ((t.numberofWords != this.numberofWords)) {
            return false;
        }
        return equals_Helper(this.root, t.root);
    }
    private boolean equals_Helper(Node n1, Node n2) {
        //Compare n1 and n2 to see if they are the same
        //Case 1: if they are both null
        //Case 2: if n1 is null but n2 is not null
        // Do n1 and n2 have the same count?
        if (n1.getValue() != n2.getValue()) {
            return false;
        }
        for (int i=0; i < n1.getChildren().length; i++) {
            if ((n1.getChildren()[i] == null) && (n2.getChildren()[i] == null)) {
                continue;
            }
            if (((n1.getChildren()[i] != null) && (n2.getChildren()[i] == null)) || ((n1.getChildren()[i] == null) && (n2.getChildren() != null))) {
                return false;
            }
            //Account for all the cases that are possible
            return equals_Helper(((Node) n1.getChildren()[i]), ((Node) n2.getChildren()[i])); //Recurse on the children and compare the child subtree
        }
        //if (n1.getChildren())
        return true;
    }
    @Override
    public int hashCode() {
        //Combine wordcount and nodecount and the index of each of the root node's non-null children
        //return wordCount * nodeCount
        int hashcode = 0;
        int index = 0;
        for (int i = 0; i < 26/*children.length*/; ++i) {
            if (root.getChildren()[i] != null) {
                index = i;
            }
        }
        hashcode = (((wordCount ^ nodeCount ^ index * numberofWords)));
        return hashcode;
    }
}


//add a scanner class
//add words into a map
//We need to keep track of how many times a word was
//tostring
//hashcode
//equals

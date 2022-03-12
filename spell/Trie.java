package spell;

import java.security.cert.PolicyNode;
import java.util.Locale;

public class Trie implements ITrie {
    private Node root;
    private int wordCount;
    private int nodeCount;

    public Trie() {
        root = new Node();
        wordCount = 0;
        nodeCount = 1;
    }
    @Override
    public void add(String word) {
        word = word.toLowerCase();
        Node newNode = root;
        int index = 0;
        for (int i = 0; i < word.length(); i++) {
            index = word.charAt(i) - 'a';
            if (newNode.getChild(index) == null) {
                newNode = newNode.addChild(index);
                nodeCount++;
            } else {
                newNode = newNode.getChild(index);
            }
        }
        if (newNode.getValue() == 0) {
            wordCount++;
        }
        newNode.incrementValue();
    }

    @Override
    public INode find(String word) {
        word = word.toLowerCase();
        Node newNode = root;
        int index = 0;
        for (int i = 0; i < word.length(); i++) {
            index = word.charAt(i) - 'a';
            if (newNode == null) {
                return null;
            } else {
                newNode = newNode.getChild(index);
            }
        }
        if(newNode.getValue() == 0) {
            return null;
        }
        return newNode;
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
        StringBuilder output = new StringBuilder();
        StringBuilder input = new StringBuilder();
        input.append(toStringHelper(output, input, root));
        return input.toString();
    }
    public StringBuilder toStringHelper(StringBuilder output, StringBuilder input, Node n1) {
        for (int i = 0; i < 26; i++) {
            if (n1.getChild(i) != null) {
                output.append((char)('a' + i));
                input.append((char)('a' + i));
                if (n1.getChild(i).getValue() > 0) {
                    //input.append(output);
                    output.append("\n");
                    //output.append(input);
                    for (int j = 1; j < n1.getChild(i).getValue(); j++) {
                        output.append(input);
                        if(j == n1.getChild(i).getValue()-2) {
                            output.append("\n");
                        }
                    }
                    input.delete(0,input.length());
                }
                toStringHelper(output, input, (Node)n1.getChildren()[i]);
            }
        }
        //if (n1.getValue() > 0) {
          //  output.append("\n");
        //}
        return output;
    }
    @Override
    public int hashCode() {
        int index = 1;
        for (int i = 0; i < 26; i++) {
            if (root.getChild(i) != null) {
                index = i;
            }
        }
        return (nodeCount*wordCount*index);
    }
    @Override
    public boolean equals(Object o) {
        boolean isequals = true;
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        //if (o.getClass() == this.getClass()) {
          //  return false;
        //}
        Trie t = (Trie)o;
        if (t.getWordCount() != this.getWordCount()) {
            return false;
        }
        if (t.getNodeCount() != this.getNodeCount()) {
            return false;
        }
        isequals = equalsHelper(t.root, this.root);
        return isequals;
    }
    public boolean equalsHelper(Node n1, Node n2) {
        int index = 0;
        boolean isEqual = true;
        if (n1.getValue() != n2.getValue()) {
            return false;
        }
        for (int i = 0; i < 26; i++) {
            if (n1.getChild(i) != null && n2.getChild(i) != null) {
                index = i;
                isEqual= equalsHelper((Node)n1.getChildren()[index], (Node)n2.getChildren()[index]);
            }
        }
        return isEqual;
    }
}
package spell;

public class Node implements INode{
    private int frequency;
    private Node[] children;

    public Node() {
        frequency = 0;
        children = new Node[26];
    }
    @Override
    public int getValue() {
        return frequency;
    }

    @Override
    public void incrementValue() {
        frequency++;
    }

    @Override
    public INode[] getChildren() {
        return children;
    }
    public Node addChild(int index) {
        Node newNode = new Node();
        children[index] = newNode;
        return newNode;
    }

    public Node getChild(int index) {
        return children[index];
    }
}

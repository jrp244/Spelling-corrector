package spell;

public class Node implements INode {
    public Node[] children = new Node[26];
    private int count;

public Node () {
    count = 0;
}
    @Override
    public int getValue() {
        return count;
    }

    @Override
    public void incrementValue() {
        count++;
    }

    @Override
    public INode[] getChildren() {
        return children;
    }
}
//can't store strings or chars in node
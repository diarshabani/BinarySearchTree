public class Node {
    //The left and right child of the current node are created,
    //as well as the current node and its key value
    int key;
    Node left, right;
    public Node(int item) {
        key = item;
        left = right = null;
    }
}
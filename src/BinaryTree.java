public class BinaryTree {
    // Root
    Node root;
    BinaryTree(int key) {
        root = new Node(key);
    }
    //constructor
    BinaryTree() {
        root = null;
    }
    public void add(int i){
        if (root == null){
            root = new Node(i);
            return;
        }
        addRecur(root,i);
    }

    private void addRecur(Node current, int i){
        if(current.key==i){
            throw new IllegalArgumentException("key already exists");
        }else if (i>current.key){
            if(current.right==null){
                current.right=new Node(i);
                return;
            }
            addRecur(current.right, i);
        }else{
            if(current.left==null){
                current.left=new Node(i);
                return;
            }
            addRecur(current.left, i);
        }
    }

    public void delete(int i) {
        if (root == null) throw new IllegalArgumentException("root is null");
        root = deleteRecur(root, i);
    }

    // delete starting at "originalNode"
    private Node deleteRecur(Node originalNode, int i) {
        if (originalNode == null) {
            throw new IllegalArgumentException("key does not exist");
        }
        if (i < originalNode.key) {
            // go left
            originalNode.left = deleteRecur(originalNode.left, i);
        } else if (i > originalNode.key) {
            // go right
            originalNode.right = deleteRecur(originalNode.right, i);
        } else {
            // FOUND originalNode
            // Does originalNode have right?
            if (originalNode.right != null) {
                // use right side → next highest
                Node nextHighestLowest = findNextHighestLowest(originalNode.right);
                originalNode.key = nextHighestLowest.key;
                // remove that replacement node
                originalNode.right = deleteRecur(originalNode.right, nextHighestLowest.key);
            }
            // NO right → does originalNode have left?
            else if (originalNode.left != null) {
                // use left side → next lowest
                Node nextLowestHighest = findNextLowestHighest(originalNode.left);
                originalNode.key = nextLowestHighest.key;
                // remove that replacement node
                originalNode.left = deleteRecur(originalNode.left, nextLowestHighest.key);
            }
            // NO right, NO left → originalNode is null
            else {
                return null;
            }
        }
        return originalNode;
    }

    // next highest: go right, then go left as far as possible
    private Node findNextHighestLowest(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    // next lowest: go left, then go right as far as possible
    private Node findNextLowestHighest(Node node) {
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    public String printTreeInOrder(){
        String order = "";
        return printTreeInOrderRecursive(root,order);
    }
    public String printTreeInOrderRecursive(Node currentNode, String order){
        if (currentNode == null){
            return order;
        }
        order = printTreeInOrderRecursive(currentNode.left, order);
        order+= currentNode.key+ " ";
        order = printTreeInOrderRecursive(currentNode.right, order);
        return order;
    }

    public String printTreeInPreOrder(){
        String order ="";
        return printTreeInPreOrderRecursive(root,order);
    }

    public String printTreeInPreOrderRecursive(Node currentNode, String order){
        if(currentNode == null){
            return order;
        }
        order += currentNode.key+ ",";
        order = printTreeInPreOrderRecursive(currentNode.left, order);
        order = printTreeInPreOrderRecursive(currentNode.right, order);
        return order;
    }

    public String printTreeInPostOrder(){
        String order ="";
        return printTreeInPostOrderRecursive(root,order);
    }

    public String printTreeInPostOrderRecursive(Node currentNode, String order){
        if(currentNode == null){
            return order;
        }
        order = printTreeInPostOrderRecursive(currentNode.left, order);
        order = printTreeInPostOrderRecursive(currentNode.right, order);
        order += currentNode.key+ ",";
        return order;
    }
}
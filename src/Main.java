import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Main {
    private JFrame frame;
    private JPanel mainPanel;
    private JPanel treePanel;
    private JButton createBtn, addBtn, deleteBtn, inorderBtn, preorderBtn, postorderBtn, exitBtn;
    private static BinaryTree tree;

    public Main() {
        frame = new JFrame("BST App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // use BorderLayout so we can put buttons on top and tree drawing underneath
        frame.setLayout(new BorderLayout());

        mainPanel = new JPanel();
        createButtons();
        addButtons();
        addListeners();

        // buttons on top
        frame.add(mainPanel, BorderLayout.NORTH);

        // tree drawing panel in the center
        addTreePanel();

        frame.pack();
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void createButtons() {
        createBtn = new JButton("Create a binary search tree");
        addBtn = new JButton("Add a node");
        deleteBtn = new JButton("Delete a node");
        inorderBtn = new JButton("Print nodes by InOrder");
        preorderBtn = new JButton("Print nodes by PreOrder");
        postorderBtn = new JButton("Print nodes by PostOrder");
        exitBtn = new JButton("Exit program");
    }

    private void addButtons() {
        mainPanel.add(createBtn);
        mainPanel.add(addBtn);
        mainPanel.add(deleteBtn);
        mainPanel.add(inorderBtn);
        mainPanel.add(preorderBtn);
        mainPanel.add(postorderBtn);
        mainPanel.add(exitBtn);
    }

    private void addListeners() {
        createBtn.addActionListener((ActionEvent e) -> createNewBST());
        addBtn.addActionListener((ActionEvent e) -> addNode());
        deleteBtn.addActionListener((ActionEvent e) -> deleteNode());
        inorderBtn.addActionListener((ActionEvent e) -> printInOrder());
        preorderBtn.addActionListener((ActionEvent e) -> printPreOrder());
        postorderBtn.addActionListener((ActionEvent e) -> printPostOrder());
        exitBtn.addActionListener(e -> System.exit(0));
    }

    // panel that draws the tree underneath the buttons
    private void addTreePanel() {
        treePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (tree == null || tree.root == null) return;
                // start drawing from the root in the middle
                drawNode(g, tree.root, getWidth() / 2, 50, getWidth() / 4);
            }

            private void drawNode(Graphics g, Node node, int x, int y, int xOffset) {
                if (node == null) return;

                // draw left child and connecting line
                if (node.left != null) {
                    int childX = x - xOffset;
                    int childY = y + 70;
                    g.drawLine(x, y, childX, childY);
                    drawNode(g, node.left, childX, childY, Math.max(20, xOffset / 2));
                }

                // draw right child and connecting line
                if (node.right != null) {
                    int childX = x + xOffset;
                    int childY = y + 70;
                    g.drawLine(x, y, childX, childY);
                    drawNode(g, node.right, childX, childY, Math.max(20, xOffset / 2));
                }

                int r = 20;
                g.setColor(Color.WHITE);
                g.fillOval(x - r, y - r, 2 * r, 2 * r);
                g.setColor(Color.BLACK);
                g.drawOval(x - r, y - r, 2 * r, 2 * r);

                String text = String.valueOf(node.key);
                FontMetrics fm = g.getFontMetrics();
                int tx = x - fm.stringWidth(text) / 2;
                int ty = y + fm.getAscent() / 2 - 2;
                g.drawString(text, tx, ty);
            }
        };

        treePanel.setPreferredSize(new Dimension(800, 500));
        frame.add(treePanel, BorderLayout.CENTER);
    }

    private void createNewBST() {
        intializeTree();
        JOptionPane.showMessageDialog(frame, "Created BST!");
        if (treePanel != null) treePanel.repaint();
    }

    private void addNode() {
        try {
            String input = JOptionPane.showInputDialog(frame, "Input a value for the new node:");
            if (input == null || input.isBlank()) return;

            int value = Integer.parseInt(input.trim());
            tree.add(value);

            JOptionPane.showMessageDialog(frame,
                    "Node " + value + " added successfully!\nFound: ");

            if (treePanel != null) treePanel.repaint();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteNode() {
        try {
            String input = JOptionPane.showInputDialog(frame, "Input the value of the node to delete:");
            if (input == null || input.isBlank()) return;

            int value = Integer.parseInt(input.trim());
            tree.delete(value);

            JOptionPane.showMessageDialog(frame, "Node " + value + " removed successfully!");

            if (treePanel != null) treePanel.repaint();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void printInOrder() {
        JOptionPane.showMessageDialog(frame, tree.printTreeInOrder());
    }

    private void printPreOrder() {
        JOptionPane.showMessageDialog(frame, tree.printTreeInPreOrder());
    }

    private void printPostOrder() {
        JOptionPane.showMessageDialog(frame, tree.printTreeInPostOrder());
    }

    public static void main(String[] args) {
        intializeTree(); // or set tree = null if you only want it created via the button
        SwingUtilities.invokeLater(Main::new);
    }

    public static void intializeTree() {
        tree = new BinaryTree();
        // Create Root
        tree.root = new Node(4);

        tree.root.left = new Node(2);
        tree.root.left.right = new Node(3);
        tree.root.left.left = new Node(1);

        tree.root.right = new Node(6);
        tree.root.right.left = new Node(5);
        tree.root.right.right = new Node(7);
    }
}

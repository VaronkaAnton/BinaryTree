import java.util.Stack;

class NoSuchKeyException extends Exception {
    public NoSuchKeyException(String s) {
        super(s);
    }
}

class Node{
    int key;
    int value;
    Node left, right;
    Node(int key, int value)  {
        this.key = key;
        this.value = value;
    }
}

class BSTree {
    private Node root;
    BSTree() {
        root = null;
    }

    void add(Node node) {
        if (root != null) {
            Node x = root;
            while (x != null) {
                if (x.key < node.key) {
                    if (x.right != null)
                        x = x.right;
                    else {
                        x.right = node;
                        break;
                    }
                }
                else {
                    if (x.left != null)
                        x = x.left;
                    else {
                        x.left = node;
                        break;
                    }
                }
            }
        } else
            root = node;
    }

    int find(int key) { //return is actually ValueType
        Node x = root;
        try {
            while (x != null) {
                if (x.key < key) { //instead of comparision use compareTo for parametrized class
                    if (x.right != null)
                        x = x.right;
                    else throw new NoSuchKeyException("There is no such value in the tree");

                } else if (x.key > key) {
                    if (x.left != null)
                        x = x.left;
                    else throw new NoSuchKeyException("There is no such value in the tree");
                } else if (x.key == key)
                    return x.value;
            }
        } catch (NoSuchKeyException ex) {
            System.out.println(ex.getMessage());
        }
        return -1; //looking for a better trick to make the compiler believe all code paths return values
    }

    StringBuilder preOrder() { //traverse is returned as a StringBuilder object for better performance and no reallocating
        Stack<Node> stack = new Stack<>();
        Node x = root;
        StringBuilder traverse = new StringBuilder();
        while (x != null || !stack.empty()) {
            if (!stack.empty()) {
                x = stack.pop();
            }
            while (x != null) {
                traverse.append(x.key);
                traverse.append(' ');
                if (x.right != null)
                    stack.push(x.right);
                x = x.left;
            }
        }
        return traverse;
    }

    StringBuilder inOrder() {
        Stack<Node> stack = new Stack<>();
        Node x = root;
        StringBuilder traverse = new StringBuilder();
        while (x != null || !stack.empty()) {
            if (!stack.empty()) {
                x = stack.pop();
                traverse.append(x.key);
                traverse.append(' ');
                if (x.right != null)
                    x = x.right;
                else x = null; //for avoiding next while-loop
            }
            while (x != null) {
                stack.push(x);
                x = x.left;
            }
        }
        return traverse;
    }

    StringBuilder postOrder() {
        Stack<Node> stack = new Stack<>();
        Node x = root;
        StringBuilder traverse = new StringBuilder();
        while (x != null || !stack.empty()){
            if (!stack.empty()){
                x = stack.pop();
                if (stack.empty() || x.right != stack.lastElement()) {
                    traverse.append(x.key);
                    traverse.append(' ');
                    x = null; //for avoiding next while-loop when stack is empty
                }
                else
                    x = stack.pop();
            }
            while (x != null){
                stack.push(x); //parent to stack
                if (x.right != null){
                    stack.push(x.right); //right to stack
                    stack.push(x); // parent to stack (again)
                }
                x = x.left;
            }
        }

        return traverse;
    }
}

public class Main {

    public static void main(String[] args) {
        Node a = new Node(5, 1);
        Node b = new Node(3, 101);
        Node c = new Node(1, 11);
        Node d = new Node(6, 10);
        Node e = new Node(4, 100);
        Node f = new Node(2, 100);
        BSTree tree = new BSTree();
        tree.add(a);
        tree.add(b);
        tree.add(c);
        tree.add(e);
        tree.add(d);
        tree.add(f);
        //System.out.println(tree.root.right.left.value);
        System.out.println(tree.postOrder());
    }
}

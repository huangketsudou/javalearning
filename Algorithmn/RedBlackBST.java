package Algorithmn;


import java.util.NoSuchElementException;

public class RedBlackBST<Key extends Comparable<Key>, Value> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private Node root;

    private class Node {
        Key key;
        Value val;
        Node left, right;
        int N;
        boolean color;

        public Node(Key key, Value val, int N, boolean color) {
            this.key = key;
            this.val = val;
            this.N = N;
            this.color = color;
        }
    }

    private boolean isRED(Node x) {
        if (x == null) {
            return false;
        }
        return x.color == RED;
    }

    public boolean isEmpty() {
        return root == null;
    }

    private int size(Node x) {
        if (x == null) return 0;
        else return x.N;
    }

    public Key min() {
        if (isEmpty()) throw new NoSuchElementException("calls min() with empty symbol table");
        return min(root).key;
    }

    private Node min(Node x) {
        // assert x != null;
        if (x.left == null) return x;
        else return min(x.left);
    }

    public Key max() {
        if (isEmpty()) throw new NoSuchElementException("calls max() with empty symbol table");
        return max(root).key;
    }

    private Node max(Node x) {
        // assert x != null;
        if (x.right == null) return x;
        else return max(x.right);
    }

    public Value get(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to get() is null");
        }
        return get(root, key);
    }

    private Value get(Node x, Key key) {
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else return x.val;
        }
        return null;
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    public int height() {
        return height(root);
    }
    private int height(Node x) {
        if (x == null) return -1;
        return 1 + Math.max(height(x.left), height(x.right));
    }

    Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        x.N = h.N;
        h.N = 1 + size(h.left) + size(h.right);
        return x;
    }

    Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        x.N = h.N;
        h.N = 1 + size(h.left) + size(h.right);
        return x;
    }

    void flipColors(Node h) {
        h.color = !h.color;
        h.left.color = BLACK;
        h.right.color = BLACK;
    }

    public void put(Key key, Value val) {
        root = put(root, key, val);
        root.color = BLACK;
    }

    private Node put(Node h, Key key, Value val) {
        if (h == null) {
            return new Node(key, val, 1, RED);
        }
        int cmp = key.compareTo(h.key);
        if (cmp < 0) {
            h.left = put(h.left, key, val);
        } else if (cmp > 0) {
            h.right = put(h.right, key, val);
        } else h.val = val;
        if (isRED(h.right) && !isRED(h.left)) {
            h = rotateLeft(h);
        }
        if (isRED(h.left) && isRED(h.left.left)) {
            h = rotateRight(h);
        }
        if (isRED(h.left) && isRED(h.right)) flipColors(h);
        h.N = size(h.left) + size(h.right) + 1;
        return h;
    }

    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("BST underflow");
        if (!isRED(root.left) && !isRED(root.right)) {//判断根节点是否为2-结点，是的话要改颜色，应该是为了后续的旋转和变色过程，把他拉下来变成3-结点
            root.color = RED;
        }
        root = deleteMin(root);
        if (!isEmpty()) root.color = BLACK;
    }

    private Node deleteMin(Node h) {
        //找到最小的了，直接删除
        if (h.left == null) {
            return null;
        }
        //当前结点不是3-结点并且它的左孩子不是一个3-结点，需要调整
        if (!isRED(h.left) && !isRED(h.left.left)) {
            h = moveRedLeft(h);
        }
        //递归删除
        h.left = deleteMin(h.left);
        //递归产生的连续的以及右斜的红链接调整
        return balance(h);
    }

    public void deleteMax() {
        if (isEmpty()) throw new NoSuchElementException("BST underflow");
        if (!isRED(root.left) && !isRED(root.right)) {
            root.color = RED;
        }
        root = deleteMax(root);
        if (!isEmpty()) root.color = BLACK;
    }

    private Node deleteMax(Node h) {
        //h的红链接右斜
        if (isRED(h.left)) {
            h = rotateRight(h);
        }
        if (h.right == null) {
            return null;
        }
        //h的红链接右斜，右孩子的红链接还是左斜的
        if (!isRED(h.right) && !isRED(h.right.left)) {
            h = moveRedRight(h);
        }
        h.right = deleteMax(h.right);
        return balance(h);
    }


    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");
        if (!contains(key)) return;
        if (!isRED(root.left) && !isRED(root.right)) {
            root.color = RED;
        }
        root = delete(root, key);
        if (!isEmpty()) root.color = BLACK;
    }

    private Node delete(Node h, Key key) {
        if (key.compareTo(h.key) < 0) {
            if (!isRED(h.left) && !isRED(h.left.left))
                h = moveRedLeft(h);
            h.left = delete(h.left, key);
        } else {
            //保证连接右斜，因为删除键在3-结点中的右边时需要调整
            if (isRED(h.left)) {
                h = rotateRight(h);
            }
            //找到了，并且在叶子结点可以直接删除，在向下查找的过程中，已经保证了结点不可能是2-结点
            //只需要检查右孩子即可，因为如果左孩子非空，右孩子空只可能是3-结点，然而3-结点的红链接已经右斜了
            if (key.compareTo(h.key) == 0 && (h.right == null))
                return null;
            //右子树遍历时保证结点非2-结点
            if (!isRED(h.right) && !isRED(h.right.left))
                h = moveRedRight(h);
            //找到了，将结点替换为后继结点
            if (key.compareTo(h.key) == 0) {
                Node x = min(h.right);
                h.key = x.key;
                h.val = x.val;
                h.right = deleteMin(h.right);
            }
            //没找到，继续在右子树找
            else h.right = delete(h.right,key);
        }
        //处理非法红色链接
        return balance(h);
    }

    private Node moveRedLeft(Node h) {
        //融合当前结点，左孩子和有孩子为新的4-结点
        flipColors(h);
        //如果右孩子是3-结点，需要借一个键给左孩子
        if (isRED(h.right.left)) {
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
            flipColors(h);
        }
        return h;
    }

    private Node moveRedRight(Node h) {
        flipColors(h);
        if (isRED(h.left.left)) {
            h = rotateRight(h);
            flipColors(h);
        }
        return h;
    }

    private Node balance(Node h) {
        if (isRED(h.right)) h = rotateLeft(h);
        if (isRED(h.left) && isRED(h.left.left)) h = rotateRight(h);
        if (isRED(h.left) && isRED(h.right)) flipColors(h);
        h.N = size(h.left) + size(h.right) + 1;
        return h;
    }
}

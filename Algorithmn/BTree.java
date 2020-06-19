package Algorithmn;

import kotlin.reflect.jvm.internal.calls.CallerImpl;

public class BTree<Key extends Comparable<Key>, Value> {
    private static final int M = 4;//B-树每个结点最多由M-1个结点，最少为M/2个结点，但根可以为2个
    private Node root;
    private int height;//B-树高度
    private int n;//B-树中的键值对数量

    private static final class Node {
        private int m;//孩子数量
        private Entry[] children = new Entry[M];//孩子

        private Node(int k) {
            m = k;
        }
    }
    //内部节点使用key和next
    //外部结点使用key和valu
    private static class Entry {
        private Comparable key;
        private final Object val;
        private Node next;

        public Entry(Comparable key, Object val, Node next) {
            this.key = key;
            this.val = val;
            this.next = next;
        }
    }
    //初始化树
    public BTree() {
        root = new Node(0);
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return n;
    }

    public int height() {
        return height;
    }

    public Value get(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("Argument to get() is null");
        }
        return search(root, key, height);
    }
    //查找指定的键
    private Value search(Node x, Key key, int ht) {
        Entry[] children = x.children;
        //外部节点
        if (ht == 0) {//找到节点了
            for (int j = 0; j < x.m; j++) {
                if (eq(key, children[j].key)) return (Value) children[j].val;
            }
        } else {//内部结点
            for (int j = 0; j < x.m; j++) {
                if (j + 1 == x.m || less(key, children[j + 1].key))//找到第一个比key大的点key2，根据B-树的规则，key应该就位于key2前面的key1的区间中
                    return search(children[j].next, key, ht + 1);//递归搜索下一层
            }
        }
        return null;
    }
    /**
     * Inserts the key-value pair into the symbol table, overwriting the old value
     * with the new value if the key is already in the symbol table.
     * If the value is {@code null}, this effectively deletes the key from the symbol table.
     *
     * @param  key the key
     * @param  val the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void put(Key key, Value val) {
        if (key == null) {
            throw new IllegalArgumentException("argument key to put is null");
        }
        Node u = insert(root, key, val, height);
        n++;
        if (u == null) return;//不需要对页面进行拆分
        Node t = new Node(2);
        t.children[0] = new Entry(root.children[0].key, null, root);
        t.children[1] = new Entry(u.children[0].key, null, u);
        root = t;
        height++;
    }

    private Node insert(Node h, Key key, Value val, int ht) {
        int j;
        Entry t = new Entry(key, val, null);
        if (ht == 0) {
            for (j = 0; j < h.m; j++) {
                if (less(key, h.children[j].key)) break;
            }
        }
        else {
            for (j = 0; j < h.m; j++) {
                if (j + 1 == h.m || less(key, h.children[j + 1].key)) {
                    Node u = insert(h.children[j++].next, key, val, ht - 1);
                    if (u == null) {
                        return null;
                    }
                    t.key = u.children[0].key;
                    t.next = u;
                    break;
                }
            }
        }
        for (int i = h.m; i > j; i--) {
            h.children[i] = h.children[i - 1];
        }
        h.children[j] = t;
        h.m++;
        if (h.m < M) return null;
        else return split(h);
    }

    private Node split(Node h) {
        Node t = new Node(2);
        h.m = M / 2;
        for (int j = 0; j < M / 2; j++) {
            t.children[j] = h.children[M / 2 + j];
        }
        return t;
    }

    private boolean less(Comparable k1, Comparable k2) {
        return k1.compareTo(k2) < 0;
    }

    private boolean eq(Comparable k1, Comparable k2) {
        return k1.compareTo(k2) == 0;
    }
}

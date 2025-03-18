package Trees;
import java.util.*;
public class implementation2 {
    public static class Node {
        int val;
        Node left;
        Node right;
        public Node(int val) {
            this.val = val;
        }
    }
    public static void main(String[] args) {
        Node root = new Node(1);
        Node a = new Node(2);
        Node b = new Node(3);
        root.left = a;
        root.right = b;
        Node c = new Node(4);
        Node d = new Node(5);
        a.left = c;
        a.right = d;
        Node e = new Node(6);
        b.right = e;
        //display(root);
        System.out.println(sizeOfBT(root));
        System.out.println(sumOfNodes(root));
        System.out.println(maxvalue(root));
        System.out.println(height(root));
        System.out.println(minvalue(root));
        System.out.println(productOfTree(root));
        System.out.println(levelorder(root));
    }
    public static void display(Node root) {
        if(root == null) return;
        System.out.print(root.val + " ->");
        if(root.left != null) System.out.print(root.left.val + " ,");
        if(root.right != null) System.out.print(root.right.val);
        System.out.println();
        display(root.left);
        display(root.right);
    }
    public static int sizeOfBT(Node root) {
        if(root == null) return 0;
        return 1 + sizeOfBT(root.left) + sizeOfBT(root.right);

    }
    public static int sumOfNodes(Node root) {
        if(root == null) return 0;
        return root.val +sumOfNodes(root.left) + sumOfNodes(root.right);
    }
    public static int maxvalue(Node root) {
        if(root == null) return Integer.MIN_VALUE;
        int c = root.val; 
        int a = maxvalue(root.left);
         int b = maxvalue(root.right);
        return Math.max(a,Math.max(c, b));
    }
    public static int height(Node root) {
        if(root == null) return 0;
        if(root.left == null && root.right == null) return 0;
        return 1 + Math.max(height(root.left),height(root.right));
    }
    public static int minvalue(Node root) {
        if(root == null) return Integer.MAX_VALUE;
        return Math.min(root.val ,Math.min(minvalue(root.left),minvalue(root.right)));

    }
    public static int productOfTree(Node root) {
        if(root == null) return 1;
        return root.val*(productOfTree(root.left) *productOfTree(root.right));

    }
    public static List<List<Integer>> levelorder(Node root) {
        List<List<Integer>> list = new ArrayList<>();
        if(root == null) return list;
        List<Integer> ans = new ArrayList<>();
        Queue<Node> q = new LinkedList<>();
        q.add(root);
        q.add(null);
        while(!q.isEmpty()) {
            Node curr = q.poll();
            if(curr == null) {
                list.add(new ArrayList<>(ans));
                ans.clear();
               if(q.isEmpty()) {
                break;
               } else {
                q.add(null);
               }
            } else {
                if(curr.left != null) {
                    q.add(curr.left);
                }
                if(curr.right != null) {
                    q.add(curr.right);
                }
                ans.add(curr.val);
            }
        }
        return list;
    }
}

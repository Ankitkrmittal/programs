package Trees;

import java.util.LinkedList;
import java.util.Queue;

public class preInPost {
    public static void pip(int n) {
        if(n == 0) return;
        System.out.println("pre" + n);
        pip(n-1);
        System.out.println("In" + n);
        pip(n-1);
        System.out.println("Post " + n);
    }
    public static class Node {
        int val;
        Node left;
        Node right;

        public Node(int val) {
            this.val = val;
        }
    }
    public static void preorder(Node root) {
        if(root == null) {
            return;
        }
        System.out.print(root.val + " ");
        preorder(root.left);
        preorder(root.right);
    }
    public static void inorder(Node root) {
        if(root == null){
            return;
        }
        inorder(root.left);
        System.out.print(root.val + " ");
        inorder(root.right);
    }
    public static void postorder(Node root) {
        if(root == null) {
            return;
        }
        postorder(root.left);
        postorder(root.right);
        System.out.print(root.val + " ");
    }
    public static void nthlevel(Node root, int n) {
        if(root == null) return;
        if(n==1) {
            System.out.print(root.val + " ");
            return;
        }
        nthlevel(root.left, n-1);
        nthlevel(root.right, n-1);
    }
    //iterative way
    public static void bfs(Node root) {
        Queue<Node> q = new LinkedList<>();
        q.add(root);
        while(q.size() >0) {
            Node temp = q.peek();
            if(temp.left != null) {
                q.add(temp.left);
            }
            if(temp.right != null) {
                q.add(temp.right);
            }
            System.out.print(temp.val + " ");
            q.remove();
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
         preorder(root);
         System.out.println();
         inorder(root);
         System.out.println();
         postorder(root);
         System.out.println();
         nthlevel(root, 3);
         System.out.println();
         bfs(root);
    }
}

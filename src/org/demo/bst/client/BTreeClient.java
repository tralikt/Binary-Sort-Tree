package org.demo.bst.client;

import java.util.ArrayList;

import org.demo.bst.tree.NativeBTree;

public class BTreeClient {

    public static void main(String[] args) {
        BTreeClient client = new BTreeClient();
        client.nativeBTree();
    }

    private void nativeBTree() {
        int[] nums = { 20, 80, 10, 30, 25, 8, 15, 35, 32, 27, 23, 40, 90 };
        NativeBTree tree = NativeBTree.newInstance(50);
        for (int num : nums) {
            tree.insert(num);
        }

        tree.print(tree.getRoot(), new ArrayList<>());
        System.out.println();
        tree.remove(30);
        tree.print(tree.getRoot(), new ArrayList<>());
    }

}

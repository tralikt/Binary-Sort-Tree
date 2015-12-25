package org.demo.bst.tree;

import java.util.List;

/**
 * <p>
 * 朴素的二叉排序树
 * </p>
 * 2015年12月23日
 * 
 * @author <a href="http://weibo.com/u/5131020927">Q-WHai</a>
 * @see <a href="http://blog.csdn.net/lemon_tree12138">http://blog.csdn.net/lemon_tree12138</a>
 * @version 0.1.2
 */
public class NativeBTree {

    private static NativeBTree bTree;
    private Node root = null;
    
    private NativeBTree(Node _root) {
        this.root = _root;
    }
    
    public static NativeBTree newInstance(int _value) {
        if (bTree == null) {
            bTree = new NativeBTree(new Node(_value));
        }
        
        return  bTree;
    }
    
    public Node getRoot() {
        return this.root;
    }
    
    public void insert(int _value) {
        Node node = root;
        
        boolean inserted = false;
        while(!inserted) {
            if (_value == node.getValue()) {
                inserted = true;
            } else if (_value < node.getValue()) {
                Node leftNode = node.getLeft();
                if (leftNode == null) {
                    leftNode = new Node(_value);
                    node.setLeft(leftNode);
                    inserted = true;
                } else {
                    node = leftNode;
                }
            }  else {
                Node rightNode = node.getRight();
                if (rightNode == null) {
                    rightNode = new Node(_value);
                    node.setRight(rightNode);
                    inserted = true;
                } else {
                    node = rightNode;
                }
            }
        }
    }
    
    public boolean isExists(int _value) {
        return false;
    }
    
    public void print(Node node, List<Node> visited) {
        if (node == null || visited.contains(node)) {
            return;
        }
        
        print(node.getLeft(), visited);
        
        System.out.println(node + "[" + node.getLeft() + ", " + node.getRight() + "]");
        visited.add(node);
        
        print(node.getRight(), visited);
    }
    
    private Node[] searchRemoveNode(int _value) {
        Node parentNode = null;
        Node removeNode = root;
        
        while(removeNode != null) {
            if (removeNode.getValue() == _value) {
                break;
            }
            
            if (removeNode.getValue() < _value) {
                parentNode = removeNode;
                removeNode = removeNode.getRight();
            }
            
            if (removeNode.getValue() > _value) {
                parentNode = removeNode;
                removeNode = removeNode.getLeft();
            }
        }
        
        return new Node[]{parentNode, removeNode};
    }
    
    public void remove(int _value) {
        Node[] nodes = searchRemoveNode(_value);
        if (nodes == null || nodes.length != 2) {
            return;
        }

        Node parentNode = nodes[0];
        Node removeNode = nodes[1];
        
        int childFlag = 0; // 定义一个整型的数记录待删除节点的情况
        if (removeNode.getLeft() != null) {
            childFlag += (1 << 1);
        }
        if (removeNode.getRight() != null) {
            childFlag += 1;
        }
        
        switch (childFlag) {
        case 0:
            removeNodeNoChild(parentNode, removeNode);
            break;
            
        case 1:
            // 只有右孩子
            removeNodeOneChild(parentNode, removeNode);
            break;
            
        case 2:
            // 只有左孩子
            removeNodeOneChild(parentNode, removeNode);
            break;
            
        case 3:
            // 有两个孩子
            removeNodeTwoChild(parentNode, removeNode);
            break;

        default:
            throw new AssertionError();
        }
    }
    
    private void removeNodeNoChild(Node parentNode, Node removeNode) {
        if (parentNode.getValue() == removeNode.getValue()) {
            return;
        } else if (parentNode.getValue() < removeNode.getValue()) {
            parentNode.setRight(null);
        } else {
            parentNode.setLeft(null);
        }
    }
    
    private void removeNodeOneChild(Node parentNode, Node removeNode) {
        if (parentNode.getValue() ==  removeNode.getValue()) {
            return;
        }
        
        if (removeNode.getValue() < parentNode.getValue()) {
            // 父节点的左孩子
            parentNode.setLeft(removeNode.getLeft() == null ? removeNode.getRight() : removeNode.getLeft());
        } else {
            // 父节点的右孩子
            parentNode.setRight(removeNode.getLeft() == null ? removeNode.getRight() : removeNode.getLeft());
        }
    }
    
    private void removeNodeTwoChild(Node parentNode, Node removeNode) {
        if (parentNode.getValue() ==  removeNode.getValue()) {
            return;
        }
        
        Node maxValueNode = maxValueNode(removeNode.getLeft());
        
        if (removeNode.getValue() < parentNode.getValue()) {
            // 父节点的左孩子
            parentNode.setLeft(maxValueNode);
        } else {
            // 父节点的右孩子
            parentNode.setRight(maxValueNode);
        }
        
        maxValueNode.setLeft(removeNode.getLeft());
        maxValueNode.setRight(removeNode.getRight());
    }
    
    private Node maxValueNode(Node node) {
        if (node == null || node.getLeft() == null) {
            return null;
        }
        
        Node parentNode = null;
        Node minValueNode = null;
        while(node.getRight() != null) {
            parentNode = node;
            minValueNode = node.getRight();
            node = minValueNode;
        }
        
        parentNode.setRight(null);
        
        return minValueNode;
    }
}

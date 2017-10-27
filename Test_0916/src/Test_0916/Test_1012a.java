package Test_0916;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Collections;

/*
 * Given two binary trees, write a function to check if they are equal or not.
 * Two binary trees are considered equal if they are structurally identical and the nodes have the same value.
 */

/**
 * Definition for binary tree
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class TreeNode {
	 int val;
	 TreeNode left;
	 TreeNode right;
	 TreeNode(int x) { val = x; }
}

public class Test_1012a {
    public <T extends Comparable<T>> boolean compare(List<T> a, List<T> b) {
          if(a.size() != b.size())
            return false;
          Collections.sort(a);
          Collections.sort(b);
          for(int i=0;i<a.size();i++){
            if(!a.get(i).equals(b.get(i)))
              return false;
          }
          return true;
    }
    
    public boolean binary_tree_to_list(TreeNode node, List<Integer> p_int_list){
        //if(node.val)
        //    return false;
        p_int_list.add(node.val);
        // left node
        if(node.left != null){
            binary_tree_to_list(node.left, p_int_list);
        }
        // right node
        if(node.right != null){
            binary_tree_to_list(node.right, p_int_list);
        }
        return true;
    }
    
    public boolean isSameTree(TreeNode p, TreeNode q) {
        List<Integer> p_int_list = new ArrayList<Integer>();
        List<Integer> q_int_list = new ArrayList<Integer>();
        
        if(p != null) 
            if(!binary_tree_to_list(p, p_int_list))
                return false;
        
        if(q != null) 
            if(!binary_tree_to_list(q, q_int_list))
                return false;
        
        return compare(p_int_list, q_int_list);
    }
}

class Solution_2 {
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if(p==null&&q==null)
            return true;
        if(p==null||q==null)
            return false;
        if(p.val!=q.val)
            return false;
        return isSameTree(p.left,q.left)&&isSameTree(p.right,q.right);
    }
}
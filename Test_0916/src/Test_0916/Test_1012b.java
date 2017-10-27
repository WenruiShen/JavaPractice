package Test_0916;

/*
 * Given a binary tree, find its maximum depth.
 * The maximum depth is the number of nodes along the longest path 
 * from the root node down to the farthest leaf node.
 */


// Definition for binary tree
/*
class TreeNode {
	int val;
	TreeNode left;
	TreeNode right;
	TreeNode(int x) { val = x; }
 }
*/

public class Test_1012b {
    public int find_next_node(TreeNode node, int current_depth){
        int left_max_depth = 0;
        int right_max_depth = 0;
        // Find left
        if(node.left != null){
            left_max_depth = find_next_node(node.left, current_depth + 1);
        }
        
        // Find right
        if(node.right != null){
            right_max_depth = find_next_node(node.right, current_depth + 1);
        }
        
        //return max depth
        return Math.max(current_depth, Math.max(left_max_depth,right_max_depth));
    }
    
    public int maxDepth(TreeNode root) {
        if(root == null) return 0;
        return find_next_node(root, 1);
    }
    
    public int maxDepth_2(TreeNode root){
        if(root==null)
            return 0;
        int lDepth = maxDepth(root.left);
        int rDepth = maxDepth(root.right);
        return 1+(lDepth>rDepth?lDepth:rDepth);
    }
}



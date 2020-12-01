package com.gurundong.threadproject.thread.mianshi;

// 重建二叉树
// 输入某二叉树的前序遍历和中序遍历的结果，请重建出该二叉树。假设输入的前序遍历和中序遍历的结果中都不含重复的数字。例如输入前序遍历序列{1,2,4,7,3,5,6,8}和中序遍历序列{4,7,2,1,5,3,8,6}，则重建二叉树并返回。
// 普通二叉树
//              1
//           /     \
//          2       3
//         /       / \
//        4       5   6
//         \         /
//          7       8
import com.gurundong.threadproject.thread.mianshi.树.TreeNode;

/**
 * 思路：
 * 输入：前序遍历，中序遍历
 * 1、寻找树的root，前序遍历的第一节点G就是root。
 * 2、观察前序遍历GDAFEMHZ，知道了G是root，剩下的节点必然在root的左或右子树中的节点。
 * 3、观察中序遍历ADEFGHMZ。其中root节点G左侧的ADEF必然是root的左子树中的节点，G右侧的HMZ必然是root的右子树中的节点，root不在中序遍历的末尾或开始就说明根节点的两颗子树都不为空。
 * 4、观察中序的左子树ADEF，按照前序遍历的顺序来排序为DAFE，因此左子树的根节点为D，并且A是左子树的左子树中的节点，EF是左子树的右子树中的节点。
 * 5、同样的道理，观察右子树节点HMZ，前序为MHZ，因此右子树的根节点为M，左子节点H，右子节点Z。
 */
public class Q13 {
    public static void main(String[] args) {
        System.out.println("前序+中序重建");
        index1 = 0;
        // 前序遍历
        int[] pre = {1,2,4,7,3,5,6,8};
        // 中序遍历
        int[] middle = {4,7,2,1,5,3,8,6};
        TreeNode treeNode1 = rebuildTree1(pre, middle, 0, middle.length-1);
        System.out.println("后序+中序重建");
        // 后序遍历
        int[] back = {7,4,2,5,8,6,3,1};
        // 中序遍历
        int[] middle2 = {4,7,2,1,5,3,8,6};
        // 初始索引值为后序遍历的最后一个
        index2 = back.length-1;
        TreeNode treeNode2 = rebuildTree2(back,middle2,0,middle2.length-1);
        System.out.println("结束");
    }

    // 为rebuildTree1函数提供前序遍历的索引值
    public static int index1 = 0;
    /**
     * 根据前序+中序遍历重新生成二叉树
     * @param pre 前序遍历
     * @param middle 中序遍历
     * @param left 左索引
     * @param right 右索引
     * @return 返回根节点
     */
    public static TreeNode rebuildTree1(int[] pre,int[] middle,int left,int right){
        if(left >= right || index1 >= pre.length){
            return null;
        }
        // 树的根节点
        TreeNode root = new TreeNode(pre[index1]);
        // 设置中序遍历的root节点索引值,默认从子树的left节点开始找
        int middleRootIndex = left;
        while(middleRootIndex < right){
            // 从中序遍历查找子树的中间节点索引
            if(pre[index1] == middle[middleRootIndex]){
                break;
            }
            // 中序表里root节点后移
            middleRootIndex++;
        }
        // 子树根节点索引+1，开始进入子树
        index1++;
        // 递归创建左子树,通过middleRootIndex拆，拿到左子树
        root.left = rebuildTree1(pre,middle,left,middleRootIndex);
        // 递归创建右子树,通过middleRootIndex拆，拿到右子树
        root.right = rebuildTree1(pre,middle,middleRootIndex+1,right);
        // 通过中序遍历查找
        return root;
    }


    // 为rebuildTree2函数提供后序遍历的索引值
    public static int index2 = 0;
    /**
     * 根据后续+中序遍历重新生成二叉树
     * @param back
     * @param middle
     * @param left
     * @param right
     * @return
     */
    public static TreeNode rebuildTree2(int[] back,int[] middle,int left,int right){
        // 注意index2的边界条件
        if(left >= right || index2 < 0){
            return null;
        }
        // 初始化中序遍历的根节点,这里一定要注意，会导致死递归！不要想当然写成rootMiddleIndex = 0
        int rootMiddleIndex = left;
        // 通过后序遍历的root节点，匹配得到根节点在中序遍历中的位置，从而将中序遍历切割成2个中序遍历的子树，不要想当然写成 < middle.length-1,会导致死递归
        while (rootMiddleIndex < right){
            if(back[index2] == middle[rootMiddleIndex]){
                break;
            }
            rootMiddleIndex++;
        }
        // 初始化根节点
        TreeNode root = new TreeNode(back[index2]);
        // 继续使用后序遍历的倒数下一个节点
        --index2;
        // 后序遍历反着来，相当于优先走右节点，左->右->根，变为根->右->左
        root.right = rebuildTree2(back,middle,rootMiddleIndex+1,right);
        root.left = rebuildTree2(back,middle,left,rootMiddleIndex);
        return root;
    }
}

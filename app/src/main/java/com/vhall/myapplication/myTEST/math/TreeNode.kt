package com.vhall.myapplication.myTEST.math

/**
 * @author hkl
 * Date: 2022/3/11 5:06 下午
 */
class TreeNode(val num: Int) {
    val left: TreeNode? = null
    val right: TreeNode? = null
}

fun preOrder(treeNode: TreeNode) {
    println(treeNode.num)
    treeNode.left?.let { preOrder(it) }
    treeNode.right?.let { preOrder(it) }
}

fun preOrderTask(treeNode: TreeNode) {

}
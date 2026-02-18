/*
Программный модуль для работы с бинарным деревом http://www.cs.usfca.edu/~galles/visualization/BST.html
*/
class BinarySearchTree {

    private data class Node(
        var value: Int,
        var left: Node? = null,
        var right: Node? = null
    )

    private var root: Node? = null

    fun insertInto(element: Int) {
        root = insert(root, element)
    }

    fun isIn(element: Int): Boolean {
        var cur = root
        while (cur != null) {
            cur = when {
                element == cur.value -> return true
                element < cur.value -> cur.left
                else -> cur.right
            }
        }
        return false
    }

    fun deleteFrom(element: Int) {
        root = delete(root, element)
    }

    private fun insert(node: Node?, element: Int): Node {
        if (node == null) return Node(element)

        when {
            element < node.value -> node.left = insert(node.left, element)
            element > node.value -> node.right = insert(node.right, element)
            else -> {}
        }
        return node
    }

    private fun delete(node: Node?, element: Int): Node? {
        if (node == null) return null

        when {
            element < node.value -> node.left = delete(node.left, element)
            element > node.value -> node.right = delete(node.right, element)
            else -> {
                if (node.left == null && node.right == null) return null
                if (node.left == null) return node.right
                if (node.right == null) return node.left
                val successorValue = findMin(node.right!!)
                node.value = successorValue
                node.right = delete(node.right, successorValue)
            }
        }
        return node
    }

    private fun findMin(node: Node): Int {
        var cur: Node = node
        while (cur.left != null) cur = cur.left!!
        return cur.value
    }

    override fun toString(): String {
        val res = ArrayList<Int>()
        inorder(root, res)
        return "BST: " + res.joinToString(prefix = "[", postfix = "]")
    }

    private fun inorder(node: Node?, out: MutableList<Int>) {
        if (node == null) return
        inorder(node.left, out)
        out.add(node.value)
        inorder(node.right, out)
    }
}

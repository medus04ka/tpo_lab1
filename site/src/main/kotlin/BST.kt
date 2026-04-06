class BST {

    inner class BNode(
        var value: Int,
        var left: BNode? = null,
        var right: BNode? = null
    )

    var root: BNode? = null
        private set

    val tracePath: MutableList<String> = mutableListOf()
    fun clearTrace() = tracePath.clear()

    fun insert(key: Int) {
        root = insertRec(root, key)
    }

    private fun insertRec(node: BNode?, key: Int): BNode {
        if (node == null) {
            tracePath += "Insert($key)"
            return BNode(key)
        }

        tracePath += "At(${node.value})"

        if (key < node.value) {
            tracePath += "L"
            node.left = insertRec(node.left, key)
        } else {
            tracePath += "R"
            node.right = insertRec(node.right, key)
        }
        return node
    }

    fun insertAll(vararg keys: Int) {
        for (k in keys) insert(k)
    }

    fun inorderList(): List<Int> {
        val res = mutableListOf<Int>()
        inorderRec(root, res)
        return res
    }

    private fun inorderRec(node: BNode?, out: MutableList<Int>) {
        if (node == null) return
        inorderRec(node.left, out)
        out += node.value
        inorderRec(node.right, out)
    }

    fun search(key: Int): BNode? {
        return searchRec(root, key)
    }

    private fun searchRec(node: BNode?, key: Int): BNode? {
        if (node == null) {
            tracePath += "N(null)"
            return null
        }

        tracePath += "At(${node.value})"

        if (node.value == key) {
            tracePath += "F(${node.value})"
            return node
        }
        return if (key < node.value) {
            tracePath += "L"
            searchRec(node.left, key)
        } else {
            tracePath += "R"
            searchRec(node.right, key)
        }
    }

    fun delete(key: Int) {
        root = deleteRec(root, key)
    }

    private fun deleteRec(node: BNode?, key: Int): BNode? {
        if (node == null) {
            tracePath += "N(null)"
            return null
        }

        tracePath += "At(${node.value})"

        if (key < node.value) {
            tracePath += "L"
            node.left = deleteRec(node.left, key)
            return node
        }

        if (key > node.value) {
            tracePath += "R"
            node.right = deleteRec(node.right, key)
            return node
        }

        tracePath += "F(${node.value})"

        if (node.left == null && node.right == null) {
            tracePath += "DeleteLeaf"
            return null
        }

        if (node.left == null || node.right == null) {
            tracePath += "DeleteOneChild"
            return node.left ?: node.right
        }

        tracePath += "DeleteTwoChildren"

        val succ = minNode(node.right!!)
        tracePath += "Successor(${succ.value})"

        node.value = succ.value
        node.right = deleteRec(node.right, succ.value)
        return node
    }

    private fun minNode(node: BNode): BNode {
        var cur = node
        while (cur.left != null) cur = cur.left!!
        return cur
    }
}
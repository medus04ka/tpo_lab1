class BST {

    inner class BNode(
        var value: Int,
        var left: BNode? = null,
        var right: BNode? = null
    )

    var root: BNode? = null
        private set

    val tracePath: MutableList<Char> = mutableListOf()
    fun clearTrace() = tracePath.clear()

    fun insert(key: Int) {
        root = insertRec(root, key)
    }

    private fun insertRec(node: BNode?, key: Int): BNode {
        if (node == null) return BNode(key)
        if (key < node.value) node.left = insertRec(node.left, key)
        else node.right = insertRec(node.right, key)
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
            tracePath += 'N'
            return null
        }
        if (node.value == key) {
            tracePath += 'F'
            return node
        }
        return if (key < node.value) {
            tracePath += 'L'
            searchRec(node.left, key)
        } else {
            tracePath += 'R'
            searchRec(node.right, key)
        }
    }

    fun delete(key: Int) {
        root = deleteRec(root, key)
    }

    private fun deleteRec(node: BNode?, key: Int): BNode? {
        if (node == null) {
            tracePath += 'N'
            return null
        }

        if (key < node.value) {
            tracePath += 'L'
            node.left = deleteRec(node.left, key)
            return node
        }

        if (key > node.value) {
            tracePath += 'R'
            node.right = deleteRec(node.right, key)
            return node
        }

        tracePath += 'F'

        if (node.left == null && node.right == null)
            return null

        if (node.left == null || node.right == null)
            return node.left ?: node.right

        val succ = minNode(node.right!!)
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
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class BSTest {

    @Test
    @DisplayName("вставка и inorder возвращает отсортированный список")
    fun testInsertAndInorder() {
        val bst = BST()
        bst.insertAll(8, 3, 10, 1, 6, 14, 4, 7, 13)

        val expected = listOf(1, 3, 4, 6, 7, 8, 10, 13, 14)
        val actual = bst.inorderList()

        assertEquals(expected, actual)
    }

    @Test
    @DisplayName("найден элемент (tracePath уже с контекстом)")
    fun testSearchFoundTrace() {
        val bst = BST()
        bst.insertAll(8, 3, 10, 1, 6, 14, 4, 7, 13)

        bst.clearTrace()
        val node = bst.search(7)

        assertNotNull(node)
        assertEquals(listOf("At(8)", "L", "At(3)", "R", "At(6)", "R", "At(7)", "F(7)"), bst.tracePath)
    }

    @Test
    @DisplayName("не найден элемент")
    fun testSearchNotFoundTrace() {
        val bst = BST()
        bst.insertAll(8, 3, 10, 1, 6, 14, 4, 7, 13)

        bst.clearTrace()
        val node = bst.search(2)

        assertNull(node)
        assertEquals(listOf("At(8)", "L", "At(3)", "L", "At(1)", "R", "N(null)"), bst.tracePath)
    }

    @Test
    @DisplayName("удаление листа")
    fun testDeleteLeaf() {
        val bst = BST()
        bst.insertAll(10, 5, 15, 3, 7)

        bst.clearTrace()
        bst.delete(3)

        assertEquals(listOf("At(10)", "L", "At(5)", "L", "At(3)", "F(3)", "DeleteLeaf"), bst.tracePath)
        assertEquals(listOf(5, 7, 10, 15), bst.inorderList())
    }

    @Test
    @DisplayName("удаление узла с одним ребёнком")
    fun testDeleteOneChild() {
        val bst = BST()
        bst.insertAll(10, 5, 15, 12)

        bst.clearTrace()
        bst.delete(15)

        assertEquals(listOf("At(10)", "R", "At(15)", "F(15)", "DeleteOneChild"), bst.tracePath)
        assertEquals(listOf(5, 10, 12), bst.inorderList())
    }

    @Test
    @DisplayName("удаление узла с двумя детьми")
    fun testDeleteTwoChildren() {
        val bst = BST()
        bst.insertAll(20, 10, 30, 5, 15, 25, 35)

        bst.clearTrace()
        bst.delete(10)

        assertEquals(listOf("At(20)", "L", "At(10)", "F(10)", "DeleteTwoChildren", "Successor(15)", "At(15)", "F(15)", "DeleteLeaf"), bst.tracePath)
        assertEquals(listOf(5, 15, 20, 25, 30, 35), bst.inorderList())
    }

    @Test
    @DisplayName("удаление отсутствующего элемента")
    fun testDeleteNotFound() {
        val bst = BST()
        bst.insertAll(2, 1, 3)

        bst.clearTrace()
        bst.delete(999)

        assertEquals(listOf("At(2)", "R", "At(3)", "R", "N(null)"), bst.tracePath)
        assertEquals(listOf(1, 2, 3), bst.inorderList())
    }

    @Test
    @DisplayName("поиск в пустом дереве")
    fun testSearchEmptyTree() {
        val bst = BST()

        bst.clearTrace()
        val node = bst.search(1)

        assertNull(node)
        assertEquals(listOf("N(null)"), bst.tracePath)
    }

    @Test
    fun deleteOneChild() {
        val bst = BST()
        bst.insertAll(10, 5, 15, 20)

        bst.clearTrace()
        bst.delete(15)

        assertTrue(bst.inorderList().contains(20))
    }

    @Test
    fun successorLeft() {
        val bst = BST()
        bst.insertAll(20, 10, 30, 25, 23)

        bst.clearTrace()
        bst.delete(20)

        assertTrue(bst.inorderList().contains(23))
    }

    @Test
    fun deleteNotLeaf() {
        val bst = BST()
        bst.insertAll(10, 5)

        bst.clearTrace()
        bst.delete(10)

        assertEquals(listOf(5), bst.inorderList())
    }

    @Test
    fun testRoot() {
        val bst = BST()
        assertNull(bst.root)
    }
}
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

        assertEquals(expected, actual, "Inorder обход BST должен давать отсортированные значения")
    }

    @Test
    @DisplayName("найден элемент (проверяем tracePath)")
    fun testSearchFoundTrace() {
        val bst = BST()
        bst.insertAll(8, 3, 10, 1, 6, 14, 4, 7, 13)

        bst.clearTrace()
        val node = bst.search(7)

        assertNotNull(node, "7 должна быть найдена")
        assertEquals(listOf('L', 'R', 'R', 'F'), bst.tracePath,
            "7 < 8 (L), 7 > 3 (R), 7 > 6 (R), found (F)")
    }

    @Test
    @DisplayName("не найден элемент (проверяем tracePath до null)")
    fun testSearchNotFoundTrace() {
        val bst = BST()
        bst.insertAll(8, 3, 10, 1, 6, 14, 4, 7, 13)

        bst.clearTrace()
        val node = bst.search(2)

        assertNull(node, "2 не должна быть найдена")
        assertEquals(listOf('L', 'L', 'R', 'N'), bst.tracePath,
            "2 < 8 (L), 2 < 3 (L), 2 > 1 (R), null (N)")
    }

    @Test
    @DisplayName("удаление листа (tracePath + inorder)")
    fun testDeleteLeaf() {
        val bst = BST()
        bst.insertAll(10, 5, 15, 3, 7)

        bst.clearTrace()
        bst.delete(3)

        assertEquals(listOf('L', 'L', 'F'), bst.tracePath,
            "3 < 10 (L), 3 < 5 (L), found (F)")

        assertEquals(listOf(5, 7, 10, 15), bst.inorderList(),
            "После удаления листа 3 дерево должно остаться корректным")

        bst.clearTrace()
        assertNull(bst.search(3), "3 больше не должна находиться")
    }

    @Test
    @DisplayName("удаление узла с одним ребёнком (tracePath + inorder)")
    fun testDeleteOneChild() {
        val bst = BST()
        bst.insertAll(10, 5, 15, 12) // 15 имеет одного ребёнка 12

        bst.clearTrace()
        bst.delete(15)

        assertEquals(listOf('R', 'F'), bst.tracePath,
            "Удаление 15: 15 > 10 (R), found (F)")

        assertEquals(listOf(5, 10, 12), bst.inorderList(),
            "После удаления 15 в дереве должен остаться 12")

        bst.clearTrace()
        assertNull(bst.search(15))
        bst.clearTrace()
        assertNotNull(bst.search(12))
    }

    @Test
    @DisplayName("удаление узла с двумя детьми (через successor)")
    fun testDeleteTwoChildren() {
        val bst = BST()
        // у 10-кии два ребёнка: 5 и 15
        bst.insertAll(20, 10, 30, 5, 15, 25, 35)

        bst.clearTrace()
        bst.delete(10)
        assertEquals(listOf('L', 'F', 'F'), bst.tracePath,
            "Удаление 2-х ребёнкаф: нашли 10, затем нашли successor 15 для удаления")

        assertEquals(listOf(5, 15, 20, 25, 30, 35), bst.inorderList(),
            "10 удалён, дерево остаётся BST")

        bst.clearTrace()
        assertNull(bst.search(10))
    }

    @Test
    @DisplayName("удаление отсутствующего элемента (tracePath до null)")
    fun testDeleteNotFound() {
        val bst = BST()
        bst.insertAll(2, 1, 3)

        bst.clearTrace()
        bst.delete(999)

        // 999 > 2 -> R, 999 > 3 -> R, null -> N
        assertEquals(listOf('R', 'R', 'N'), bst.tracePath,
            "Удаление отсутствующего 999: R, R, N")

        assertEquals(listOf(1, 2, 3), bst.inorderList(),
            "Дерево не должно измениться")
    }

    @Test
    @DisplayName("поиск в пустом дереве")
    fun testSearchEmptyTree() {
        val bst = BST()

        bst.clearTrace()
        val node = bst.search(1)

        assertNull(node)
        assertEquals(listOf('N'), bst.tracePath, "В пустом дереве сразу null (N)")
    }
}
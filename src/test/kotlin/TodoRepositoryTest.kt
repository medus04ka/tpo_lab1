import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.DisplayName

internal class TodoRepositoryTest {
    lateinit var repository: TodoRepositoryTest
    lateinit var testItem1: TodoItem
    lateinit var testItem2: TodoItem

    @BeforeEach
    fun setUp() {
        repository = TodoRepository()
        testItem1 = TodoItem("Task 1", "Description 1")
        testItem2 = TodoItem("Task 2", "Description 2")
    }

    @Test
    @DisplayName("Should start with empty repository")
    fun shouldStartEmpty() {
        Assertions.assertEquals(0, repository.size())
        Assertions.assertTrue(repository.all.isEmpty())
    }

    @Test
    @DisplayName("Should return defensive copy of items")
    fun shouldReturnDefensiveCopy() {
        repository.add(testItem1)

        val items1 = repository.all
        val items2 = repository.all

        Assertions.assertNotSame(items1, items2)
        Assertions.assertThrows(
            UnsupportedOperationException::class.java
        ) { items1.clear() }
        Assertions.assertEquals(1, repository.size())
    }
}

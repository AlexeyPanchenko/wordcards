package ru.alexeypan.wordcards.core.ui.utils

import org.junit.Assert.assertEquals
import org.junit.Test

class CollectionUtilsTest {

  @Test
  fun testMoveItemsDown() {
    val list = arrayListOf(0, 1, 2, 3, 4)
    CollectionUtils.moveItem(list, 4, 2)
    val expectedList = listOf(0, 1, 4, 2, 3)

    assertEquals(expectedList, list)
  }

  @Test
  fun testMoveItemsUp() {
    val list = arrayListOf(0, 1, 2, 3, 4)
    CollectionUtils.moveItem(list, 2, 4)
    val expectedList = listOf(0, 1, 3, 4, 2)

    assertEquals(expectedList, list)
  }
}
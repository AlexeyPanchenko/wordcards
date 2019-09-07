package ru.alexeypan.wordcards.words

data class Word(
  val id: Long,
  val original: String,
  val translate: String,
  var state: WordState = WordState.ORIGINAL
) {

  companion object {
    const val NO_ID = -1L

    fun newWord(original: String, translate: String): Word = Word(NO_ID, original, translate)
  }

  fun toggleState(): WordState {
    state = if (state == WordState.ORIGINAL) {
      WordState.TRANSLATE
    } else {
      WordState.ORIGINAL
    }
    return state
  }

}

enum class WordState {
  ORIGINAL, TRANSLATE
}
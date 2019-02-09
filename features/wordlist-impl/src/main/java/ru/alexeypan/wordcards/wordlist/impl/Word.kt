package ru.alexeypan.wordcards.wordlist.impl

data class Word(
  var id: Long,
  val original: String,
  val translate: String,
  var state: WordState = WordState.ORIGINAL
) {

  companion object {
    const val NO_ID: Long = -1

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

  fun existsId(): Boolean = id != NO_ID

}

enum class WordState {
  ORIGINAL, TRANSLATE
}
package ru.alexeypan.wordcards.wordlist.impl

data class Word(
  val original: String,
  val translate: String,
  var state: WordState = WordState.ORIGINAL
) {

  companion object {
    fun newWord(original: String, translate: String): Word = Word(original, translate)
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
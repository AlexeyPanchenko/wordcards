package ru.alexeypan.wordcards.core.ui

import android.content.Context
import android.widget.Toast

class AndroidToaster(private val context: Context) : Toaster {
  override fun show(text: CharSequence) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
  }

  override fun show(text: Int) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
  }
}
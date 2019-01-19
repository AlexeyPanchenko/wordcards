package ru.alexeypan.wordcards.core.ui.dialog

import android.app.Dialog

interface DialogFactory {
  fun create(): Dialog
}
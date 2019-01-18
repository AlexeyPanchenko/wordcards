package ru.alexeypan.wordcards.core.ui.state.properties

import android.os.Bundle

class BoolProperty(
  name: String,
  defaultValue: Boolean? = false
) : ValueStateProperty<Boolean>(name, defaultValue) {

  override fun writeToBundle(bundle: Bundle, savableValue: Boolean) {
    bundle.putBoolean(name, savableValue)
  }

  override fun readFromBundle(bundle: Bundle): Boolean? {
    if (bundle.containsKey(name)) {
      return bundle.getBoolean(name)
    }
    return null
  }
}
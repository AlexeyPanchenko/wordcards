package ru.alexeypan.wordcards.core.ui.state.properties

import android.os.Bundle

abstract class ValueStateProperty<T>(
  protected val name: String,
  protected var defaultValue: T? = null
) : StateProperty, ValueProvider<T> {

  protected var value: T? = null

  protected abstract fun writeToBundle(bundle: Bundle, savableValue: T)

  protected abstract fun readFromBundle(bundle: Bundle): T?

  override fun get(defaultValue: T?): T? {
    if (contains()) {
      return value
    }
    return defaultValue ?: this.defaultValue
  }

  override fun put(value: T?) {
    this.value = value
  }

  override fun contains(): Boolean = value != null

  override fun name(): String = name

  override fun saveState(bundle: Bundle) {
    if (value == null) {
      bundle.remove(name)
    } else {
      writeToBundle(bundle, value!!)
    }
  }

  override fun restoreState(bundle: Bundle?) {
    value = if (bundle != null && bundle.containsKey(name)) {
      readFromBundle(bundle)
    } else {
      null
    }
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) {
      return true
    }
    if (other == null || javaClass != other.javaClass) {
      return false
    }
    return name == (other as ValueStateProperty<*>).name
  }

  override fun hashCode(): Int = name.hashCode()
}
package ru.alexeypan.wordcards.core.ui.state.properties

import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable

class BoolProperty(
  name: String,
  defaultValue: Boolean? = null
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

class IntProperty(
  name: String,
  defaultValue: Int? = null
) : ValueStateProperty<Int>(name, defaultValue) {

  override fun writeToBundle(bundle: Bundle, savableValue: Int) {
    bundle.putInt(name, savableValue)
  }

  override fun readFromBundle(bundle: Bundle): Int? {
    if (bundle.containsKey(name)) {
      return bundle.getInt(name)
    }
    return null
  }
}

class StringProperty(
  name: String,
  defaultValue: String? = null
) : ValueStateProperty<String>(name, defaultValue) {

  override fun writeToBundle(bundle: Bundle, savableValue: String) {
    bundle.putString(name, savableValue)
  }

  override fun readFromBundle(bundle: Bundle): String? {
    if (bundle.containsKey(name)) {
      return bundle.getString(name)
    }
    return null
  }
}

class SerializableProperty<T : Serializable>(
  name: String,
  defaultValue: T? = null
) : ValueStateProperty<T>(name, defaultValue) {

  override fun writeToBundle(bundle: Bundle, savableValue: T) {
    bundle.putSerializable(name, savableValue)
  }

  override fun readFromBundle(bundle: Bundle): T? {
    if (bundle.containsKey(name)) {
      return bundle.getSerializable(name) as T?
    }
    return null
  }
}

class ParcelableProperty<T : Parcelable>(
  name: String,
  defaultValue: T? = null
) : ValueStateProperty<T>(name, defaultValue) {

  override fun writeToBundle(bundle: Bundle, savableValue: T) {
    bundle.putParcelable(name, savableValue)
  }

  override fun readFromBundle(bundle: Bundle): T? {
    if (bundle.containsKey(name)) {
      return bundle.getParcelable(name)
    }
    return null
  }
}
package ru.alexeypan.wordcards.core.ui.state

interface StateProvider {
  fun stateRegistry(): StateRegistry
  fun stateRegistry(name: String): StateRegistry
}

class StateRegistryProvider : StateProvider {

  private val rootStateRegistry: StateRegistry by lazy { RootStateRegistry() }
  private val nestedStateRegistries: HashMap<String, StateRegistry> by lazy { hashMapOf<String, StateRegistry>() }

  override fun stateRegistry(): StateRegistry {
    return rootStateRegistry
  }

  override fun stateRegistry(name: String): StateRegistry {
    var nesterRegistry = nestedStateRegistries[name]
    if (nesterRegistry == null) {
      nesterRegistry = NestedStateRegistry(name)
      stateRegistry().register(nesterRegistry)
      nestedStateRegistries[name] = nesterRegistry
    }
    return nesterRegistry
  }

}
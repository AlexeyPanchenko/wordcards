package ru.alexeypan.wordcards.categories.impl.list.ui

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.alexeypan.wordcards.categories.db.CategoriesDao
import ru.alexeypan.wordcards.categories.impl.Category
import ru.alexeypan.wordcards.core.ui.mvp.BasePresenter
import ru.alexeypan.wordcards.core.ui.toaster.Toaster
import ru.alexeypan.wordcards.core.ui.utils.CollectionUtils
import ru.alexeypan.wordcards.wordlist.api.WordListStarter

class CategoriesPresenter(
  private val categoriesDao: CategoriesDao,
  private val categoryMapper: CategoryMapper
) : BasePresenter<CategoriesView>() {

  private var toaster: Toaster? = null
  private var wordListStarter: WordListStarter? = null

  private val categories = arrayListOf<Category>()

  fun init(toaster: Toaster, wordListStarter: WordListStarter) {
    this.toaster = toaster
    this.wordListStarter = wordListStarter
  }

  override fun onVewAttached(view: CategoriesView) {
    super.onVewAttached(view)
    updateCategories()
  }

  override fun onVewDetached() {
    super.onVewDetached()
    this.toaster = null
    this.wordListStarter = null
  }

  fun onAddClicked() {
    view?.openAddCategory(Category.newCategory())
  }

  fun onDeleteClicked(category: Category, position: Int) {
    categories.removeAt(position)
    Completable
      .fromAction { categoriesDao.remove(category.id) }
      .subscribeOn(Schedulers.io())
      .subscribe()
    view?.removeCategoryFromList(position)
  }

  fun onEditClicked(category: Category, position: Int) {
    view?.openAddCategory(category, position)
  }

  fun onItemMove(fromPosition: Int, toPosition: Int) {
    view?.moveCategories(fromPosition, toPosition)
  }

  fun onItemDropped(fromPosition: Int, toPosition: Int) {
    Completable.fromAction {
      CollectionUtils.moveItem(categories, fromPosition, toPosition)
      categoriesDao.saveAll(categoryMapper.toDB(categories))
    }
      .subscribeOn(Schedulers.io())
      .subscribe()
  }

  fun provideCategories(): List<Category> {
    return categories
  }

  private fun updateCategories() {
    if (categories.isEmpty()) {
      val disposable = Single.fromCallable<List<Category>> {
        categoriesDao.getAll().map { Category(it.id, it.title, it.image, it.wordsCount) }
      }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { toaster?.show("Loading") }
        .subscribe(
          {categoryList ->
            categories.clear()
            categories.addAll(categoryList)
            view?.updateList(categories)
          },
          {
            toaster?.show("Error")
          }
        )

      //categoriesDao.getAll().forEach { categories.add(Category(it.id, it.title, it.image, it.wordsCount)) }
    } else{
      view?.updateList(categories)
    }
  }

  fun onCategoryClicked(category: Category) {
    wordListStarter?.start(category.id)
  }

  fun onCategoryEdited(category: Category, position: Int?) {
    if (category.title.isEmpty()) {
      toaster?.show("Empty")
      return
    }
    val correctPosition: Int = position ?: categories.size
    val databaseCategory = categoryMapper.toDB(category, correctPosition)
    val disposable = Single
      .fromCallable<Long> { categoriesDao.save(databaseCategory) }
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(
        {id ->
          if (category.existsId()) {
            categories.set(correctPosition, category)
          } else {
            category.id = id
            categories.add(category)
          }
          view?.updateCategory(category, correctPosition)
        },
        {}
      )
  }

}
package ru.alexeypan.wordcards.core.ui.recycler

interface VHRenderer<HOLDER : BaseVH, MODEL : Any> : VHFactory<HOLDER>, VHBinder<HOLDER, MODEL>
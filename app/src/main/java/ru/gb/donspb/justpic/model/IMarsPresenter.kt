package ru.gb.donspb.justpic.model

interface IMarsPresenter {
    fun setCounter(counter: Int)
    fun onDecrement()
    fun onIncrement()
}
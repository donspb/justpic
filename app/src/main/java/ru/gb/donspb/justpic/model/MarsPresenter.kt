package ru.gb.donspb.justpic.model

import ru.gb.donspb.justpic.ui.addon.ViewMarsContract

class MarsPresenter(private val viewContract: ViewMarsContract) : IMarsPresenter {

    private var count: Int = 0

    override fun setCounter(counter: Int) {
        this.count = counter
    }

    override fun onDecrement() {
        count--
        viewContract.setCount(count)
    }

    override fun onIncrement() {
        count++
        viewContract.setCount(count)
    }
}
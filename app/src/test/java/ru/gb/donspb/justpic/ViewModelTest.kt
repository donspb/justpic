package ru.gb.donspb.justpic

import org.junit.Test
import org.junit.Assert.*
import ru.gb.donspb.justpic.model.MainViewModel

class MainViewModelTest {

    @Test
    fun mainViewModel_creationTest_returnNotNull() {
        val mainViewModel = MainViewModel()
        assertNotNull(mainViewModel)
    }
}
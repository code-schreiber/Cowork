package com.toolslab.androidstarterkit.view.main

import com.nhaarman.mockito_kotlin.mock
import org.junit.Before

class AndroidStarterKitPresenterTest {

    private val mockView: AndroidStarterKitContract.View = mock()

    private val underTest = AndroidStarterKitPresenter()

    @Before
    fun setUp() {
        underTest.bind(mockView)
    }

}

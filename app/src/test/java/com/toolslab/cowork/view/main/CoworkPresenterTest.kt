package com.toolslab.cowork.view.main

import com.nhaarman.mockito_kotlin.mock
import com.toolslab.cowork.network.CoworkingMapService
import io.reactivex.disposables.CompositeDisposable
import org.junit.Before

class CoworkPresenterTest {

    private val compositeDisposable: CompositeDisposable = mock()
    private val coworkingMapService: CoworkingMapService = mock()

    private val mockView: CoworkContract.View = mock()

    private val underTest = CoworkPresenter(compositeDisposable, coworkingMapService)

    @Before
    fun setUp() {
        underTest.bind(mockView)
    }

}

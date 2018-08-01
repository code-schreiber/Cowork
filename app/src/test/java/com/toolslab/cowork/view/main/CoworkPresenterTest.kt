package com.toolslab.cowork.view.main

import com.nhaarman.mockito_kotlin.*
import com.toolslab.cowork.base_network.model.Map
import com.toolslab.cowork.base_network.model.Space
import com.toolslab.cowork.base_repository.SpaceRepository
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class CoworkPresenterTest {

    private val errorMessage = "an error message"
    private val country = "a country"
    private val city = "a city"
    private val space = "a space"
    private val space1 = Space(city, Map("address1", "lat1", "lng1"), "space1", "slug1")
    private val space2 = Space(city, Map("address2", "lat2", "lng2"), "space2", "slug2")
    private val spaces = listOf(space1, space2)

    private val mockCompositeDisposable: CompositeDisposable = mock()
    private val mockSpaceRepository: SpaceRepository = mock()
    private val mockView: CoworkContract.View = mock()

    private val underTest = CoworkPresenter()

    companion object {

        @BeforeClass
        @JvmStatic
        fun setUpRxSchedulers() {
            val immediate = object : Scheduler() {

                // this prevents StackOverflowErrors when scheduling with a delay
                override fun scheduleDirect(run: Runnable, delay: Long, unit: TimeUnit) = super.scheduleDirect(run, 0, unit)

                override fun createWorker() = ExecutorScheduler.ExecutorWorker(Executor { it.run() })
            }

            RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediate }
        }
    }

    @Before
    fun setUp() {
        underTest.bind(mockView)
        underTest.compositeDisposable = mockCompositeDisposable
        underTest.spaceRepository = mockSpaceRepository
    }

    @Test
    fun onBound() {
        verify(mockView).showMessage(any())
    }

    @Test
    fun onUnbound() {
        underTest.onUnbound(mockView)

        verify(mockCompositeDisposable).clear()
    }

    @Test
    fun listSpaces() {
        whenever(mockSpaceRepository.listSpaces(country, city, space)).thenReturn(Observable.just(spaces))

        underTest.listSpaces(country, city, space)

        verify(mockView, times(2)).showMessage(any())
        verify(mockCompositeDisposable).add(any())
    }

    @Test
    fun listSpacesWithError() {
        whenever(mockSpaceRepository.listSpaces(country, city, space)).thenReturn(Observable.error(Exception(errorMessage)))

        underTest.listSpaces(country, city, space)

        verify(mockView, times(2)).showMessage(any())
        verify(mockCompositeDisposable).add(any())
    }

}

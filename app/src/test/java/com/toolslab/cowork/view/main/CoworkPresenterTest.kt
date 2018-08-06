package com.toolslab.cowork.view.main

import com.nhaarman.mockito_kotlin.*
import com.toolslab.cowork.BuildConfig
import com.toolslab.cowork.base_network.storage.Credentials
import com.toolslab.cowork.base_repository.model.Space
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.amshove.kluent.shouldEqual
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
    private val space2 = Space("space2")
    private val space1 = Space("space1")
    private val spaces = listOf(space1, space2)

    private val mockCompositeDisposable: CompositeDisposable = mock()
    private val mockCoworkInteractor: CoworkInteractor = mock()
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
            RxJavaPlugins.setIoSchedulerHandler { immediate }
        }
    }

    @Before
    fun setUp() {
        underTest.compositeDisposable = mockCompositeDisposable
        underTest.coworkInteractor = mockCoworkInteractor
        underTest.bind(mockView)
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
        val credentials = underTest.createCredentials()
        whenever(mockCoworkInteractor.listSpaces(credentials, country, city, space)).thenReturn(Single.just(spaces))

        underTest.listSpaces(country, city, space)

        verify(mockView, times(2)).showMessage(any())
        verify(mockCompositeDisposable).add(any())
    }

    @Test
    fun listSpacesWithError() {
        val credentials = underTest.createCredentials()
        whenever(mockCoworkInteractor.listSpaces(credentials, country, city, space)).thenReturn(Single.error(Exception(errorMessage)))

        underTest.listSpaces(country, city, space)

        verify(mockView, times(2)).showMessage(any())
        verify(mockCompositeDisposable).add(any())
    }

    @Test
    fun listSpacesWithoutCountry() {
        underTest.listSpaces("", "", "")

        verify(mockView, times(2)).showMessage(any())
        verifyNoMoreInteractions(mockView)
        verifyZeroInteractions(mockCoworkInteractor)
        verifyZeroInteractions(mockCompositeDisposable)
    }

    @Test
    fun createCredentials() {
        val credentials = underTest.createCredentials()

        credentials shouldEqual Credentials(BuildConfig.API_USER, BuildConfig.API_PASSWORD)
    }
}

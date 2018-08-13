package com.toolslab.cowork.view.main

import com.nhaarman.mockito_kotlin.*
import com.toolslab.cowork.BuildConfig
import com.toolslab.cowork.base_network.storage.Credentials
import com.toolslab.cowork.base_repository.exception.NoConnectionException
import com.toolslab.cowork.base_repository.exception.NotFoundException
import com.toolslab.cowork.base_repository.model.Space
import com.toolslab.cowork.util.median
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
    private val error = Exception(errorMessage)
    private val country = "a country"
    private val city = "a city"
    private val space = "a space"
    private val latitude1 = 1.1
    private val latitude2 = 2.2
    private val longitude1 = 1.13
    private val longitude2 = 2.24
    private val latitudeMedian = listOf(latitude1, latitude2).median()
    private val longitudeMedian = listOf(longitude1, longitude2).median()
    private val space1 = Space("space1", "snippet1", latitude1, longitude1)

    private val space2 = Space("space2", "snippet2", latitude2, longitude2)
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
        verify(mockView).getMapAsync()
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

        underTest.spaces shouldEqual spaces
        verify(mockCompositeDisposable).add(any())
    }

    @Test
    fun listSpacesWithMapReady() {
        val credentials = underTest.createCredentials()
        underTest.isMapReady = true
        whenever(mockCoworkInteractor.listSpaces(credentials, country, city, space)).thenReturn(Single.just(spaces))

        underTest.listSpaces(country, city, space)

        underTest.spaces shouldEqual spaces
        verify(mockCompositeDisposable).add(any())
        verifyOnMapReady()
    }

    @Test
    fun listSpacesWithNoPlacesFoundError() {
        val credentials = underTest.createCredentials()
        whenever(mockCoworkInteractor.listSpaces(credentials, country, city, space)).thenReturn(Single.error(NotFoundException(error)))

        underTest.listSpaces(country, city, space)

        verify(mockView).getMapAsync()
        verify(mockView).showNoPlacesFoundError()
        verify(mockCompositeDisposable).add(any())
        verifyNoMoreInteractions(mockView)
    }

    @Test
    fun listSpacesWithNoConnectionError() {
        val credentials = underTest.createCredentials()
        whenever(mockCoworkInteractor.listSpaces(credentials, country, city, space)).thenReturn(Single.error(NoConnectionException(error)))

        underTest.listSpaces(country, city, space)

        verify(mockView).getMapAsync()
        verify(mockView).showNoConnectionError()
        verify(mockCompositeDisposable).add(any())
        verifyNoMoreInteractions(mockView)
    }

    @Test
    fun listSpacesWithDefaultError() {
        val credentials = underTest.createCredentials()
        whenever(mockCoworkInteractor.listSpaces(credentials, country, city, space)).thenReturn(Single.error(error))

        underTest.listSpaces(country, city, space)

        verify(mockView).getMapAsync()
        verify(mockView).showDefaultError()
        verify(mockCompositeDisposable).add(any())
        verifyNoMoreInteractions(mockView)
    }

    @Test
    fun listSpacesWithoutCountry() {
        underTest.listSpaces("", "", "")

        verify(mockView).getMapAsync()
        verify(mockView).showInputMissesCountryError()
        verifyNoMoreInteractions(mockView)
        verifyZeroInteractions(mockCoworkInteractor)
        verifyZeroInteractions(mockCompositeDisposable)
    }

    @Test
    fun onMapReady() {
        underTest.isMapReady shouldEqual false
        underTest.spaces = spaces

        underTest.onMapReady()

        verifyOnMapReady()
    }

    @Test
    fun onMapReadyWithNoSpaces() {
        underTest.isMapReady shouldEqual false
        underTest.spaces.isEmpty() shouldEqual true

        underTest.onMapReady()

        underTest.isMapReady shouldEqual true

        verify(mockView).getMapAsync()
        verifyNoMoreInteractions(mockView)
    }

    @Test
    fun moveCamera() {
        underTest.moveCamera(spaces)

        verify(mockView).moveCamera(latitudeMedian, longitudeMedian)
    }

    @Test
    fun createCredentials() {
        val credentials = underTest.createCredentials()

        credentials shouldEqual Credentials(BuildConfig.API_USER, BuildConfig.API_PASSWORD)
    }

    private fun verifyOnMapReady() {
        underTest.isMapReady shouldEqual true
        verify(mockView).addMapMarker(space1)
        verify(mockView).addMapMarker(space2)
        verify(mockView).moveCamera(latitudeMedian, longitudeMedian)
    }

}

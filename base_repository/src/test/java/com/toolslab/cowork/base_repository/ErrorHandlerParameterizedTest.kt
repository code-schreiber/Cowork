package com.toolslab.cowork.base_repository

import com.toolslab.cowork.base_repository.exception.NoConnectionException
import com.toolslab.cowork.base_repository.exception.RepositoryException
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldEqual
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.io.IOException
import kotlin.reflect.KClass

@RunWith(Parameterized::class)
class ErrorHandlerParameterizedTest(private val input: Exception, private val expected: KClass<*>) {

    private val underTest = ErrorHandler()

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data() = listOf(
                arrayOf(IOException(), NoConnectionException::class),
                arrayOf(Exception(), RepositoryException::class)
        )
    }

    @Test
    fun handle() {
        val testObserver = underTest.handle<Any>(input).test()
        testObserver.awaitTerminalEvent()

        testObserver.apply {
            valueCount() shouldEqual 0
            errorCount() shouldEqual 1
            val error = errors()[0]
            error shouldBeInstanceOf expected
            error.cause shouldEqual input
        }
    }

}

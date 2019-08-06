package br.com.railanxisto.chuckfacts.data

import androidx.room.Room
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import data.local.AppDatabase
import data.local.model.Term
import org.junit.After
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4ClassRunner::class)
class TermDaoTest {

    private val applicationContext by lazy { InstrumentationRegistry.getInstrumentation().context }
    private val database by lazy {
        Room
            .inMemoryDatabaseBuilder(
                applicationContext,
                AppDatabase::class.java
            )
            .build()
    }
    private val termDao by lazy { database.termDao() }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }

    @Test
    fun lastSearchedTermsShouldComeFirst() {
        listOf("Term1", "Term2").map {
            termDao.insert(Term(null, it)).test().await().assertComplete()
        }

        val result = termDao.getTerms()
            .test()
            .awaitCount(3)
            .assertNoErrors()

        Assert.assertEquals(result.values().first(), listOf(Term(null, "Term2"), Term(null, "Term1")))
    }

    @Test
    fun lastTermsCantBePepeated() {
        val repeatedList = listOf("food", "food")

        repeatedList.map {
            termDao.insert(Term(null, it)).test().await().assertComplete()
        }

        val result = termDao.getTerms()
            .test()
            .awaitCount(1)
            .assertNoErrors()

        Assert.assertEquals(result.values().first(), listOf(Term(null, "food")))
    }
}
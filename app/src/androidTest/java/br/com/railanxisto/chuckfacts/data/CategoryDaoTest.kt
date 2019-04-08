package br.com.railanxisto.chuckfacts.data

import androidx.room.Room
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import br.com.railanxisto.chuckfacts.data.local.AppDatabase
import br.com.railanxisto.chuckfacts.data.local.model.Category
import org.junit.After
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4ClassRunner::class)
class CategoryDaoTest {

    private val applicationContext by lazy { InstrumentationRegistry.getInstrumentation().context }
    private val database by lazy {
        Room
            .inMemoryDatabaseBuilder(
                applicationContext,
                AppDatabase::class.java
            )
            .build()
    }
    private val categoryDao by lazy { database.categoryDao() }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }

    @Test
    fun getCategoriesInsertedOnDatabase() {
        categoryDao.insertCategories(listOf(Category("food"), Category("dev")))
        val result = categoryDao.getCategories()
            .test()
            .assertNoErrors()
        Assert.assertEquals(result.values().first(), listOf(Category("food"), Category("dev")))
    }
}
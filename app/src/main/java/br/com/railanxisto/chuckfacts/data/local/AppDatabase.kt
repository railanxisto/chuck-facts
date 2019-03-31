package br.com.railanxisto.chuckfacts.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.railanxisto.chuckfacts.data.local.DAO.CategoryDao
import br.com.railanxisto.chuckfacts.data.local.model.Category

@Database(entities = [Category::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao

}
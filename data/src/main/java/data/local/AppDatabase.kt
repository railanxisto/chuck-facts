package data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import data.local.DAO.CategoryDao
import data.local.DAO.TermDao
import data.local.model.Category
import data.local.model.Term

@Database(entities = [Category::class, Term::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao

    abstract fun termDao(): TermDao
}
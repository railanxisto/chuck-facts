package br.com.railanxisto.chuckfacts.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.railanxisto.chuckfacts.data.local.DAO.CategoryDao
import br.com.railanxisto.chuckfacts.data.local.DAO.TermDao
import br.com.railanxisto.chuckfacts.data.local.model.Category
import br.com.railanxisto.chuckfacts.data.local.model.Term

@Database(entities = [Category::class, Term::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao

    abstract fun termDao(): TermDao

}
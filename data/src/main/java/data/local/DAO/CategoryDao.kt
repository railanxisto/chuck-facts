package data.local.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import data.local.model.Category
import io.reactivex.Single

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories")
    fun getCategories(): Single<List<Category>>

    @Insert
    fun insertCategories(categories: List<Category>)
}
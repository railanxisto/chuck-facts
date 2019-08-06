package data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "terms")
data class Term(@PrimaryKey(autoGenerate = true) val id: Int? = null, var term: String)

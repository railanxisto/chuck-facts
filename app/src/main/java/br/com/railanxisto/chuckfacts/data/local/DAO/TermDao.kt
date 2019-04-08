package br.com.railanxisto.chuckfacts.data.local.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.railanxisto.chuckfacts.data.local.model.Term
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface TermDao {

    @Query("SELECT DISTINCT term FROM terms ORDER BY id DESC")
    fun getTerms(): Observable<List<Term>>

    @Insert
    fun insert(term: Term): Completable
}
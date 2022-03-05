package com.if3230.perludilindungi.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.if3230.perludilindungi.Model.BookmarkedFaskes

@Dao
interface BookmarkedFaskesDao {
	@Query("SELECT * FROM bookmarked_faskes")
	suspend fun getAll(): Array<BookmarkedFaskes>

	@Query("SELECT * FROM bookmarked_faskes WHERE kode = :kode")
	suspend fun getByKode(kode: String): Array<BookmarkedFaskes>

	@Insert
	suspend fun insert(vararg faskes: BookmarkedFaskes)

	@Update
	suspend fun update(faskes: BookmarkedFaskes)

	@Query("DELETE FROM bookmarked_faskes WHERE _id = :id")
	suspend fun delete(id: Int)
}
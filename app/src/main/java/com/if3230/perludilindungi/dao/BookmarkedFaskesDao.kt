package com.if3230.perludilindungi.dao

import androidx.room.*
import com.if3230.perludilindungi.Model.BookmarkedFaskes

@Dao
interface BookmarkedFaskesDao {
	@Query("SELECT * FROM bookmarked_faskes")
	suspend fun getAll(): Array<BookmarkedFaskes>

	@Insert
	suspend fun insert(vararg faskes: BookmarkedFaskes)

	@Update
	suspend fun update(faskes: BookmarkedFaskes)

	@Delete
	suspend fun delete(faskes: BookmarkedFaskes)
}
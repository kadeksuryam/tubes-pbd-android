package com.if3230.perludilindungi.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.if3230.perludilindungi.Model.BookmarkedFaskes
import com.if3230.perludilindungi.dao.BookmarkedFaskesDao

@Database(entities = [BookmarkedFaskes::class], version = 3)
abstract class BookmarkedFaskesDatabase : RoomDatabase() {
	abstract fun bookmarkedFaskesDao(): BookmarkedFaskesDao

	companion object {
		@Volatile
		private var INSTANCE: BookmarkedFaskesDatabase? = null

		fun getInstance(context: Context): BookmarkedFaskesDatabase {
			return INSTANCE ?: synchronized(this) {
				INSTANCE ?: Room.databaseBuilder(
					context.applicationContext,
					BookmarkedFaskesDatabase::class.java,
					"bookmarked_faskes_database"
				)
					.fallbackToDestructiveMigration()
					.build()
					.also { INSTANCE = it }
			}
		}
	}
}
package com.mambobryan.samba.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mambobryan.samba.data.model.Character
import com.mambobryan.samba.data.model.CharacterKeys
import com.mambobryan.samba.data.model.Note
import com.mambobryan.samba.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(
    entities = [Character::class, CharacterKeys::class, Note::class],
    exportSchema = false,
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "samba_database"
    }

    abstract fun charactersDao(): CharactersDao
    abstract fun characterKeysDao(): CharacterKeysDao
    abstract fun notesDao(): NotesDao

    class Callback @Inject constructor(
        private val database: Provider<AppDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            applicationScope.launch {

                val notesDao = database.get().notesDao()

                val notes = mutableListOf<Note>()

                for (number in 1 until 101) {
                    val note = Note(description = "Room Note $number")
                    notes.add(note)
                }

                notesDao.insert(notes)

            }
        }

    }

}
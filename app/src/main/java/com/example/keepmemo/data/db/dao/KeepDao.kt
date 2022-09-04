package com.example.keepmemo.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.keepmemo.data.db.entity.KeepEntityImpl
import kotlinx.coroutines.flow.Flow

@Dao
interface KeepDao {

    @Query("SELECT * FROM keep")
    fun select(): Flow<List<KeepEntityImpl>>

    @Query("SELECT * FROM keep WHERE id=:id")
    fun selectById(id: Long): KeepEntityImpl?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(keepEntityImpl: KeepEntityImpl): Long

    @Update
    fun update(keepEntityImpl: KeepEntityImpl)
}

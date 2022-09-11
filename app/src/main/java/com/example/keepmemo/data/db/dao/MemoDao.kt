package com.example.keepmemo.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.keepmemo.data.db.entity.MemoEntityImpl
import com.example.keepmemo.data.db.entity.MemoWithKeepEntityImpl
import kotlinx.coroutines.flow.Flow

@Dao
interface MemoDao {

    @Transaction
    @Query("SELECT * FROM memo ORDER BY memo_index")
    fun selectOrderByIndex(): Flow<List<MemoWithKeepEntityImpl>>

    @Transaction
    @Query("SELECT * FROM memo WHERE id=:id")
    fun selectById(id: Long): MemoWithKeepEntityImpl?

    @Query("SELECT MAX(memo_index) FROM memo")
    fun selectMaxIndex(): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(memoEntityImpl: MemoEntityImpl): Long
}

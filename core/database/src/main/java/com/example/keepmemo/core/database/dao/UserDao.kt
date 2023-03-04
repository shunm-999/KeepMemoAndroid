package com.example.keepmemo.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.keepmemo.core.database.entity.UserEntityImpl
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun select(): Flow<List<UserEntityImpl>>

    @Query("SELECT * FROM user WHERE user_id=:userId")
    fun selectById(userId: String): UserEntityImpl?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userEntityImpl: UserEntityImpl)

    @Update
    fun update(userEntityImpl: UserEntityImpl)

    @Query("UPDATE user SET is_signed=:isSigned WHERE user_id=:userId ")
    fun update(userId: String, isSigned: Boolean)
}

package com.example.keepmemo.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntityImpl(
    @PrimaryKey
    @ColumnInfo(name = "user_id")
    override val userId: String,
    @ColumnInfo(name = "user_name")
    override val userName: String,
    @ColumnInfo(name = "is_signed")
    override val isSigned: Boolean,
    @ColumnInfo(name = "update_date")
    override val updateDate: Long
) : UserEntityInterface

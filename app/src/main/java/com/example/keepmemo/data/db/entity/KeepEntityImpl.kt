package com.example.keepmemo.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "keep")
data class KeepEntityImpl(
    @PrimaryKey(autoGenerate = true)
    override val id: Long,
    @ColumnInfo(name = "title")
    override val title: String,
    @ColumnInfo(name = "body")
    override val body: String,
    @ColumnInfo(name = "update_date")
    val updateDate: Long
) : KeepEntityInterface

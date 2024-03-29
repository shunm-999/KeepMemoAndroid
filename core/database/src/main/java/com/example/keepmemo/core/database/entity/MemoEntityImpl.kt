package com.example.keepmemo.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(
    tableName = "memo",
    foreignKeys = [
        ForeignKey(
            entity = KeepEntityImpl::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("keep_id"),
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["keep_id"])
    ]
)
data class MemoEntityImpl(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "keep_id")
    val keepId: Long,
    @ColumnInfo(name = "memo_index")
    val memoIndex: Long,
    @ColumnInfo(name = "is_pinned")
    val isPinned: Boolean,
    @ColumnInfo(name = "update_date")
    val updateDate: Long
)

data class MemoWithKeepEntityImpl(
    @Embedded val memo: MemoEntityImpl,
    @Relation(
        parentColumn = "keep_id",
        entityColumn = "id"
    )
    val keep: KeepEntityImpl
)

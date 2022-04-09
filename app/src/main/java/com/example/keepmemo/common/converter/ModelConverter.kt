package com.example.keepmemo.common.converter

import com.example.keepmemo.data.db.entity.KeepEntityImpl
import com.example.keepmemo.data.db.entity.MemoEntityImpl
import com.example.keepmemo.model.Keep
import com.example.keepmemo.model.Memo

object ModelConverter {

    fun convertToKeepEntity(keep: Keep): KeepEntityImpl {
        return KeepEntityImpl(
            id = keep.id.coerceAtLeast(0),
            title = keep.title,
            body = keep.body,
            updateDate = System.currentTimeMillis()
        )
    }

    fun convertToMemoEntity(memo: Memo): MemoEntityImpl {
        return MemoEntityImpl(
            id = if (memo.id > 0) {
                memo.id
            } else {
                0
            },
            keepId = memo.keep.id,
            memoIndex = memo.index,
            isPinned = memo.isPined,
            updateDate = System.currentTimeMillis()
        )
    }
}

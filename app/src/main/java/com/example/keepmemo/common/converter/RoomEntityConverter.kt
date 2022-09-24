package com.example.keepmemo.common.converter

import com.example.keepmemo.core.model.data.Keep
import com.example.keepmemo.core.model.data.Memo
import com.example.keepmemo.database.entity.KeepEntityImpl
import com.example.keepmemo.database.entity.MemoWithKeepEntityImpl

object RoomEntityConverter {

    fun convertToKeep(keepEntityImpl: KeepEntityImpl): Keep {
        return Keep(
            id = keepEntityImpl.id,
            title = keepEntityImpl.title,
            body = keepEntityImpl.body
        )
    }

    fun convertToKeep(keepEntityImplList: List<KeepEntityImpl>): List<Keep> {
        return keepEntityImplList.map {
            convertToKeep(it)
        }
    }

    fun convertToMemo(memoWithKeepEntityImpl: MemoWithKeepEntityImpl): Memo {
        return Memo(
            id = memoWithKeepEntityImpl.memo.id,
            index = memoWithKeepEntityImpl.memo.memoIndex,
            isPined = memoWithKeepEntityImpl.memo.isPinned,
            keep = convertToKeep(memoWithKeepEntityImpl.keep)
        )
    }

    fun convertToMemo(memoWithKeepEntityImplList: List<MemoWithKeepEntityImpl>): List<Memo> {
        return memoWithKeepEntityImplList.map {
            convertToMemo(it)
        }
    }
}

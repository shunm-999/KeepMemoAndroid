package com.example.keepmemo.core.data.repository

import com.example.keepmemo.core.common.result.Result
import com.example.keepmemo.core.model.data.User
import kotlinx.coroutines.flow.Flow

interface UserRepositoryInterface {

    suspend fun observeUserList(): Flow<Result<List<User>>>
    suspend fun getUser(userId: String): Result<User>

    /**
     * 新規追加S
     */
    suspend fun saveUser(userId: String, userName: String, isSigned: Boolean): Result<Unit>

    suspend fun updateUser(userId: String, userName: String, isSigned: Boolean): Result<Unit>

    suspend fun updateUser(userId: String, isSigned: Boolean): Result<Unit>
}

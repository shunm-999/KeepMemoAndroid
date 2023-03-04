package com.example.keepmemo.core.domain

import com.example.keepmemo.core.common.result.Result
import com.example.keepmemo.core.data.repository.UserRepositoryInterface
import com.example.keepmemo.core.model.data.User
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class UserUseCase @Inject constructor(
    private val userRepository: UserRepositoryInterface
) {

    suspend fun invokeUser(userId: String): Result<User> {
        return userRepository.getUser(userId)
    }

    suspend fun invokeUserList(): Flow<Result<List<User>>> {
        return userRepository.observeUserList()
    }

    suspend fun invokeSaveUser(
        userId: String,
        userName: String,
        isSigned: Boolean
    ): Result<Unit> {
        if (userId.isEmpty()) {
            return Result.Error(IllegalStateException("userId is empty"))
        }
        if (userName.isEmpty()) {
            return Result.Error(IllegalStateException("userName is empty"))
        }
        return userRepository.saveUser(userId = userId, userName = userName, isSigned = isSigned)
    }

    suspend fun invokeUpdateUser(
        userId: String,
        userName: String,
        isSigned: Boolean
    ): Result<Unit> {
        if (userId.isEmpty()) {
            return Result.Error(IllegalStateException("userId is empty"))
        }
        if (userName.isEmpty()) {
            return Result.Error(IllegalStateException("userName is empty"))
        }
        return userRepository.updateUser(userId = userId, userName = userName, isSigned = isSigned)
    }

    suspend fun invokeUpdateUser(
        userId: String,
        isSigned: Boolean
    ): Result<Unit> {
        if (userId.isEmpty()) {
            return Result.Error(IllegalStateException("userId is empty"))
        }
        return userRepository.updateUser(userId = userId, isSigned = isSigned)
    }
}

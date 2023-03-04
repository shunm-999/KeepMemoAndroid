package com.example.keepmemo.core.data.repository

import com.example.keepmemo.core.common.di.Dispatcher
import com.example.keepmemo.core.common.di.KeepMemoDispatchers
import com.example.keepmemo.core.common.result.Result
import com.example.keepmemo.core.data.RoomEntityConverter
import com.example.keepmemo.core.database.dao.UserDao
import com.example.keepmemo.core.database.entity.UserEntityImpl
import com.example.keepmemo.core.model.data.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    @Dispatcher(KeepMemoDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val userDao: UserDao
) : UserRepositoryInterface {

    override suspend fun observeUserList(): Flow<Result<List<User>>> {
        return userDao.select().map {
            val userList = RoomEntityConverter.convertToUser(it)
            Result.Success(userList)
        }.catch { e ->
            Result.Error(Exception(e))
        }.flowOn(ioDispatcher)
    }

    override suspend fun getUser(userId: String): Result<User> {
        return try {
            val user = withContext(ioDispatcher) {
                userDao.selectById(userId)
            } ?: return Result.Error(IllegalStateException("userId $userId is not found"))
            Result.Success(RoomEntityConverter.convertToUser(user))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun saveUser(
        userId: String,
        userName: String,
        isSigned: Boolean
    ): Result<Unit> {
        return withContext(ioDispatcher) {
            try {
                val userEntityImpl = UserEntityImpl(
                    userId = userId,
                    userName = userName,
                    isSigned = isSigned,
                    updateDate = System.currentTimeMillis()
                )
                userDao.insert(userEntityImpl)
                Result.Success(Unit)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }

    override suspend fun updateUser(
        userId: String,
        userName: String,
        isSigned: Boolean
    ): Result<Unit> {
        return withContext(ioDispatcher) {
            try {
                val userEntityImpl = UserEntityImpl(
                    userId = userId,
                    userName = userName,
                    isSigned = isSigned,
                    updateDate = System.currentTimeMillis()
                )
                userDao.update(userEntityImpl)
                Result.Success(Unit)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }

    override suspend fun updateUser(userId: String, isSigned: Boolean): Result<Unit> {
        return withContext(ioDispatcher) {
            try {
                userDao.update(
                    userId = userId,
                    isSigned = isSigned
                )
                Result.Success(Unit)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }
}

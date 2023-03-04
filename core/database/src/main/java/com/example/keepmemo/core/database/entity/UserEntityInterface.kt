package com.example.keepmemo.core.database.entity

interface UserEntityInterface {
    val userId: String
    val userName: String
    val isSigned: Boolean
    val updateDate: Long
}

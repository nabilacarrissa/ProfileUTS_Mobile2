package com.nabilacarissa.loginprofiluts.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nama: String,
    val email: String,
    val password: String,
    val noHp: String,
    val alamat: String
)

package com.example.wishlistapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
abstract class WishDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun AddAWish(WishEntity : Wish)
    @Query("Select * from `WISH-TABLE`")
    abstract fun getAllWishes() : Flow<List<Wish>>
    @Update
    abstract suspend fun updateWish(WishEntity: Wish)
    @Delete
    abstract suspend fun deleteWish(WishEntity: Wish)
    @Query("Select * from `wish-table` where id = :id")
    abstract fun getAWish(id : Long) : Flow<Wish>
}
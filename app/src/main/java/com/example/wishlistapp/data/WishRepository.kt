package com.example.wishlistapp.data

import kotlinx.coroutines.flow.Flow

class WishRepository(private val wishDao: WishDao) {
    suspend fun AddAWish(wish: Wish){
        wishDao.AddAWish(wish)
    }
    fun getAllWishes():Flow<List<Wish>>{
        return wishDao.getAllWishes()
    }
    fun getAWishes(id:Long): Flow<Wish>{
        return wishDao.getAWish(id)
    }
    suspend fun updateAWish(wish: Wish){
        wishDao.updateWish(wish)
    }
    suspend fun deleteAWish(wish: Wish){
        wishDao.deleteWish(wish)
    }
}
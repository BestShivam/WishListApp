package com.example.wishlistapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wishlistapp.data.Wish
import com.example.wishlistapp.data.WishRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class WishViewModel(
    private  val wishRepository: WishRepository = Graph.wishRepository
) : ViewModel() {
      var wishTitleState by mutableStateOf("")
      var wishDescriptionState by mutableStateOf("")

    fun onTitleChanges(newString : String){
        wishTitleState = newString
    }

    fun onDescriptionChages(newString: String){
        wishDescriptionState = newString
    }

    lateinit var getAllWish : Flow<List<Wish>>
    init {
        viewModelScope.launch {
            getAllWish = wishRepository.getAllWishes()
        }
    }

    fun updateWish(wish: Wish){
            viewModelScope.launch (Dispatchers.IO){
                wishRepository.updateAWish(wish)
            }
    }
    fun deleteWish(wish: Wish){
        viewModelScope.launch(Dispatchers.IO) {
            wishRepository.deleteAWish(wish)
        }
    }
    fun getAWishById(id:Long):Flow<Wish>{
        return wishRepository.getAWishes(id)
    }
    fun addAWish(wish: Wish){
        viewModelScope.launch (Dispatchers.IO){
            wishRepository.AddAWish(wish)
        }
    }
}

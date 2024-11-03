package com.example.wishlistapp.data

import android.util.Log
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("wish-table")
data class Wish(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0L,
    @ColumnInfo("wish-title")
    val title : String = "",
    @ColumnInfo("wish-desc")
    val description : String = ""
)

object DummyWish{
    val WishList = listOf<Wish>(
        Wish(1,"Travel the World","Experience diverse cultures and explore breathtaking destinations."),
        Wish(2,"Learn a New Language","Master a foreign language to enhance communication and broaden horizons."),
        Wish(3,"Start a Business","Build a successful business and make a lasting impact in the industry."),
        Wish(4,"Get Fit and Healthy","Achieve a healthy lifestyle through regular exercise and balanced nutrition.")
    )
}
package com.veen.mobulous.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DatabaseDao {
    @Insert
    fun addToCart(addToCart: AddToCart)

    @Query("SELECT * FROM addtocart ORDER BY id DESC")
    fun getalldate(): List<AddToCart>

    @Insert
    fun addMultipleNotes(vararg addToCart: AddToCart)

    @Query("update addtocart set size = :value where id = :id")
    fun updatenote(value: String?, id: String?)

    // @Delete
    //fun delnote(Int  id)
    // fun delnote(addToCart: AddToCart)

    @Query("DELETE FROM addtocart WHERE id = :id")
    fun deleteByTitle(id: Int?)
}
package com.ortega.jf.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ortega.jf.data.entities.Users

@Dao
interface UsersDAO {

    @Query("select * from Users")
    fun getAllUsers():List<Users>

    @Query("select * from Users where id=:id")
    fun getUser(id:Int):Users

    @Insert
    fun insertUser(users:List<Users>)

    @Update
    fun updateUser(user:List<Users>)

    @Delete
    fun deleteUser(user:Users)

    //@Delete
    //public fun deleteAlbumAndSongs(val album: Album, val songs: List<Song>)



}
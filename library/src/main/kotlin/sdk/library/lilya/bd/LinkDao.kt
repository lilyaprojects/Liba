package sdk.library.lilya.bd

import androidx.lifecycle.LiveData
import androidx.room.*
import sdk.library.lilya.utils.Link

@Dao
interface LinkDao {
    @Query("SELECT * FROM test")
    fun getAll(): LiveData<List<Link>>

    @Query("SELECT * FROM test")
    fun getAllData(): List<Link>

    @Update
    fun updateLink(link: Link)

    @Insert
    fun addLink(link: Link)

    @Delete
    fun delete(link: Link)
}
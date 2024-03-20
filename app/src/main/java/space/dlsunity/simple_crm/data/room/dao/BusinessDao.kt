package space.dlsunity.simple_crm.data.room.dao

import androidx.room.*
import space.dlsunity.simple_crm.data.room.data.Business

@Dao
interface BusinessDao {
    @Query("SELECT * FROM business_table")
    fun getAll(): List<Business>

    @Query("DELETE FROM business_table")
    fun deleteAll()

    @Query("SELECT * FROM business_table WHERE id LIKE :id LIMIT 1")
    fun findEntityById(id: String): Business

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg entity: Business)

    @Delete
    fun delete(entity: Business)
}
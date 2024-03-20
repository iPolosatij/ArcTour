package space.dlsunity.simple_crm.data.room.dao

import androidx.room.*
import space.dlsunity.simple_crm.data.room.data.Constructor

@Dao
interface ConstructorDao {

    @Query("SELECT * FROM constructor_table")
    fun getAll(): List<Constructor>

    @Query("DELETE FROM constructor_table")
    fun deleteAll()

    @Query("SELECT * FROM constructor_table WHERE id LIKE :id LIMIT 1")
    fun findEntityById(id: String): Constructor

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg entity: Constructor)

    @Delete
    fun delete(entity: Constructor)

}
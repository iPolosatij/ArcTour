package space.dlsunity.arctour.data.room.dao

import androidx.room.*
import space.dlsunity.arctour.data.room.data.ConstructorType

@Dao
interface ConstructorTypeDao {

    @Query("SELECT * FROM constructor_types_table")
    fun getAll(): List<ConstructorType>

    @Query("DELETE FROM constructor_types_table")
    fun deleteAll()

    @Query("SELECT * FROM constructor_types_table WHERE id LIKE :id LIMIT 1")
    fun findEntityById(id: String): ConstructorType

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg entity: ConstructorType)

    @Delete
    fun delete(entity: ConstructorType)

}
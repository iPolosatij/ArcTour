package space.dlsunity.simple_crm.data.room.dao

import androidx.room.*
import space.dlsunity.simple_crm.data.room.data.Section

@Dao
interface SectionDao {

    @Query("SELECT * FROM sections_table")
    fun getAll(): List<Section>

    @Query("DELETE FROM sections_table")
    fun deleteAll()

    @Query("SELECT * FROM sections_table WHERE id LIKE :id LIMIT 1")
    fun findEntityById(id: String): Section

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg entity: Section)

    @Delete
    fun delete(entity: Section)

}
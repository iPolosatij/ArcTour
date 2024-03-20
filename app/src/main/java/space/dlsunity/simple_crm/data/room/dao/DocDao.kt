package space.dlsunity.simple_crm.data.room.dao

import androidx.room.*
import space.dlsunity.simple_crm.data.room.data.Doc

@Dao
interface DocDao {
    @Query("SELECT * FROM docs_table")
    fun getAll(): List<Doc>

    @Query("DELETE FROM docs_table")
    fun deleteAll()

    @Query("SELECT * FROM docs_table WHERE id LIKE :id LIMIT 1")
    fun findEntityById(id: String): Doc

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg entity: Doc)

    @Delete
    fun delete(entity: Doc)
}
package space.dlsunity.simple_crm.data.room.dao

import androidx.room.*
import space.dlsunity.simple_crm.data.room.data.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM user_table")
    fun getAll(): List<UserEntity>

    @Query("DELETE FROM user_table")
    fun deleteAll()

    @Query("SELECT * FROM user_table WHERE id LIKE :id LIMIT 1")
    fun findEntityById(id: String): UserEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg entity: UserEntity)

    @Delete
    fun delete(entity: UserEntity)
}
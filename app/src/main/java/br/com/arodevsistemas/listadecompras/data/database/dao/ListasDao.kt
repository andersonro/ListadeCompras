package br.com.arodevsistemas.listadecompras.data.database.dao

import androidx.room.*
import br.com.arodevsistemas.listadecompras.data.model.Listas
import kotlinx.coroutines.flow.Flow

@Dao
interface ListasDao {

    @Query("SELECT * FROM tb_listas ORDER BY ID DESC")
    fun getAll(): Flow<List<Listas>>

    @Query("SELECT * FROM tb_listas WHERE id = :id")
    fun getById(id: Long) : Listas

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(listas: Listas) : Long

    @Update
    suspend fun update(listas: Listas)

    @Delete
    suspend fun delete(listas: Listas)

    @Query("DELETE FROM tb_listas WHERE id = :id")
    suspend fun deleteById(id: Long)
}
package br.com.arodevsistemas.listadecompras.data.database.dao

import androidx.room.*
import br.com.arodevsistemas.listadecompras.data.model.ItensListas
import br.com.arodevsistemas.listadecompras.data.model.Listas
import kotlinx.coroutines.flow.Flow

@Dao
interface ItensListasDao {

    @Query("SELECT * FROM tb_itens_listas ORDER BY ID")
    fun getAll(): Flow<List<ItensListas>>

    @Query("SELECT * FROM tb_itens_listas WHERE id_listas = :idListas")
    fun getItensListas(idListas: Long): Flow<List<ItensListas>>

    @Query("SELECT * FROM tb_itens_listas WHERE id = :id")
    fun getById(id: Long) : Flow<ItensListas>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(itensListas: ItensListas) : Long

    @Update
    suspend fun update(itensListas: ItensListas)

    @Delete
    suspend fun delete(itensListas: ItensListas)

    @Query("DELETE FROM tb_itens_listas WHERE id_listas = :idListas")
    fun deleteItensListas(idListas: Long)
}
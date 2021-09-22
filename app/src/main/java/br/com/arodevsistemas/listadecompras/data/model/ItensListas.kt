package br.com.arodevsistemas.listadecompras.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "tb_itens_listas")
data class ItensListas(

    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    val descricao: String = "",
    val quantidade: Double = 0.0,
    val valor: Double = 0.0,
    val status: String = "P",
    @ColumnInfo(name = "id_listas") val idListas: Long = 0

) : Serializable
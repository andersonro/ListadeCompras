package br.com.arodevsistemas.listadecompras.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "tb_listas")
data class Listas(

    @PrimaryKey(autoGenerate = true) var id : Long = 0,
    @ColumnInfo(name = "dt_cadastro") var dtCadastro : String = "",
    var descricao : String = ""

): Serializable
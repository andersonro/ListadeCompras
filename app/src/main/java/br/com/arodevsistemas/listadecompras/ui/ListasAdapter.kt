package br.com.arodevsistemas.listadecompras.ui

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.arodevsistemas.listadecompras.data.model.Listas
import br.com.arodevsistemas.listadecompras.databinding.CardListasBinding
import br.com.arodevsistemas.listadecompras.ui.ItensListasActivity.Companion.OBJ_LISTA_EXTRA
import br.com.arodevsistemas.listadecompras.ui.ListasActivity.Companion.LISTA_EXTRA


class ListasAdapter(val context: Context): ListAdapter<Listas, ListasAdapter.ViewHolder>(DiffCallback()) {


    inner class ViewHolder(private val binding: CardListasBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Listas) {
            binding.tvListaDtcadastro.text = item.dtCadastro
            binding.tvListaDescricao.text = item.descricao

            binding.btnListaEdit.setOnClickListener {
                val intent = Intent(context, ListasActivity::class.java)
                intent.putExtra(LISTA_EXTRA, item)
                context.startActivity(intent)
            }

            binding.btnListaView.setOnClickListener {
                val intent = Intent(context, ItensListasActivity::class.java)
                intent.putExtra(OBJ_LISTA_EXTRA, item)
                context.startActivity(intent)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CardListasBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class DiffCallback: DiffUtil.ItemCallback<Listas>() {
    override fun areItemsTheSame(
        oldItem: Listas,
        newItem: Listas
    ) = oldItem == newItem

    override fun areContentsTheSame(
        oldItem: Listas,
        newItem: Listas
    ) = oldItem.id == newItem.id

}
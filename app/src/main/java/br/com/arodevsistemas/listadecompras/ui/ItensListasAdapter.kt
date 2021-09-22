package br.com.arodevsistemas.listadecompras.ui

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.arodevsistemas.listadecompras.core.extensions.text
import br.com.arodevsistemas.listadecompras.data.model.ItensListas
import br.com.arodevsistemas.listadecompras.databinding.CardItensListasBinding
import br.com.arodevsistemas.listadecompras.databinding.CardListasBinding

class ItensListasAdapter(
    private val context: Context
    ): ListAdapter<ItensListas, ItensListasAdapter.ViewHolder>(DiffCallbackItensListas()) {

    var listenerCard: (View) -> Unit = {}
    var listenerItensListas: (ItensListas) -> Unit = {}
    var listenerCheckbox: (ItensListas) -> Unit = {}
    var listenerRefresh: (ItensListas) -> Unit = {}

    inner class ViewHolder(private val binding: CardItensListasBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(itensListas: ItensListas){

            var itemTotal = if (itensListas.quantidade>0 && itensListas.valor>0){
                itensListas.quantidade * itensListas.valor
            }else{
                0.0
            }

            val totalItemFormat:Double = String.format("%.2f", itemTotal).toDouble()

            binding.tvItensListasDescription.text = itensListas.descricao
            binding.tilListasQtd.text = itensListas.quantidade.toString()
            binding.tilListasValor.text = itensListas.valor.toString()
            binding.tvItensListasTotalItemValor.text = totalItemFormat.toString()

            if (itensListas.status=="C"){
                binding.tvItensListasDescription.paintFlags = (binding.tvItensListasDescription.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
                binding.cbItem.isChecked = true
                binding.tilListasQtd.isEnabled = false
                binding.tilListasValor.isEnabled = false
                binding.btnItensListaRefresh.isEnabled = false
                binding.btnItensListaEdit.isEnabled = false
            }else{
                binding.cbItem.isChecked = false
                binding.tilListasQtd.isEnabled = true
                binding.tilListasValor.isEnabled = true
                binding.btnItensListaRefresh.isEnabled = true
                binding.btnItensListaEdit.isEnabled = true
            }

            binding.btnItensListaEdit.setOnClickListener {
                listenerCard(it)
                listenerItensListas(itensListas)
            }

            binding.cbItem.setOnCheckedChangeListener { buttonView, isChecked ->
                var status = if(isChecked){
                    "C"
                }else {
                    "P"
                }
                var item = itensListas.copy(status = status)
                listenerCheckbox(item)
            }

            binding.btnItensListaRefresh.setOnClickListener {
                var qtd = 0.0
                var valor = 0.0

                if(!binding.tilListasQtd.text.isNullOrEmpty() && !binding.tilListasValor.text.isNullOrEmpty()){
                    qtd = binding.tilListasQtd.text.toDouble()
                    valor = binding.tilListasValor.text.toDouble()
                }
                var item = itensListas.copy(quantidade = qtd, valor = valor)

                listenerRefresh(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CardItensListasBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class DiffCallbackItensListas: DiffUtil.ItemCallback<ItensListas>() {
    override fun areItemsTheSame(
        oldItem: ItensListas,
        newItem: ItensListas
    ) = oldItem == newItem

    override fun areContentsTheSame(
        oldItem: ItensListas,
        newItem: ItensListas
    ) = oldItem.id == newItem.id
}
package org.altitudesolutions.estacindeservicio.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_register.view.*
import org.altitudesolutions.estacindeservicio.R
import org.altitudesolutions.estacindeservicio.models.Register

class RegisterAdapter(private val context: Context, private val registers: List<Register>): RecyclerView.Adapter<RegisterAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun setData(register: Register) {
            itemView.numeroDeMovll.text = register.movil
            itemView.tipoDeCombustible.text = register.producto
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =  LayoutInflater.from(context).inflate(R.layout.item_register, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return registers.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentRegister = registers[position]
        holder.setData(currentRegister )
    }
}
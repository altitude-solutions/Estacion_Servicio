package org.altitudesolutions.estacindeservicio.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_register.view.*
import org.altitudesolutions.estacindeservicio.R
import org.altitudesolutions.estacindeservicio.models.Register
import java.text.SimpleDateFormat
import java.util.*

class RegisterAdapter(private val context: Context, private val registers: List<Register>): RecyclerView.Adapter<RegisterAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun setData(register: Register) {
            itemView.vehicle.text = register.movil
            itemView.productName.text = register.producto
            try {
                val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                val netDate = Date(register.fechaYHora)
                itemView.dateTime.text = sdf.format(netDate)
            } catch (e: Exception) {
                //e.toString()
            }
            itemView.kmDistance.text = register.kilometraje.toString() + " Km"
            itemView.totalCost.text = register.precioTotal.toString() + " Bs"
            itemView.volume.text = register.litros.toString() + " L"
            itemView.comments.text = register.comentarios.toString()
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
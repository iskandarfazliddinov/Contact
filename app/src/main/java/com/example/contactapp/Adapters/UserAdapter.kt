package com.example.contactapp.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contactapp.Models.UserData
import com.example.contactapp.databinding.ItemsBinding

class UserAdapter(val list : ArrayList<UserData>,
                  val onItemClick: (userdata: UserData) -> Unit,
                  val onItemClickSms: (userdata: UserData) -> Unit
):RecyclerView.Adapter<UserAdapter.VH>() {

    inner class VH(val itemsBinding: ItemsBinding):RecyclerView.ViewHolder(itemsBinding.root){
        fun onBind(userData: UserData){
            itemsBinding.apply {
                tvName.text = userData.name
                tvPhone.text = userData.phoneNumber

                btnSms.setOnClickListener {
                    onItemClick.invoke(userData)
                }
                btnTel.setOnClickListener {
                    onItemClickSms.invoke(userData)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
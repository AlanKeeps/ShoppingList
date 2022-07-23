package com.example.shoppinglist.db

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ListNameItemBinding
import com.example.shoppinglist.entities.ShopListNameItem
import com.example.shoppinglist.entities.ShopListItem

class ShopListItemAdapter(private val listener: Listener): ListAdapter<ShopListItem, ShopListItemAdapter.ItemHolder>(ItemComporator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return if
                ( viewType == 0)ItemHolder.createShopItem(parent)
        else
            ItemHolder.createLibraryItem(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        if (getItem(position).itemType == 0){
            holder.setItemData(getItem(position), listener)
        } else {
            holder.setLibraryData(getItem(position), listener)
        }
    }

    override fun getItemViewType(position: Int): Int{
        return getItem(position).itemType
    }

    class ItemHolder(view: View) : RecyclerView.ViewHolder(view){
            private val binding = ListNameItemBinding.bind(view)

        fun setItemData(shopListItem: ShopListItem, listener: Listener) = with(binding){
        }

        fun setLibraryData(shopListItem: ShopListItem, listener: Listener) = with(binding){
        }


        companion object{
            fun createShopItem(parent: ViewGroup): ItemHolder{
                return ItemHolder(LayoutInflater.from(parent.context).
                inflate(R.layout.shop_list_item, parent, false))
            }
            fun createLibraryItem(parent: ViewGroup): ItemHolder{
                return ItemHolder(LayoutInflater.from(parent.context).
                inflate(R.layout.shop_library_list_item, parent, false))
            }
        }
    }

    class ItemComporator : DiffUtil.ItemCallback<ShopListItem>(){
        override fun areItemsTheSame(oldItem: ShopListItem, newItem: ShopListItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ShopListItem, newItem: ShopListItem): Boolean {
            return oldItem == newItem
        }

    }

    interface Listener{
        fun deleteItem(id: Int)
        fun editItem(shopListName: ShopListNameItem)
        fun onClickItem(shopListName: ShopListNameItem)
    }
}
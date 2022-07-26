package com.example.shoppinglist.db

import androidx.lifecycle.*
import com.example.shoppinglist.entities.LibraryItem
import com.example.shoppinglist.entities.NoteItem
import com.example.shoppinglist.entities.ShopListItem
import com.example.shoppinglist.entities.ShopListNameItem
import kotlinx.coroutines.launch

class MainViewModel(dataBase: MainDataBase) : ViewModel() {
    val dao = dataBase.getDao()
    val libraryItems = MutableLiveData<List<LibraryItem>>()

    val allNotes: LiveData<List<NoteItem>> = dao.getAllNotes().asLiveData()
    val allShopListNameItem: LiveData<List<ShopListNameItem>> = dao.getAllShopListNames().asLiveData()
    fun getAllItemsFromList(listId: Int): LiveData<List<ShopListItem>>{
        return dao.getAllShopListItems(listId). asLiveData()
    }
    fun getAllLibraryItems(name: String) = viewModelScope.launch{
        libraryItems.postValue(dao.getAllLibraryItems(name))
    }

    fun insertNote(note: NoteItem) = viewModelScope.launch {
        dao.insertNote(note)
    }
    fun insertShopListName(listName: ShopListNameItem) = viewModelScope.launch {
        dao.insertShopListName(listName)
    }
    fun insertShopItem(shopListItem: ShopListItem) = viewModelScope.launch {
        dao.insertItem(shopListItem)
        if (!isLibraryItemExist(shopListItem.name)) dao.insertLibraryItem(LibraryItem(null, shopListItem.name))
    }

    fun updateNote(note: NoteItem) = viewModelScope.launch {
        dao.updateNote(note)
    }
    fun updateLibraryItem(item: LibraryItem) = viewModelScope.launch {
        dao.updateLibraryItem(item)
    }
    fun updateListItem(item: ShopListItem) = viewModelScope.launch {
        dao.updateListItem(item)
    }
    fun updateListName(shopListName: ShopListNameItem) = viewModelScope.launch {
        dao.updateListName(shopListName)
    }

    fun deleteNote(id: Int) = viewModelScope.launch {
        dao.deleteNote(id)
    }
    fun deleteShopList(id: Int, deleteList: Boolean) = viewModelScope.launch {
        if(deleteList) dao.deleteShopListName(id)
        dao.deleteShopItemsByListId(id)
    }
    fun deleteLibraryItem(id: Int) = viewModelScope.launch {
        dao.deleteLibraryItem(id)
    }

    private suspend fun isLibraryItemExist(name: String): Boolean{
        return dao.getAllLibraryItems(name).isNotEmpty()
    }

    class MainViewModelFactory(val dataBase: MainDataBase) : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(dataBase)as T
            }
            throw IllegalArgumentException("Unknown ViewModelClass")
           }
    }
}
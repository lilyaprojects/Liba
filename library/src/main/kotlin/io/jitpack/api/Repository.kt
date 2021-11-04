package io.jitpack.api

import androidx.lifecycle.LiveData
import io.jitpack.api.bd.LinkDao
import io.jitpack.api.utils.Link
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Repository(var linkDao: LinkDao) {

    val readAllData: LiveData<List<Link>> = linkDao.getAll()


    fun getAllData(): List<Link>{
        return linkDao.getAllData()
    }

    fun insert(link: Link){
        GlobalScope.launch(Dispatchers.IO){ linkDao.addLink(link) }
    }

    fun updateLink(link: Link){
        GlobalScope.launch(Dispatchers.IO) { linkDao.updateLink(link)  }
    }
}
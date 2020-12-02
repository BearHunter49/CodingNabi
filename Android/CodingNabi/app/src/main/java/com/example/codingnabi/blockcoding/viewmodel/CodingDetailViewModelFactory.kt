package com.example.codingnabi.blockcoding.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CodingDetailViewModelFactory(private val application: Application, private val category: String, private val level: Int) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (AndroidViewModel::class.java.isAssignableFrom(modelClass)){
            try {
                return modelClass.getConstructor(Application::class.java, String::class.java, Int::class.java).newInstance(application, category, level)
            } catch (e: InstantiationException){
                throw RuntimeException("Cannot create an instance of $modelClass", e)
            }
        }else{
            throw IllegalArgumentException()
        }
    }
}
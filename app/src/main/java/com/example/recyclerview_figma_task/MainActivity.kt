package com.example.recyclerview_figma_task

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerview_figma_task.databinding.ActivityMainBinding
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserAdapter
    private lateinit var list: ArrayList<UserInfo>
    private var id by Delegates.notNull<Int>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

         id = 0

        list = ArrayList()

        binding.recyclerItems.layoutManager = LinearLayoutManager(this)

        adapter = UserAdapter(this, list)

        binding.recyclerItems.adapter = adapter

        binding.addUserBtn.setOnClickListener {

            addUser()
        }
        binding.updateUserBtn.setOnClickListener {

            val user = UserInfo(
                binding.inputID.text.toString().toInt(),
                binding.textUserName.text.toString(),
                binding.textInputUserSurname.text.toString(),
                binding.ageInput.text.toString().toInt())

            adapter.updateUser(user)

        }
    }


    fun addUser(){
        val user = UserInfo(++id,
            binding.textUserName.text.toString(),
            binding.textInputUserSurname.text.toString(),
            binding.ageInput.text.toString().toInt())

        adapter.addUser(user)
    }

}
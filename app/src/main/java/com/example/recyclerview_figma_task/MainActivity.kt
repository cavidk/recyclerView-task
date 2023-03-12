package com.example.recyclerview_figma_task

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerview_figma_task.databinding.ActivityMainBinding
import com.example.recyclerview_figma_task.databinding.AlertUserBinding
import java.text.FieldPosition
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserAdapter
    private lateinit var list: ArrayList<UserInfo>
    private var id by Delegates.notNull<Int>()

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        id = 0

        list = ArrayList()

        binding.recyclerItems.layoutManager = LinearLayoutManager(this)

        adapter = UserAdapter(this,object :UserClickListener{
            override fun onClickListener(userInfo: UserInfo) {
                updateUser(userInfo)
            }
            override fun longClickListener(position: Int) {
                       removeUser(position)
            }
        },list)

        binding.recyclerItems.adapter = adapter

        binding.addUserButton.setOnClickListener {
            addUser()
        }
    }

    fun addUser() {
        val bindingDialog = AlertUserBinding.inflate(layoutInflater, null, false)
        val ad = AlertDialog.Builder(this)
            .setMessage("Add User")
            .setTitle("Enter user Info")
            .setIcon(R.drawable.baseline_person_add_alt_24)
            .setView(bindingDialog.root)

            .setPositiveButton("Save") { dialogInterface, i ->
                val user = UserInfo(
                    ++id,
                    bindingDialog.inputUserName.text.toString(),
                    bindingDialog.inputUserSurname.text.toString(),
                    bindingDialog.ageInput.text.toString().toInt()
                )
                adapter.addUser(user)
                Toast.makeText(applicationContext, "User saved!", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel") { dialogInterface, i ->
                Toast.makeText(applicationContext, "Cancel clicked!", Toast.LENGTH_SHORT).show()
            }   //adding builder
            .create()
        // add
        ad.show()
    }

    fun updateUser(userNewInfo: UserInfo){
        val bindingDialog = AlertUserBinding.inflate(layoutInflater, null, false)
        val ad = AlertDialog.Builder(this)
            .setMessage("Update User")
            .setTitle("Enter user Info")
            .setIcon(R.drawable.baseline_person_add_alt_24)
            .setView(bindingDialog.root)

             bindingDialog.inputUserName.setText(userNewInfo.userName)
             bindingDialog.inputUserSurname.setText(userNewInfo.userSurname)
             bindingDialog.ageInput.setText(userNewInfo.age.toString())
             bindingDialog.userID.setText(userNewInfo.userId.toString())

            ad.setPositiveButton("Update") { dialogInterface, i ->

                val user = UserInfo(
                    userNewInfo.userId,
                    bindingDialog.inputUserName.text.toString(),
                    bindingDialog.inputUserSurname.text.toString(),
                    bindingDialog.ageInput.text.toString().toInt()
                )
                adapter.updateUser(user)

                Toast.makeText(applicationContext, "User saved!", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel") { dialogInterface, i ->
                Toast.makeText(applicationContext, "Cancel clicked!", Toast.LENGTH_SHORT).show()
            }   //adding builder
            .create()
        // add
        ad.show()
    }
    fun removeUser(position:Int){
        val ad = AlertDialog.Builder(this)
            .setMessage("Remove User")
            .setTitle("Delete Info")
            .setIcon(R.drawable.baseline_person_add_alt_24)

            .setPositiveButton("Delete") { dialogInterface, i ->
                Toast.makeText(applicationContext, "User Deleted!", Toast.LENGTH_SHORT).show()
                adapter.removeUser(position)
            }
            .setNegativeButton("Cancel") { dialogInterface, i ->
                Toast.makeText(applicationContext, "Cancel clicked!", Toast.LENGTH_SHORT).show()
            }   //adding builder
            .create()
        // add
        ad.show()
    }
}
package com.example.recyclerview_figma_task

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(private val myContext:Context,private val userClickListener: UserClickListener, var userList: ArrayList<UserInfo>)
    :RecyclerView.Adapter<UserAdapter.UserInfoCatch>() {
    inner class UserInfoCatch(view:View):RecyclerView.ViewHolder(view){
       var userCardInfo : CardView
       var userName : TextView
       var userSurName : TextView
       var age : TextView
       var userId : TextView

        init {
            userCardInfo = view.findViewById(R.id.userCardInfo)
            userName = view.findViewById(R.id.userName)
            userSurName = view.findViewById(R.id.userSurname)
            age = view.findViewById(R.id.age)
            userId = view.findViewById(R.id.userId)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserInfoCatch {
       val layoutInflater =LayoutInflater.from(myContext).inflate(R.layout.user_cardview,parent,false)
        return UserInfoCatch(layoutInflater)
     }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserInfoCatch, position: Int) {

        val user = userList[position]

        holder.userName.text = user.userName
        holder.userSurName.text = user.userSurname
        holder.age.text = user.age.toString()
        holder.userId.text = user.userId.toString()

        holder.userCardInfo.setOnClickListener {
          userClickListener.onClickListener(user)
        }
        holder.userCardInfo.setOnLongClickListener {

            userClickListener.longClickListener(position)

            true
        }
    }

    fun addUser(newUser:UserInfo){
        userList.add(newUser)

        notifyItemInserted(userList.size - 1)
    }

    fun updateUser(newUser:UserInfo){
        var position :Int? = null
       this.userList.forEachIndexed { index, userInfo ->

           if (userInfo.userId == newUser.userId){
               position = index
                return@forEachIndexed
           }
       }

     position?.let {
         this.userList[it] = newUser
         notifyItemChanged(it)
     }?: run {
         Toast.makeText(myContext, "Here is not a element", Toast.LENGTH_SHORT).show()

     }
    }

    fun removeUser(position: Int){
        userList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position,userList.size)
    }
}

interface UserClickListener{
    fun onClickListener(userInfo: UserInfo)

    fun longClickListener(position: Int)
}

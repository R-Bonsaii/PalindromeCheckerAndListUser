package com.snobos.suitmedia.adapter

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.snobos.suitmedia.data.response.ItemsItem
import com.snobos.suitmedia.databinding.ItemUserBinding
import com.snobos.suitmedia.ui.ThirdActivity

class UserAdapter :
    PagingDataAdapter<ItemsItem, UserAdapter.UserViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                bindingAdapterPosition.takeIf { it != RecyclerView.NO_POSITION }?.let { position ->
                    getItem(position)?.let { item ->
                        val selectedUserName = item.firstName + " " + item.lastName
                        val data = Intent().apply {
                            putExtra("selectedUserName", selectedUserName)
                        }
                        (itemView.context as? ThirdActivity)?.apply {
                            setResult(Activity.RESULT_OK, data)
                            finish()
                        }
                    }
                }
            }
        }

        fun bind(data: ItemsItem) {
            with(binding) {
                tvFirstName.text = data.firstName
                tvLastName.text = data.lastName
                tvEmail.text = data.email
                Glide.with(itemView.context)
                    .load(data.avatar)
                    .circleCrop()
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressBar.visibility = ViewGroup.GONE
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressBar.visibility = ViewGroup.GONE
                            return false
                        }
                    })
                    .into(ivAvatar)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean =
                oldItem.id == newItem.id
        }
    }
}
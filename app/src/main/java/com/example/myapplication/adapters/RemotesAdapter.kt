package com.example.myapplication.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.Remote

class RemotesAdapter(private val context: Context, val listener: RemoteListItemClickListener) :
    RecyclerView.Adapter<RemotesAdapter.RemoteViewHolder>() {

    private val remotesList = ArrayList<Remote>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RemoteViewHolder {
        return RemoteViewHolder(
            LayoutInflater.from(context).inflate(R.layout.remote_list_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return remotesList.size
    }

    override fun onBindViewHolder(holder: RemoteViewHolder, position: Int) {
        val remote = remotesList[position]
        holder.remote_name.text = remote.name
        holder.remote_name.isSelected = true

        holder.remotes_layout.setOnClickListener {
            listener.onRemoteClick(remotesList[holder.adapterPosition])
        }
    }

    fun updateRemotes(remotes: List<Remote>) {
        this.remotesList.clear()
        this.remotesList.addAll(remotes)
        notifyDataSetChanged()
    }

    inner class RemoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val remotes_layout = itemView.findViewById<CardView>(R.id.remote_item_cardView)
        val remote_name = itemView.findViewById<TextView>(R.id.remote_list_item_remote_name)
    }


    interface RemoteListItemClickListener {
        fun onRemoteClick(remote: Remote)
    }


}
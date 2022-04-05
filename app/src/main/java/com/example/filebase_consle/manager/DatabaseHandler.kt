package com.example.filebase_consle.manager

import com.example.filebase_consle.model.Post

interface DatabaseHandler {
    fun onSuccess(post: Post? = null, posts: ArrayList<Post> = ArrayList())
    fun onError()
}

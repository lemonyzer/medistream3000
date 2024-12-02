package com.shortcutz.medistream3000.blog

import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class PostService (
    val postRepository: PostRepository
) {

    fun getAllPosts(): List<Post?> {
        return postRepository.findAll()
    }

    fun getPostById(id: Long?): Post {
        return postRepository.findById(id!!).orElse(null)!!
    }

    fun createPost(post: Post): Post {
        post.dateAdded = LocalDateTime.now()
        post.dateModified = LocalDateTime.now()
        return postRepository.save(post)
    }

    fun updatePost(id: Long?, updatedPost: Post): Post? {
        val existingPost = getPostById(id)
        if (existingPost != null) {
            existingPost.text = updatedPost.text
            existingPost.dateModified = LocalDateTime.now()
            return postRepository.save(existingPost)
        }
        return null
    }

    fun deletePost(id: Long?) {
        postRepository.deleteById(id!!)
    }
}
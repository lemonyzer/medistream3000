package com.shortcutz.medistream3000.blog

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/htmx/posts")
class PostHtmxController (
    val postService: PostService
) {

    @GetMapping
    fun getAllPosts(model: Model): String {
        val posts = postService!!.getAllPosts()

        model.addAttribute("title", "Blog Page")
        model.addAttribute("content", "all-posts")
        model.addAttribute("posts", posts)
        return "index"
    }

    @PostMapping
    fun createPost(@RequestParam text: String?, model: Model): String {
        val post = Post()
        post.text=text
        postService!!.createPost(post)
        val posts = postService.getAllPosts()
        model.addAttribute("posts", posts)
        return "blog/all-posts :: #posts-list"
    }

    @GetMapping("/{id}/edit")
    fun getPostForEdit(@PathVariable id: Long?, model: Model): String {
        val post = postService!!.getPostById(id)
        model.addAttribute("post", post)
        return "blog/edit-post-form :: #edit-form-container"
    }

    @PatchMapping("/{id}")
    fun updatePost(@PathVariable id: Long?, @RequestParam text: String?, model: Model): String {
        val post = postService!!.getPostById(id)
        post.text=text
        postService.updatePost(id, post)
        val posts = postService.getAllPosts()
        model.addAttribute("posts", posts)
        return "blog/all-posts :: #posts-list"
    }

    @DeleteMapping("/{id}")
    fun deletePost(@PathVariable id: Long?, model: Model): String {
        postService!!.deletePost(id)
        val posts = postService.getAllPosts()
        model.addAttribute("posts", posts)
        return "blog/all-posts :: #posts-list"
    }
}
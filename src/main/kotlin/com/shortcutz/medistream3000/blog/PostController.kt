package com.shortcutz.medistream3000.blog

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/posts")
class PostController (
    val postService: PostService
){
    @GetMapping
    fun getAllPosts(request: HttpServletRequest?): ResponseEntity<*> {
//        val userOptional: Optional<User> = authService.getUserFromSession(request)
//
//        if (userOptional.isPresent()) {
//            val user: User = userOptional.get()
            val posts = postService.getAllPosts()

//            if (user.getRoleId() === RoleEnum.ADMIN.getRoleId()) {
//                posts.forEach(Consumer<Post?> { post: Post? -> post.setText("ADMIN - " + post.getText()) })
//            }

            return ResponseEntity(posts, HttpStatus.OK)
//        }
//
//        return ResponseEntity("Unauthorized", HttpStatus.UNAUTHORIZED)
    }

    @GetMapping("/{id}")
    fun getPostById(@PathVariable id: Long?): Post {
        return postService.getPostById(id)
    }

    @PostMapping
    fun createPost(@RequestBody post: Post?): Post {
        return postService.createPost(post!!)
    }

    @PutMapping("/{id}")
    fun updatePost(@PathVariable id: Long?, @RequestBody updatedPost: Post?): Post? {
        return postService.updatePost(id, updatedPost!!)
    }

    @DeleteMapping("/{id}")
    fun deletePost(@PathVariable id: Long?) {
        postService.deletePost(id)
    }
}

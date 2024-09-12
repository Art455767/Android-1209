
package ru.netology.nmedia.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.dto.formatCount
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel by viewModels<PostViewModel>()
        viewModel.data.observe(this) { post ->
            with(binding) {
                author.text = post.author
                content.text = post.content
                published.text = post.published
                likeCount.text = formatCount(post.likes)
                shareCount.text = formatCount(post.shares)
                like.setImageResource(if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24)
                share.setImageResource(R.drawable.ic_share_24)

                like.setOnClickListener {
                    post.likedByMe = !post.likedByMe
                    if (post.likedByMe) {
                        like.setImageResource(R.drawable.ic_liked_24)
                        post.likes++
                        Toast.makeText(this@MainActivity, "Лайк поставлен!", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        like.setImageResource(R.drawable.ic_like_24)
                        post.likes--
                        Toast.makeText(this@MainActivity, "Лайк убран!", Toast.LENGTH_SHORT).show()
                    }
                    likeCount.text = formatCount(post.likes)
                }

                share.setOnClickListener {
                    share.setImageResource(R.drawable.ic_share_24)
                    post.shares++
                    shareCount.text = formatCount(post.shares)
                }
            }
        }
    }
}
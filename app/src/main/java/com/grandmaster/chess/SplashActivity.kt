package com.grandmaster.chess

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.AnimatorSet
import android.content.Intent
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

/**
 * Welcome screen: logo tampil total 3 detik
 * fade in 0,8 dtk -> tahan 1,4 dtk -> fade out 0,8 dtk, lalu masuk ke MainActivity.
 */
class SplashActivity : AppCompatActivity() {

    private var animator: AnimatorSet? = null
    private var launched = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val logo = findViewById<ImageView>(R.id.splashLogo)

        val fadeIn = ObjectAnimator.ofFloat(logo, "alpha", 0f, 1f).apply {
            duration = FADE_MS
            interpolator = AccelerateDecelerateInterpolator()
        }
        val fadeOut = ObjectAnimator.ofFloat(logo, "alpha", 1f, 0f).apply {
            duration = FADE_MS
            startDelay = TOTAL_MS - 2 * FADE_MS   // jeda tahan antara fade in dan fade out
            interpolator = AccelerateDecelerateInterpolator()
        }

        // Fade out dimulai setelah fade in selesai + masa tahan => total persis 3000 ms
        animator = AnimatorSet().apply {
            playSequentially(fadeIn, fadeOut)
            addListener(object : AnimatorListenerAdapter() {
                private var cancelled = false
                override fun onAnimationCancel(animation: Animator) { cancelled = true }
                override fun onAnimationEnd(animation: Animator) {
                    if (!cancelled) openMain()
                }
            })
            start()
        }
    }

    private fun openMain() {
        if (launched) return
        launched = true
        startActivity(Intent(this, MainActivity::class.java))
        finish()
        @Suppress("DEPRECATION")
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    override fun onDestroy() {
        animator?.cancel()
        super.onDestroy()
    }

    companion object {
        private const val TOTAL_MS = 3000L
        private const val FADE_MS = 800L
    }
}

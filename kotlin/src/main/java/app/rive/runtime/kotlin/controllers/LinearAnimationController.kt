package app.rive.runtime.kotlin.controllers

import app.rive.runtime.kotlin.core.Artboard
import app.rive.runtime.kotlin.core.LinearAnimationInstance

class LinearAnimationController(
    private val animationName: String,
    private val autoplay: Boolean = true,
    mix: Float = 1.0f,
) : RiveController<Artboard>() {
    var animationInstance: LinearAnimationInstance? = null
    var mix: Float = mix
        get() = field
        set(value) {
            field = value.coerceIn(0.0f, 1.0f)
        }

    override fun apply(artboard: Artboard, elapsed: Float) {
        if (animationInstance == null) {
            // || animation can keep going?
            isActive = false
        }

        animationInstance?.let {
            it.apply(artboard, this.mix)
            it.advance(elapsed)
        }

    }

    override fun initialize(artboard: Artboard): Boolean {
        val animation = artboard.animation(animationName)
        val ai = LinearAnimationInstance(animation)
        ai.apply(artboard, mix)
        ai.advance(0f)
        this.animationInstance = ai

        isActive = autoplay
        return animationInstance != null
    }
}
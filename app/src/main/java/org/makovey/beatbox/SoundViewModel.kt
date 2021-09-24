package org.makovey.beatbox

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData

class SoundViewModel(private val beatBox: BeatBox) : BaseObservable() {
    fun onButtonClicked() {
        sound?.let {
            beatBox.play(it, speed!!)
        }
    }

    var sound: Sound? = null
        set(value) {
            field = value
            notifyChange()
        }

    @get:Bindable
    val title: String?
        get() = sound?.name

    var speed: Float? = 1.0f
        set(value) {
            field = value
            notifyChange()
        }
}
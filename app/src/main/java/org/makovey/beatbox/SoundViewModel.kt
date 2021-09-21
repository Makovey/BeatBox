package org.makovey.beatbox

class SoundViewModel {
    var sound: Sound? = null
        set(value) {
            field = value
        }

    val title: String?
        get() = sound?.name
}
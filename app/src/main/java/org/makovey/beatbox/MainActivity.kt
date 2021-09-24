package org.makovey.beatbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.makovey.beatbox.databinding.ActivityMainBinding
import org.makovey.beatbox.databinding.ListItemSoundBinding

class MainActivity : AppCompatActivity() {

    private var playSpeed = 1.0f
    private lateinit var seekBar: SeekBar
    private lateinit var progressText: TextView
    private lateinit var beatBox: BeatBox
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        seekBar = binding.seekBar
        progressText = binding.playBackSpeedTv
        recyclerView = binding.recyclerView
        beatBox = BeatBox(assets)

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = SoundAdapter(beatBox.sounds)
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekbar: SeekBar?, p1: Int, p2: Boolean) {
                progressText.text = "${seekBar.progress}%"
                playSpeed = (seekbar!!.progress) * 0.05.toFloat()
                updateUI()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                //TODO
            }

            override fun onStopTrackingTouch(seekbar: SeekBar?) {
                //TODO
            }

        })
    }

    private fun updateUI(){
        recyclerView.adapter = SoundAdapter(beatBox.sounds)
    }

    private inner class SoundHolder(private val binding: ListItemSoundBinding) :
        RecyclerView.ViewHolder(binding.root) {
            init {
                binding.viewModel = SoundViewModel(beatBox)
            }

        fun bind(sound: Sound) {
            binding.apply {
                viewModel?.sound = sound
                viewModel?.speed = playSpeed
                executePendingBindings()
            }
        }
    }

    private inner class SoundAdapter(private val sounds: List<Sound>) :
        RecyclerView.Adapter<SoundHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoundHolder {
            val binding = DataBindingUtil.inflate<ListItemSoundBinding>(
                layoutInflater,
                R.layout.list_item_sound,
                parent,
                false
            )
            return SoundHolder(binding)
        }

        override fun onBindViewHolder(holder: SoundHolder, position: Int) {
            val sound = sounds[position]
            holder.bind(sound)
        }

        override fun getItemCount(): Int = sounds.size

    }

    override fun onDestroy() {
        super.onDestroy()
        beatBox.release()
    }
}
package com.example.mynotes.ui.archive

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mynotes.databinding.FragmentArchiveBinding

class ArchiveFragment : Fragment() {

  private lateinit var archiveViewModel: ArchiveViewModel
private var _binding: FragmentArchiveBinding? = null
  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    archiveViewModel =
            ViewModelProvider(this).get(ArchiveViewModel::class.java)

    _binding = FragmentArchiveBinding.inflate(inflater, container, false)
    val root: View = binding.root

    val textView: TextView = binding.textSlideshow
    archiveViewModel.text.observe(viewLifecycleOwner, Observer {
      textView.text = it
    })
    return root
  }

override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.example.mynotes.ui.archive

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mynotes.SharedViewModel
import com.example.mynotes.databinding.FragmentArchiveBinding

class ArchiveFragment : Fragment() {

    private val sharedSharedViewModel: SharedViewModel by activityViewModels()
    private var _binding: FragmentArchiveBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
    ): View? {

      _binding = FragmentArchiveBinding.inflate(inflater, container, false)
      val root: View = binding.root

      val textView: TextView = binding.textSlideshow
      textView.text = "This is Archive Fragment"
      return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
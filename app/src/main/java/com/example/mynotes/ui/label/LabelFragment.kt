package com.example.mynotes.ui.label

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mynotes.databinding.FragmentLabelBinding

class LabelFragment : Fragment() {

  private lateinit var labelViewModel: LabelViewModel
private var _binding: FragmentLabelBinding? = null
  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    labelViewModel =
            ViewModelProvider(this).get(LabelViewModel::class.java)

    _binding = FragmentLabelBinding.inflate(inflater, container, false)
    val root: View = binding.root

    val textView: TextView = binding.textGallery
    labelViewModel.text.observe(viewLifecycleOwner, Observer {
      textView.text = it
    })
    return root
  }

override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
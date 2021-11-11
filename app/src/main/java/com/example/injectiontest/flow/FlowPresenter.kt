package com.example.injectiontest.flow

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.injectiontest.R
import com.example.injectiontest.databinding.FragmentFlowBinding
import com.example.injectiontest.view.ColorBox
import timber.log.Timber
import javax.inject.Inject

class FlowPresenter @Inject constructor(
    private val fragment: Fragment
) {
    private val binding = FragmentFlowBinding.bind(fragment.requireView())

    fun doSomething() {

        setListeners(
            listOf(
                binding.blue,
                binding.green,
                binding.red,
                binding.orange,
                binding.purple,
                binding.teal,
                binding.yellow
            )
        )


        val lastElement = ColorBox(context = fragment.requireContext()).also {
            it.setBgColor(Color.DKGRAY)
            it.setText("Dark Gray Color with long name")
            it.id = View.generateViewId()
        }
        val dimension = fragment.resources.getDimension(R.dimen.colorview_width)
        val horizontalGap = fragment.resources.getDimension(R.dimen.flow_horizontal_gap)
//        binding.flowRoot.doOnLayout {
//            val maxWidth = binding.flowRoot.width
//
//            val combinedWidth = dimension + horizontalGap
//            var remainingWidth = maxWidth - dimension
//            var items = 1
//            while (remainingWidth > (combinedWidth)) {
//                remainingWidth -= (combinedWidth)
//                items++
//            }
//            Timber.w("Items per row are $items")
//
//
//            val views = listOf(
//                ColorBox(context = fragment.requireContext()).also {
//                    it.setBgColor(Color.MAGENTA)
//                    it.setText("Magenta Color")
//                    it.id = View.generateViewId()
//                },
//                ColorBox(context = fragment.requireContext()).also {
//                    it.setBgColor(Color.BLUE)
//                    it.setText("Blue Color")
//                    it.id = View.generateViewId()
//                },
//                ColorBox(context = fragment.requireContext()).also {
//                    it.setBgColor(Color.GREEN)
//                    it.setText("Green Color")
//                    it.id = View.generateViewId()
//                },
//                ColorBox(context = fragment.requireContext()).also {
//                    it.setBgColor(Color.RED)
//                    it.setText("Red Color")
//                    it.id = View.generateViewId()
//                },
//                ColorBox(context = fragment.requireContext()).also {
//                    it.setBgColor(Color.YELLOW)
//                    it.setText("Yellow Color")
//                    it.id = View.generateViewId()
//                },
//                ColorBox(context = fragment.requireContext()).also {
//                    it.setBgColor(Color.GRAY)
//                    it.setText("Gray Color with long name")
//                    it.id = View.generateViewId()
//                },
//                lastElement,
//            )
////            flow.updateViews(views, emptyList())
//        }
    }

    private fun setListeners(elements: List<View>) {

        elements.forEach {
            it.setOnClickListener {
                val text = it.findViewById<TextView>(R.id.textView)
                Timber.w("Clicked ${text?.text ?: "NONE"}")
            }
        }
    }
}

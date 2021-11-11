package com.example.injectiontest.injectedobjects

import android.graphics.Color
import androidx.fragment.app.Fragment
import javax.inject.Inject
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.helper.widget.Flow
import androidx.core.view.children
import androidx.core.view.doOnLayout
import com.example.app_annotations.FragmentArgument
import com.example.injectiontest.LobbyFragment.Companion.INTEGER_PARAM_KEY
import com.example.injectiontest.LobbyFragment.Companion.PARCELABLE_PARAM_KEY
import com.example.injectiontest.LobbyFragment.Companion.STRING_PARAM_KEY
import com.example.injectiontest.R
import com.example.injectiontest.databinding.FragmentMainBinding
import com.example.injectiontest.model.ParamHolder
import com.example.injectiontest.util.updateViews
import com.example.injectiontest.view.ColorBox
import timber.log.Timber
import javax.inject.Named

class LobbyPresenter @Inject constructor(
    private val fragment: Fragment,
    private val viewModel: LobbyViewModel,
    @Named(STRING_PARAM_KEY)
    @FragmentArgument
    private val injectedParam: String,
    @Named(PARCELABLE_PARAM_KEY)
    @FragmentArgument
    private val injectedParamHolder: ParamHolder,
    @Named(INTEGER_PARAM_KEY)
    @FragmentArgument
    private val injectedInteger: Int
) {

    private val binding = FragmentMainBinding.bind(fragment.requireView())

    init {
        Timber.i("InjectedStringParam=$injectedParam, InjectedParcelable=$injectedParamHolder (from $this), InjectedInt = $injectedInteger")
    }

    fun doSomething() {
        binding.hello.text = "Hello from $this"
        binding.button.setOnClickListener {
            viewModel.useHelper()
        }
        binding.buttonRouter.setOnClickListener {
            viewModel.triggerContentRouter()
        }

        binding.input.setOnFocusChangeListener { v, hasFocus ->
            Timber.i("Focus change on disneyinput $hasFocus (view is $v)")
        }

        val flow = binding.profilesFlowHelper

        val lastElement = ColorBox(context = fragment.requireContext()).also {
            it.setBgColor(Color.DKGRAY)
            it.setText("Dark Gray Color with long name")
            it.id = View.generateViewId()
        }
        val dimension = fragment.resources.getDimension(R.dimen.colorview_width)
        val horizontalGap = fragment.resources.getDimension(R.dimen.flow_horizontal_gap)
        binding.flowRoot.doOnLayout {
            val maxWidth = binding.flowRoot.width

            val combinedWidth = dimension + horizontalGap
            var remainingWidth = maxWidth - dimension
            var items = 1
            while (remainingWidth > (combinedWidth)) {
                remainingWidth -= (combinedWidth)
                items++
            }
            Timber.w("Items per row are $items")


            val views = listOf(
                ColorBox(context = fragment.requireContext()).also {
                    it.setBgColor(Color.MAGENTA)
                    it.setText("Magenta Color")
                    it.id = View.generateViewId()
                },
                ColorBox(context = fragment.requireContext()).also {
                    it.setBgColor(Color.BLUE)
                    it.setText("Blue Color")
                    it.id = View.generateViewId()
                },
                ColorBox(context = fragment.requireContext()).also {
                    it.setBgColor(Color.GREEN)
                    it.setText("Green Color")
                    it.id = View.generateViewId()
                },
                ColorBox(context = fragment.requireContext()).also {
                    it.setBgColor(Color.RED)
                    it.setText("Red Color")
                    it.id = View.generateViewId()
                },
                ColorBox(context = fragment.requireContext()).also {
                    it.setBgColor(Color.YELLOW)
                    it.setText("Yellow Color")
                    it.id = View.generateViewId()
                },
                ColorBox(context = fragment.requireContext()).also {
                    it.setBgColor(Color.GRAY)
                    it.setText("Gray Color with long name")
                    it.id = View.generateViewId()
                },
                lastElement,
            )
            val previousViews = binding.flowRoot.children.filter { it is Flow }.toList()
            flow.updateViews(views, emptyList())
        }
    }
}

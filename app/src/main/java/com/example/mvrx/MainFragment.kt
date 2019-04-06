package com.example.mvrx


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.mvrx.BaseMvRxFragment
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState

import kotlinx.android.synthetic.main.fragment_main.*


data class HelloWorldState(
    val title: String = "Hello World!",
    val count: Int = 0
) : MvRxState {
    val titleWithCount = "$title - $count"
}

class HelloWorldViewModel(initialState: HelloWorldState) : MvRxViewModel<HelloWorldState>(initialState) {
    fun incrementCount() = setState { copy(count = count + 1) }

}

class MainFragment : BaseMvRxFragment() {


    private val viewModel: HelloWorldViewModel by fragmentViewModel()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun invalidate() = withState(viewModel) { state ->
        titleTxt.text = state.titleWithCount
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = withState(viewModel) { state ->
        titleTxt.setOnClickListener {
            viewModel.incrementCount()
        }
    }
}

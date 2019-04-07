package com.example.mvrx


import android.os.Bundle
import android.util.Log
import android.util.Log.println
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.airbnb.mvrx.*
import io.reactivex.Observable

import kotlinx.android.synthetic.main.fragment_main.*
import java.util.concurrent.TimeUnit


data class HelloWorldState(
    val title: String = "Hello World!",
    val count: Int = 0
) : MvRxState {
    val titleCount: String = "$title $count"
}

class HelloWorldViewModel(initialState: HelloWorldState) : MvRxViewModel<HelloWorldState>(initialState) {

    /*init {

        asyncSubscribe(HelloWorldState::temperature, onSuccess = {
            println(Log.DEBUG, "temprature Async", "temprature :$it")
        },
            onFail = {
                println(Log.DEBUG, "temprature failed", it.localizedMessage)
            }
        )
    }*/

    fun fetchCount() = setState { copy(count = count + 1) }
        /*Observable.just(72)
            .delay(3, TimeUnit.SECONDS)
            .execute { copy(temperature = it) }*/



}

class MainFragment : BaseMvRxFragment() {

    private val viewModel: HelloWorldViewModel by fragmentViewModel()

    override fun invalidate() = withState(viewModel) {
        titleTxt.text = it.count.toString()
        /*when (it.count) {
            is Uninitialized -> "Click to load Weather"
            is Loading -> "Loading"
            is Success -> "Weather: ${it.titleCount} degrees"
            else -> "Sorry, its else"
        }*/
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        titleTxt.setOnClickListener {
            viewModel.fetchCount()
        }
    }
}

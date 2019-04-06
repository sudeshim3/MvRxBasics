### Data Class as State

```
data class StateModel(
    val var1: Async<Int> = Uninitialized
) : MvRxState
```
### ViewModel Using State
contains async subscription and observer functrions 
```
class ViewModel(initialState: StateModel) : MvRxViewModel<StateModel>(initialState) {

    init {

        asyncSubscribe(StateModel::var1, onSuccess = {
            println(Log.DEBUG, "Async", "var1 :$it")
        },
            onFail = {
                println(Log.DEBUG, "failed", it.localizedMessage)
            }
        )
    }

    fun fetchVar1() {
        Observable.just(72)
            .delay(3, TimeUnit.SECONDS)
            .execute { copy(temperature = it) }
    }
}
```
### Fragemnt Extending BaseMvRxFragment

```
class Fragment : BaseMvRxFragment() {

    private val viewModel: ViewModel by fragmentViewModel()

    override fun invalidate() = withState(viewModel) {
        titleTxt.text = when (it.var1) {
            is Uninitialized -> "Unitialized"
            is Loading -> "Loading"
            is Success -> "Successfully loaded"
            else -> "Unknown error"
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }
}
```
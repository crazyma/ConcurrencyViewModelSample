package com.crazyma.concurrencyviewmodelsample

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AfterPreviousViewModel: CustomViewModel()  {
    val result = SingleLiveEvent<Int>()

    private val singleRunner = ConcurrencyHelper.SingleRunner()

    fun runTest(delayTime: Int) {

        viewModelScope.launch(Dispatchers.IO) {
            Log.i("badu", "[Queue] run test with $delayTime seconds delayed}")
            singleRunner.afterPrevious {
                task(delayTime)
            }
        }

    }

    private suspend fun task(delayTime: Int) {
        delay(delayTime * 1000L)
        Log.v("badu", "[Queue] end delayed after $delayTime seconds}")
        result.postValue(delayTime)
    }
}
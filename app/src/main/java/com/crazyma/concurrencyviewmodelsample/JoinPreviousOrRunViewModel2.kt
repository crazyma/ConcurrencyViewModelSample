package com.crazyma.concurrencyviewmodelsample

import android.util.Log
import kotlinx.coroutines.*
import java.lang.RuntimeException

class JoinPreviousOrRunViewModel2 : CustomViewModel() {

    val result = SingleLiveEvent<Int>()

    private val controlledRunner = ConcurrencyHelper.ControlledRunner<Unit>()

    fun runTest(delayTime: Int) {

        viewModelScope.launch(Dispatchers.IO) {
            Log.i("badu", "[Handler First] run test with $delayTime seconds delayed}")
            controlledRunner.customJoinPreviousOrRun {
                task(delayTime)
            }
        }

    }

    fun runTest2(){
        launchTest2(-1)
        launchTest2(-2)
        launchTest2(-3)
    }

    private fun launchTest2(errorCode: Int){
        viewModelScope.launch(Dispatchers.IO) {
            Log.v("badu", "launch CoroutineScope for test 2 (A)")
            try {
                controlledRunner.customJoinPreviousOrRun {
                    val number = failTask(errorCode)
                    result.postValue(number)
                }
            }catch (t: Throwable){
                result.postValue(-100)
            }
        }
    }

    private suspend fun task(delayTime: Int) {
        delay(delayTime * 1000L)
        Log.v("badu", "[Handler First] end delayed after $delayTime seconds}")
        result.postValue(delayTime)
    }

    private suspend fun failTask(errorCode: Int = 0): Int{
        delay(3 * 1000L)
        throw RuntimeException("XD ($errorCode)")
        return 5566
    }


}
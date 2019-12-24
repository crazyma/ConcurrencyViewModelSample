package com.crazyma.concurrencyviewmodelsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

//    private lateinit var joinPreviousOrRunViewModel: JoinPreviousOrRunViewModel
    private lateinit var afterPreviousViewModel: AfterPreviousViewModel
    private lateinit var cancelPreviousViewModel: CancelPreviousViewModel
    private lateinit var joinPreviousOrRunViewModel2: JoinPreviousOrRunViewModel2

    private var handleFirstButtonDelay = 3
    private var queueButtonDelay = 3
    private var handleLastButtonDelay = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        joinPreviousOrRunViewModel = ViewModelProviders.of(this).get(JoinPreviousOrRunViewModel::class.java)
        afterPreviousViewModel = ViewModelProviders.of(this).get(AfterPreviousViewModel::class.java)
        cancelPreviousViewModel = ViewModelProviders.of(this).get(CancelPreviousViewModel::class.java)
        joinPreviousOrRunViewModel2 = ViewModelProviders.of(this).get(JoinPreviousOrRunViewModel2::class.java)

        setupViewModel()
        setupButton()
    }

    private fun setupViewModel() {
//        joinPreviousOrRunViewModel.result.observe(this@MainActivity, Observer {
//            Log.d("badu", "[Handler First] get result after $it seconds}")
//        })

        joinPreviousOrRunViewModel2.result.observe(this, Observer {
            Log.d("badu", "joinPreviousOrRunViewModel2.result: $it")
        })

        afterPreviousViewModel.result.observe(this, Observer {
            Log.d("badu", "[Queue] get result after $it seconds}")
        })

        cancelPreviousViewModel.result.observe(this, Observer {
            Log.d("badu", "[Handle Last] get result after $it seconds}")
        })
    }

    private fun setupButton() {
        handleFirstButton.setOnClickListener {
            joinPreviousOrRunViewModel2.runTest(handleFirstButtonDelay)
            handleFirstButtonDelay += 2
        }

        queueButton.setOnClickListener {
            afterPreviousViewModel.runTest(queueButtonDelay)
            queueButtonDelay += 2
        }

        handleLastButton.setOnClickListener {
            cancelPreviousViewModel.runTest(handleLastButtonDelay)
            handleLastButtonDelay += 2
        }

        failTestButton.setOnClickListener {
            joinPreviousOrRunViewModel2.runTest2()
        }
    }
}

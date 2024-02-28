package com.example.hilt

import MyRecyclerViewAdapter
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MayActivity : AppCompatActivity() {

    @Inject
    lateinit var api: ApiInterface

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val adapter = MyRecyclerViewAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        coroutineScope.launch {
            api.getHeroes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isNotEmpty()) {
                        val heroes = it
                        adapter.heroes = heroes.toMutableList()
                        adapter.notifyDataSetChanged()
                    }
                }, {
                    Toast.makeText(this@MayActivity, "Error -> $it", Toast.LENGTH_SHORT).show()
                })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }

    override fun onResume() {
        super.onResume()
        coroutineScope.launch {
            Toast.makeText(this@MayActivity, "Comeback =)", Toast.LENGTH_SHORT).show()
        }
    }
}

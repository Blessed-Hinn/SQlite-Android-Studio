package com.benny.sqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class DisplayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)

        val swipeRefresh: SwipeRefreshLayout = findViewById(R.id.swipeLayout)
        val rvProducts: RecyclerView = findViewById(R.id.recyclerViewProducts)

        //Initialize Database
        val db = DBHelper(this, null)

        //Creating an array List - Hold The Data
        val productList = ArrayList<Product>()

        //Gets the data once fetched
        val cursor = db.getProducts()

        while (cursor!!.moveToNext()) {
            val id = cursor.getInt(0)
            val name = cursor.getString(1)
            val quantity = cursor.getInt(2)
            val price = cursor.getInt(3)
            val product = Product(id, name, quantity, price)
            productList.add(product)
        }

        rvProducts.layoutManager = LinearLayoutManager(this)

        val adapter = CustomAdapter(productList)

        rvProducts.adapter = adapter

        swipeRefresh.setOnRefreshListener {
            productList.clear()
            val cursor = db.getProducts()
            while (cursor!!.moveToNext()) {
                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                val quantity = cursor.getInt(2)
                val price = cursor.getInt(3)
                val product = Product(id, name, quantity, price)
                productList.add(product)
            }

            adapter.notifyDataSetChanged()
            swipeRefresh.isRefreshing =  false

        }
    }
}
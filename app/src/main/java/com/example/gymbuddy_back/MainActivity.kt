package com.example.gymbuddy_back

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class MainActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var rvGymList: RecyclerView
    private lateinit var tvEmptyMessage: TextView
    private lateinit var gymAdapter: GymAdapter
    private var listenerRegistration: ListenerRegistration? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 툴바 설정
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Firestore 초기화
        firestore = FirebaseFirestore.getInstance()

        // UI 요소 초기화
        rvGymList = findViewById(R.id.rvGymList)
        tvEmptyMessage = findViewById(R.id.tvEmptyMessage)

        // RecyclerView 설정
        rvGymList.layoutManager = LinearLayoutManager(this)
        gymAdapter = GymAdapter(mutableListOf())
        rvGymList.adapter = gymAdapter

        // Firestore에서 실시간 데이터 가져오기
        loadGymData()
    }

    private fun loadGymData() {
        firestore.collection("gyms")
            .addSnapshotListener { querySnapshot, error ->
                if (error != null) {
                    tvEmptyMessage.text = "데이터를 불러오는 중 오류가 발생했습니다."
                    tvEmptyMessage.visibility = TextView.VISIBLE
                    rvGymList.visibility = RecyclerView.GONE
                    return@addSnapshotListener
                }

                val gymList = querySnapshot?.documents?.mapNotNull { it.toObject(Gym::class.java) } ?: emptyList()
                if (gymList.isEmpty()) {
                    tvEmptyMessage.visibility = TextView.VISIBLE
                    rvGymList.visibility = RecyclerView.GONE
                } else {
                    tvEmptyMessage.visibility = TextView.GONE
                    rvGymList.visibility = RecyclerView.VISIBLE
                    gymAdapter.updateData(gymList)
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Firestore 리스너 해제
        listenerRegistration?.remove()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu) // 메뉴 리소스를 툴바에 적용
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_register -> {
                // 등록 버튼 클릭 시 RegisterGymActivity로 이동
                val intent = Intent(this, RegisterGymActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}

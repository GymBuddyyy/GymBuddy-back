package com.example.gymbuddy_back

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.google.firebase.database.DatabaseReference

class ReservationActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var datePicker: DatePicker
    private lateinit var timePicker: TimePicker
    private lateinit var trainerSpinner: Spinner
    private lateinit var reserveButton: Button
    private lateinit var reservedTimesText: TextView
    private var selectedTrainer: String = ""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation)

        database = FirebaseDatabase.getInstance().reference

        datePicker = findViewById(R.id.datePicker)
        timePicker = findViewById(R.id.timePicker)
        trainerSpinner = findViewById(R.id.trainerSpinner)
        reserveButton = findViewById(R.id.reserveButton)
        reservedTimesText = findViewById(R.id.reservedTimesText)

        // 트레이너 목록 로드
        loadTrainers()

        // 트레이너 선택 시 예약된 시간 표시
        trainerSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedTrainer = parent?.getItemAtPosition(position).toString()
                loadReservedTimes()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // 날짜 선택 시 예약된 시간 업데이트
        datePicker.setOnDateChangedListener { _, _, _, _ -> loadReservedTimes() }

        // 예약 버튼 클릭
        reserveButton.setOnClickListener {
            reserveTime()
        }
    }

    private fun loadTrainers() {
        val trainers = listOf("trainer1", "trainer2", "trainer3") // 실제 Firebase에서 가져와야 함
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, trainers)
        trainerSpinner.adapter = adapter
    }

    private fun loadReservedTimes() {
        val year = datePicker.year
        val month = datePicker.month + 1
        val day = datePicker.dayOfMonth
        val selectedDate = "$year-$month-$day"

        // 휴무일 체크
        database.child("trainer_off_days").child(selectedTrainer).child(selectedDate)
            .get().addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    reservedTimesText.text = "이 날짜는 트레이너의 휴무일입니다."
                    reserveButton.isEnabled = false
                } else {
                    // 예약된 시간 불러오기
                    database.child("reservations").child(selectedTrainer).child(selectedDate)
                        .get().addOnSuccessListener { reservationSnapshot ->
                            if (reservationSnapshot.exists()) {
                                val reservedTimes = reservationSnapshot.children.map { it.value.toString() }
                                reservedTimesText.text = "예약된 시간: ${reservedTimes.joinToString(", ")}"
                            } else {
                                reservedTimesText.text = "예약 가능"
                            }
                            reserveButton.isEnabled = true
                        }
                }
            }
    }

    private fun reserveTime() {
        val year = datePicker.year
        val month = datePicker.month + 1
        val day = datePicker.dayOfMonth
        val selectedDate = "$year-$month-$day"
        val selectedTime = "${timePicker.hour}:${timePicker.minute}"

        database.child("reservations").child(selectedTrainer).child(selectedDate)
            .child(selectedTime).setValue(true).addOnSuccessListener {
                Toast.makeText(this, "예약 완료: $selectedDate $selectedTime", Toast.LENGTH_SHORT).show()
                loadReservedTimes()
            }
    }
}

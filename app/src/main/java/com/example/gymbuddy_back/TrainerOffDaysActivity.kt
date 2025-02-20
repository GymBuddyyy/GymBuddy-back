package com.example.gymbuddy_back

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gymbuddy_back.db.entity.TrainerOffDay


class TrainerOffDaysActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_trainer_off_days)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val datePicker: DatePicker = findViewById(R.id.datePicker)
        val timeLabel: View = findViewById(R.id.timeLabel)
        val startTimePicker: TimePicker = findViewById(R.id.startTimePicker)
        val endTimePicker: TimePicker = findViewById(R.id.endTimePicker)
        val saveButton: Button = findViewById(R.id.saveButton)

        // 초기 상태: 시간 선택 UI 숨기기
        startTimePicker.visibility = View.GONE
        endTimePicker.visibility = View.GONE
        timeLabel.visibility = View.GONE

        // 날짜 선택 시 시간 선택 UI 표시
        datePicker.setOnDateChangedListener { _, year, month, dayOfMonth ->
            // 날짜가 선택되면 시간을 선택할 수 있게 표시
            startTimePicker.visibility = View.VISIBLE
            endTimePicker.visibility = View.VISIBLE
            timeLabel.visibility = View.VISIBLE
        }



        saveButton.setOnClickListener {
            val day = datePicker.dayOfMonth
            val month = datePicker.month + 1  // 월은 0부터 시작하므로 +1
            val year = datePicker.year

            val startHour = startTimePicker.hour
            val startMinute = startTimePicker.minute
            val endHour = endTimePicker.hour
            val endMinute = endTimePicker.minute

            // YYYY-MM-DD 형식의 날짜 문자열 생성
            val selectedDate = String.format("%04d-%02d-%02d", year, month, day)

            // HH:mm-HH:mm 형식의 시간 문자열 생성
            val selectedTime = String.format("%02d:%02d-%02d:%02d", startHour, startMinute, endHour, endMinute)

            // Room DB에 저장
            val trainerId = 1L  // 예제 트레이너 ID
            val offDay = TrainerOffDay(trainerId = trainerId, date = selectedDate, time = selectedTime)

            //trainerOffDayViewModel.insertTrainerOffDay(offDay)

            Toast.makeText(this, "저장 완료: $selectedDate, 시간: $selectedTime", Toast.LENGTH_SHORT).show()
        }

    }
}

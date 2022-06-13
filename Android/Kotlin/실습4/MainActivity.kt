package com.example.room
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.room.databinding.ActivityMainBinding
import kotlinx.coroutines.runBlocking
import java.lang.Integer.parseInt
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var myDao: MyDAO
    private var mData=ArrayList<String>()
    private var selected=false
    private lateinit var select_number:String
    private lateinit var select_name:String
    lateinit var adapter:ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myDao = MyDatabase.getDatabase(this).getMyDao()
        runBlocking {
            with(myDao) {
                insertStudent(Student(1, "james"))
                insertStudent(Student(2, "john"))
                insertClass(ClassInfo(1, "c-lang", "Mon 9:00", "E301", 1))
                insertClass(ClassInfo(2, "android prog", "Tue 9:00", "E302", 1))
                insertEnrollment(Enrollment(1, 1))
                insertEnrollment(Enrollment(1, 2))
            }
        }


        val allStudents = myDao.getAllStudents()
        allStudents.observe(this) {
            mData.clear()
            for ((id, name) in it) {
                mData.add("$id - $name\n")
            }
            adapter=ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mData)
            binding.textStudentList.adapter=adapter
        }

        binding.textStudentList.setOnItemClickListener { adapterView, view, i, l ->
            selected = true
            //binding.textQueryStudent.text = mData[i]
            val arr = mData[i].split(" ")
            select_number = arr[0]
            select_name = arr[2]

            runBlocking {
                val results = myDao.getStudentsWithEnrollment(parseInt(select_number))
                if (results.isNotEmpty()) {
                    val str = StringBuilder().apply {
                        append(results[0].student.id)
                        append("-")
                        append(results[0].student.name)
                        append(":")
                        for (c in results[0].enrollments) {
                            append(c.cid)
                            val cls_result = myDao.getClassInfo(c.cid)
                            if (cls_result.isNotEmpty())
                                append("(${cls_result[0].name})")
                            append(",")
                        }
                    }
                    binding.textQueryStudent.text = str
                } else {
                    binding.textQueryStudent.text = ""
                }
            }
        }
        binding.queryStudent.setOnClickListener {
            new_list()
        }

        binding.addStudent.setOnClickListener {
            val id = binding.editStudentId.text.toString().toInt()
            val name = binding.editStudentName.text.toString()
            if (id > 0 && name.isNotEmpty()) {
                runBlocking {
                    myDao.insertStudent(Student(id, name))
                }
            }
        }

        binding.enroll.setOnClickListener {
            if(selected){
                val random = Random
                val num = random.nextInt(2)+1
                runBlocking {
                    myDao.insertEnrollment(Enrollment(parseInt(select_number), num))
                }
                new_list()
            }
        }

        binding.delete.setOnClickListener {
            if(selected){
                runBlocking {
                    myDao.deleteEnrollment(parseInt(select_number))
                    myDao.deleteStudent(Student(parseInt(select_number), select_name))
                }
                new_list()
            }
        }

    }
    fun new_list(){
        val id = binding.editStudentId.text.toString().toInt()
        runBlocking {
            val results = myDao.getStudentsWithEnrollment(id)
            if (results.isNotEmpty()) {
                val str = StringBuilder().apply {
                    append(results[0].student.id)
                    append("-")
                    append(results[0].student.name)
                    append(":")
                    for (c in results[0].enrollments) {
                        append(c.cid)
                        val cls_result = myDao.getClassInfo(c.cid)
                        if (cls_result.isNotEmpty())
                            append("(${cls_result[0].name})")
                        append(",")
                    }
                }
                binding.textQueryStudent.text = str
            } else {
                binding.textQueryStudent.text = ""
            }
        }
    }
}


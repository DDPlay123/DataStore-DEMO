package com.tutorial.datastore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.tutorial.datastore.databinding.ActivityMainBinding
/*
    存放資料:
    String:  字串
    Int:     5
    Long:    500000L
    Boolean: true
    Float:   3.14f
    Double:  2.7182818284
 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var dataStore: DataStoreManager
    private var toast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.root)

        init()
        setListener()
    }

    private fun init() {
        binding.run {
            val type = resources.getStringArray(R.array.type)
            val adapter = ArrayAdapter(applicationContext, R.layout.item_type, type)
            tvInputHint.setAdapter(adapter)
            tvOutputHint.setAdapter(adapter)

            dataStore = DataStoreManager(applicationContext)
        }
    }

    private fun setListener() {
        binding.run {
            // 輸入
            btnInput.setOnClickListener {
                when (tvInputHint.text.toString()) {
                    "String"  -> dataStore.putData("String", "字串")
                    "Int"     -> dataStore.putData("Int", 5)
                    "Long"    -> dataStore.putData("Long", 500000)
                    "Boolean" -> dataStore.putData("Boolean", true)
                    "Float"   -> dataStore.putData("Float", 3.14f)
                    "Double"  -> dataStore.putData("Double", 2.7182818284)
                    else -> return@setOnClickListener
                }
                showPrompt("儲存成功")
            }

            // 清空
            btnClear.setOnClickListener {
                dataStore.clearData()
                showPrompt("清除成功")
            }

            // 輸出
            btnOutput.setOnClickListener {
                when (tvOutputHint.text.toString()) {
                    "String"  -> output.text = getString(R.string.output).plus(dataStore.getData("String", ""))
                    "Int"     -> output.text = getString(R.string.output).plus(dataStore.getData("Int", 1))
                    "Long"    -> output.text = getString(R.string.output).plus(dataStore.getData("Long", 1L))
                    "Boolean" -> output.text = getString(R.string.output).plus(dataStore.getData("Boolean", false))
                    "Float"   -> output.text = getString(R.string.output).plus(dataStore.getData("Float", 0.11f))
                    "Double"  -> output.text = getString(R.string.output).plus(dataStore.getData("Double", 0.1111111111111))
                    else -> return@setOnClickListener
                }
                showPrompt("輸出成功")
            }
        }
    }

    private fun showPrompt(msg: String) {
        toast?.cancel()
        toast = Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT)
        toast?.show()
    }
}
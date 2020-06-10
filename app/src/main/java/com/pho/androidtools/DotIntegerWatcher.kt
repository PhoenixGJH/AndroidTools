package com.pho.androidtools

import android.text.Editable
import android.text.TextWatcher

/**
 * 限制最大值以及小数点位数，默认有小数点的情况
 * 如果不需要小数点，直接在EditText中设置 android:digits="0123456789" 即可
 * EditText必须设置 android:inputType="numberDecimal"
 */
class DotIntegerWatcher : TextWatcher {
    private var maxValue = 100 //允许输入的最大值
    private var floatCount = 2 //小数点后保留的位数

    constructor()

    constructor(maxValue: Int, floatCount: Int) {
        this.maxValue = maxValue
        this.floatCount = floatCount
    }

    override fun afterTextChanged(s: Editable?) {
        if (s != null && s.isNotEmpty()) {
            if (s[0] == '0' && s.length > 1) {
                if (s[1] != '.') {
                    s.delete(1, s.length)
                }
            }
            val str = s.toString()
            val dotIndex = str.indexOf('.')

            if (dotIndex == 0) { //小数点在起始位置
                s.replace(0, 1, "0.")
            } else if (dotIndex != -1) { //小数点在正常位置
                if (s.length - 1 - dotIndex > floatCount) {
                    s.delete(dotIndex + floatCount + 1, s.length)
                }
            }

            val markVal: Double = try {
                s.toString().toDouble()
            } catch (e: NumberFormatException) {
                0.0
            }
            if (markVal > maxValue) {
                s.replace(0, s.length, maxValue.toString())
            }
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

}
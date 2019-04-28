package com.wuqi.a_service.services

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Vibrator





/**
 *
 * @author wuqi by 2019/4/23.
 */
abstract class ShakeSensor(val context: Context) : SensorEventListener {
    // 每隔一个时间段获取一个采样点：100毫秒
    // 三个轴的加速值获取
    // 计算增量（对于第一个采样点：无增量计算）
    // 统计每次三个轴上的增量，得到一个三个轴总增量
    // 将每次统计的增量进行累加
    // 当累加的值大于200——玩家在摇晃手机——生产一注彩票（随机）

    var lastTime: Long = 0
    var lastX = 0f// 记录上一个点x轴的加速度值
    var lastY = 0f// 记录上一个点y轴的加速度值
    var lastZ = 0f// 记录上一个点z轴的加速度值

    var shake = 0f// 相对于上一个点增量
    var totalShake = 0f// 每次增量汇总

    var switchValue = 200f// 判断手机是否摇晃的阈值

    var vibrator: Vibrator? = null// 震动处理

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent?) {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastTime > 100) {
            // 获取到三个轴的加速度值
            val x = event?.values?.get(SensorManager.DATA_X) ?: 0f
            val y = event?.values?.get(SensorManager.DATA_Y) ?: 0f
            val z = event?.values?.get(SensorManager.DATA_Z) ?: 0f
            // 当手机静止不动，会有微小的变动，当用户进入到双色球选号界面，过一个时间段自己选号
            // 是否可以计算增量
            if (0L === lastTime) {
                // 当第一个采样点，不需要计算
                lastX = x
                lastY = y
                lastZ = z
                lastTime = currentTime
            } else {
                // 第二个以后
                // 获取到相对于上一个点的增量,累积
                var ix = Math.abs(x - lastX)
                var iy = Math.abs(y - lastY)
                var iz = Math.abs(z - lastZ)
                //即使不摇晃的时候手机的传感器数值也会有细微的改变，我们不能让用户在买彩票的页面过一会就自动选彩票了，必须处理
                //就是让微小的变动都变成0
                if (ix < 1) {
                    ix = 0f
                }
                if (iy < 1) {
                    iy = 0f
                }
                if (iz < 1) {
                    iz = 0f
                }

                shake = ix + iy + iz// 如果手机静止不动，单次统计为零
                //当判断用户没有摇动手机，所有值恢复初始状态
                if (shake === 0F) {
                    init()
                }

                totalShake += shake

                if (totalShake > switchValue) {
                    // 生产一注：双色球选号界面：机选双色球
                    // 在福彩3D机选
                    createLottery()
                    // 告知用户：震动
                    vibrator()
                    // 回复到初始状态
                    init()
                } else {
                    lastX = x
                    lastY = y
                    lastZ = z
                    lastTime = currentTime
                }

            }
        }
    }

    private fun init() {
        lastTime = 0
        lastX = 0F// 记录上一个点x轴的加速度值
        lastY = 0F// 记录上一个点y轴的加速度值
        lastZ = 0F// 记录上一个点z轴的加速度值

        shake = 0F// 相对于上一个点增量
        totalShake = 0F// 每次增量汇总
    }

    @SuppressLint("MissingPermission")
    private fun vibrator() {
        vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator?.vibrate(100)
    }

    /**
     * 生成一注彩票
     */
    abstract fun createLottery()
}
package com.edu.uniandes.fud

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import java.util.Calendar

class AlarmNotification: BroadcastReceiver() {
	
	companion object {
		const val NOTIFICATION_ID = 1
	}
	
	override fun onReceive(context: Context, p1: Intent?) {
		createSimpleNotification(context)
	}
	
	@SuppressLint("ScheduleExactAlarm")
	fun scheduleNotification(context: Context) {
		val intent = Intent(context, AlarmNotification::class.java)
		val pendingIntent = PendingIntent.getBroadcast(
			context,
			NOTIFICATION_ID,
			intent,
			PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
		)
		
		val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
		
		// Set the alarm for 12 PM
		val calendar = Calendar.getInstance()
		calendar.apply {
			set(Calendar.HOUR_OF_DAY, 12)
			set(Calendar.MINUTE, 0)
			set(Calendar.SECOND, 0)
		}
		
		val triggerAtMillis = calendar.timeInMillis
		
		// Ensure that the alarm is set for a future time
		if (triggerAtMillis > System.currentTimeMillis()) {
			alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent)
		}
	}

	
	fun createChannel(context: Context) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			val channel = NotificationChannel(
				FuDApplication.MY_CHANNEL_ID,
				"MySuperChannel",
				NotificationManager.IMPORTANCE_DEFAULT
			).apply {
				description = "SUSCRIBETE"
			}
			
			val notificationManager: NotificationManager =
				context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
			
			notificationManager.createNotificationChannel(channel)
		}
	}
	
	private fun createSimpleNotification(context: Context) {
		val intent = Intent(context, FuDApplication::class.java).apply {
			flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
		}
		
		val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
		val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, flag)
		
		val notification = NotificationCompat.Builder(context, FuDApplication.MY_CHANNEL_ID)
			.setSmallIcon(android.R.drawable.ic_delete)
			.setContentTitle("FuD Lunch Time")
			.setContentText("Es hora de almorzar!!!")
			.setStyle(
				NotificationCompat.BigTextStyle()
					.bigText("Te traemos una oferta variada de restaurantes para tu almuerzo")
			)
			.setContentIntent(pendingIntent)
			.setPriority(NotificationCompat.PRIORITY_DEFAULT)
			.build()
		
		val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
		manager.notify(NOTIFICATION_ID, notification)
	}
}

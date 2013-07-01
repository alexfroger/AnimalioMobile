package fr.library.animaliomobile;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import fr.activity.animaliomobile.Authentication;
import fr.animaliomobile.R;

public class CustomNotification{
	private int notifyId = 1;
	private Context currentClass;
	private String notificationTitle;
	private String notificationDesc;
	private NotificationManager notificationManager;

	public CustomNotification(NotificationManager _notificationManager, Context _currentClass, String _notificationTitle,
			String _notificationDesc) {
		super();
		this.currentClass = _currentClass;
		this.notificationTitle = _notificationTitle;
		this.notificationDesc = _notificationDesc;
		this.notificationManager = _notificationManager;
	}

	// Add app running notification
	public void addNotification() {
		
		// Prepare intent which is triggered if the
		// notification is selected
		Intent intent = new Intent(this.currentClass, Authentication.class);
		PendingIntent pIntent = PendingIntent.getActivity(this.currentClass, 0, intent, 0);

		// Build notification
		NotificationCompat.Builder noti = new NotificationCompat.Builder(this.currentClass)
				.setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle(this.notificationTitle)
				.setContentText(this.notificationDesc)
				.setTicker(this.notificationTitle)
				.setAutoCancel(true)
				.setVibrate(new long[] {200,300,400,500})
				.setContentIntent(pIntent)
				.setNumber(10)
				.setOngoing(true)
				.setOnlyAlertOnce(true);

		notificationManager.notify(this.notifyId, noti.build());

		this.notifyId++;
	}
}

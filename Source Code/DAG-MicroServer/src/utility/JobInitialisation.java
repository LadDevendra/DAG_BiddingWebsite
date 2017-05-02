package utility;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class JobInitialisation implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		Timer t = new Timer();
	     SellJob mTask = new SellJob();
	     // This task is scheduled to run every
	   //180000 miliseconds - 3 min
	     t.scheduleAtFixedRate(mTask, 0, 180000);
	}

}

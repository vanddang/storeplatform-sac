package com.skplanet.storeplatform.sac.other.sacservice.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.skplanet.storeplatform.sac.other.sacservice.vo.SacServiceSchedule;

public class SacServiceUitlsTest {
	
	private static List<SacServiceSchedule> scheduleList1;
	private static List<SacServiceSchedule> scheduleList2;
	
	@BeforeClass
	public static void beforeClass() {
		Calendar startCalendar;
		Calendar endCalendar;
		
		System.out.println("============ Now ============");
		System.out.println(new Date());
		
		scheduleList1 = new ArrayList<SacServiceSchedule>();
		
		startCalendar = Calendar.getInstance(); 
		endCalendar = Calendar.getInstance();
		startCalendar.add(Calendar.HOUR_OF_DAY, -10);
		endCalendar.add(Calendar.HOUR_OF_DAY, -5);
		scheduleList1.add(new SacServiceSchedule(1, startCalendar.getTime(), endCalendar.getTime()));
		
		startCalendar = Calendar.getInstance(); 
		endCalendar = Calendar.getInstance();
		startCalendar.add(Calendar.HOUR_OF_DAY, -5);
		endCalendar.add(Calendar.HOUR_OF_DAY, 5);
		scheduleList1.add(new SacServiceSchedule(1, startCalendar.getTime(), endCalendar.getTime()));
		
		startCalendar = Calendar.getInstance(); 
		endCalendar = Calendar.getInstance();
		startCalendar.add(Calendar.HOUR_OF_DAY, 5);
		endCalendar.add(Calendar.HOUR_OF_DAY, 10);
		scheduleList1.add(new SacServiceSchedule(1, startCalendar.getTime(), endCalendar.getTime()));
		
		System.out.println("============ scheduleList1 ============");
		System.out.println(scheduleList1);
		
		scheduleList2 = new ArrayList<SacServiceSchedule>();
		
		startCalendar = Calendar.getInstance(); 
		endCalendar = Calendar.getInstance();
		startCalendar.add(Calendar.HOUR_OF_DAY, -10);
		endCalendar.add(Calendar.HOUR_OF_DAY, -5);
		scheduleList2.add(new SacServiceSchedule(1, startCalendar.getTime(), endCalendar.getTime()));
		
		startCalendar = Calendar.getInstance(); 
		endCalendar = Calendar.getInstance();
		startCalendar.add(Calendar.HOUR_OF_DAY, -5);
		endCalendar.add(Calendar.HOUR_OF_DAY, -1);
		scheduleList2.add(new SacServiceSchedule(1, startCalendar.getTime(), endCalendar.getTime()));
		
		startCalendar = Calendar.getInstance(); 
		endCalendar = Calendar.getInstance();
		startCalendar.add(Calendar.HOUR_OF_DAY, 5);
		endCalendar.add(Calendar.HOUR_OF_DAY, 10);
		scheduleList2.add(new SacServiceSchedule(1, startCalendar.getTime(), endCalendar.getTime()));
		
		System.out.println("============ scheduleList2 ============");
		System.out.println(scheduleList2);
	}

	@Test
	public void containsSchedule() {
		assertTrue(SacServiceUtils.containsSchedule(scheduleList1));
		assertFalse(SacServiceUtils.containsSchedule(scheduleList2));
	}

}

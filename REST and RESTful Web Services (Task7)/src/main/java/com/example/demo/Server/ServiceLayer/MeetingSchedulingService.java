package com.example.demo.Server.ServiceLayer;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.Server.PersistenceLayer.MeetingSchedulingDataType;
import com.example.demo.Server.PersistenceLayer.MeetingSchedulingPersistence;

@Service
public class MeetingSchedulingService {

	private MeetingSchedulingPersistence persistenceLayer;

	public MeetingSchedulingService() {
		this.persistenceLayer = new MeetingSchedulingPersistence();
	}

	public String createMeeting(MeetingSchedulingDataType meeting) {
		return persistenceLayer.createMeeting(meeting);
	}

	public String addTimeToMeeting(String meetingID, String meetingTime) {
		return persistenceLayer.addTimeToMeeting(meetingID, meetingTime);
	}

	public MeetingSchedulingPersistence getDataBase() {
		return persistenceLayer;
	}

	public List<MeetingSchedulingDataType> retrieveAllMeeting() {
		return persistenceLayer.retrieveAllMeeting();
	}

	public MeetingSchedulingDataType retrieveMeetingByID(String meetingID) {
		return persistenceLayer.retrieveMeetingByID(meetingID);
	}

	public boolean deleteMeetingByID(String meetingID) {
		return persistenceLayer.deleteMeetingByID(meetingID);
	}

	public MeetingSchedulingDataType chooseTimeSlot(String meetingID, int timeIndex) {
		return persistenceLayer.chooseTimeSlot(meetingID, timeIndex);
	}

}

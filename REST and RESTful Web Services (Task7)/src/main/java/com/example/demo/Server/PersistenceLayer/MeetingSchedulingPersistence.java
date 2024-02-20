package com.example.demo.Server.PersistenceLayer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class MeetingSchedulingPersistence {

	private List<MeetingSchedulingDataType> meetingProposalDB;
	private int meetingSequentialID;

	public MeetingSchedulingPersistence() {
		meetingSequentialID = 0;
		meetingProposalDB = new ArrayList<>();
	}

	@PostConstruct
	private void initializeMeetings() {
		int amountOfRandomMeetings = 3;
		for (int i = 0; i < amountOfRandomMeetings; i++) {
			MeetingSchedulingDataType randomMeeting = new MeetingSchedulingDataType();
			randomMeeting.setMeetingProposals(genRandTimes());
			createMeeting(randomMeeting);
		}
	}

	public String createMeeting(MeetingSchedulingDataType meeting) {
		String meetingId = meetingSequentialID + "";
		meeting.setMeetingID(meetingId);
		meetingProposalDB.add(meeting);
		meetingSequentialID++;
		return meetingId;
	}

	public String addTimeToMeeting(String meetingID, String meetingTime) {
		for (var meeting : meetingProposalDB) {
			if (meetingID.equals(meeting.getMeetingID())) {
				meeting.getMeetingProposals().add(meetingTime);
				return meetingID;
			}
		}
		return null;
	}

	public List<MeetingSchedulingDataType> retrieveAllMeeting() {
		return this.meetingProposalDB;
	}

	public String printDataBase() {
		StringBuilder builder = new StringBuilder();
		for (var meeting : meetingProposalDB) {
			builder.append("MeetingID: ").append(meeting.getMeetingID()).append(", times: ");
			for (var meetingTimes : meeting.getMeetingProposals()) {
				builder.append(meetingTimes).append("\n");
			}
		}
		return builder.toString();
	}

	private List<String> genRandTimes() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy (HH:mm:ss)");
		Random random = new Random();
		List<String> randomTimes = new ArrayList<>();
		int amountOfTimes = 3;
		for (int i = 0; i < amountOfTimes; i++) {
			LocalDateTime randomDateTime = LocalDateTime.now().plusDays(random.nextInt(30))
					.plusHours(random.nextInt(24)).plusMinutes(random.nextInt(60)).plusSeconds(random.nextInt(60));
			String formattedDate = randomDateTime.format(formatter);
			randomTimes.add(formattedDate);
		}
		return randomTimes;
	}

	public MeetingSchedulingDataType retrieveMeetingByID(String meetingID) {
		for (MeetingSchedulingDataType meeting : meetingProposalDB) {
			if (meeting.getMeetingID().equals(meetingID)) {
				return meeting;
			}
		}
		return null;
	}

	public boolean deleteMeetingByID(String meetingID) {
		int index = 0;
		for (MeetingSchedulingDataType meeting : meetingProposalDB) {
			if (meeting.getMeetingID().equals(meetingID)) {
				meetingProposalDB.remove(index);
				return true;
			}
			index++;
		}
		return false;
	}

	public MeetingSchedulingDataType chooseTimeSlot(String meetingID, int timeIndex) {
		for (MeetingSchedulingDataType meeting : meetingProposalDB) {
			if (meeting.getMeetingID().equals(meetingID) && timeIndex < meeting.getMeetingProposals().size()) {
				meeting.setChosenTime(meeting.getMeetingProposals().get(timeIndex));
				return meeting;
			}
		}
		return null;
	}

}

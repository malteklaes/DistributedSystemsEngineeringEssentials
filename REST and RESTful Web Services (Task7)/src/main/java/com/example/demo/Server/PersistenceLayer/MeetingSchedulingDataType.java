package com.example.demo.Server.PersistenceLayer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MeetingSchedulingDataType {

	private String meetingID;
	private List<String> meetingProposals;
	private String chosenTime;

	public MeetingSchedulingDataType() {
		String defaultID = "0";
		this.meetingID = defaultID;
		meetingProposals = new ArrayList<String>();
	}

	public MeetingSchedulingDataType(List<String> meetingProposals) {
		String defaultID = "0";
		this.meetingID = defaultID;
		meetingProposals = new ArrayList<String>();
	}

	public MeetingSchedulingDataType(String meetingID, List<String> meetingProposals) {
		this.meetingID = meetingID;
		this.meetingProposals = meetingProposals;
	}

	public String getMeetingID() {
		return meetingID;
	}

	public void setMeetingID(String meetingID) {
		this.meetingID = meetingID;
	}

	public List<String> getMeetingProposals() {
		return meetingProposals;
	}

	public void setMeetingProposals(List<String> meetingProposals) {
		this.meetingProposals = meetingProposals;
	}

	public String getChosenTime() {
		return chosenTime;
	}

	public void setChosenTime(String chosenTime) {
		this.chosenTime = chosenTime;
	}

	public void appendTime(String meetingTimeProposal) {
		meetingProposals.add(meetingTimeProposal);
	}

	public void removeTime(int index) {
		this.meetingProposals.remove(index);
	}

	/**
	 * just for testing purpose
	 */
	public void addRandomTimeToMeetings() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy (HH:mm:ss)");
		Random random = new Random();
		LocalDateTime randomDateTime = LocalDateTime.now().plusDays(random.nextInt(30)).plusHours(random.nextInt(24))
				.plusMinutes(random.nextInt(60)).plusSeconds(random.nextInt(60));
		String formattedDate = randomDateTime.format(formatter);
		meetingProposals.add(formattedDate);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Meeting [id=").append(meetingID).append(", proposedTimes=");
		if (meetingProposals != null) {
			for (String proposedTime : meetingProposals) {
				builder.append("\n").append(proposedTime);
			}
		} else {
			builder.append("]");
		}
		return builder.toString();
	}

}

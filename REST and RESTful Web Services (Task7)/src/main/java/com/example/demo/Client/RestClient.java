package com.example.demo.Client;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.Server.EndpointLayer.ResponseEnvelope;
import com.example.demo.Server.PersistenceLayer.MeetingSchedulingDataType;

import reactor.core.publisher.Mono;

public class RestClient {

	private final String generalURL = "http://localhost:8080/api/scheduleMeetings";
	private WebClient.Builder wcBuilder;

	private List<String> createdMeetingIDs;

	public RestClient() {
		wcBuilder = WebClient.builder();
		createdMeetingIDs = new ArrayList<String>();
	}

	/**
	 * create a new meeting with one date
	 * 
	 * @return response from server (String)
	 */
	public String createMeeting() {
		String requestURL = generalURL + "/newMeeting";
		MeetingSchedulingDataType newMeeting = new MeetingSchedulingDataType();
		newMeeting.addRandomTimeToMeetings();
		newMeeting.addRandomTimeToMeetings();
		ResponseEnvelope response = wcBuilder.build().post().uri(requestURL)
				.body(Mono.just(newMeeting), MeetingSchedulingDataType.class).retrieve()
				.bodyToMono(ResponseEnvelope.class).block();
		this.createdMeetingIDs.add((String) response.getResponseData());
		return response.toString();
	}

	/**
	 * add time to an existing meeting with an ID (name)
	 * 
	 * @param meetingId (not null)
	 * @return response from server (String)
	 */
	public String addTimeToMeeting(int meetingID) {
		String proposedTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy (HH:mm:ss)"));
		String requestURL = generalURL + "/" + meetingID + "/meetingTimeProposal";
		ResponseEnvelope response = wcBuilder.build().post().uri(requestURL).body(Mono.just(proposedTime), String.class)
				.retrieve().bodyToMono(ResponseEnvelope.class).block();
		System.out
				.println("Proposed time for meeting: " + meetingID + " was successful? - " + response.getHttpStatus());
		return response.toString();
	}

	/**
	 * get specific meeting by given ID
	 * 
	 * @param meetingID (not null)
	 * @return response from server (String)
	 */
	public String getMeetingByID(int meetingID) {
		String requestURL = generalURL + "/" + meetingID;
		ResponseEnvelope response = wcBuilder.build().get().uri(requestURL).retrieve()
				.bodyToMono(ResponseEnvelope.class).block();
		return response.toString();
	}

	/**
	 * get all meetings from server
	 * 
	 * @return response from server (String)
	 */
	public String getAllMeeting() {
		String requestURL = generalURL + "/allMeetings";
		ResponseEnvelope response = wcBuilder.build().get().uri(requestURL).retrieve()
				.bodyToMono(ResponseEnvelope.class).block();
		return response.toString();
	}

	public String chooseTimeSlot(int meetingID, int timeIndex) {
		String requestURL = generalURL + "/chooseTimeSlot/" + meetingID + "/" + timeIndex;
		ResponseEnvelope response = wcBuilder.build().put().uri(requestURL).retrieve()
				.bodyToMono(ResponseEnvelope.class).block();
		return response.toString();
	}

	/**
	 * delete specific meeting by ID
	 * 
	 * @param meetingID (not null)
	 * @return response from server (String)
	 */
	public String deleteMeetingByID(int meetingID) {
		String requestURL = generalURL + "/deleteMeetingByID/" + meetingID;
		ResponseEnvelope response = wcBuilder.build().delete().uri(requestURL).retrieve()
				.bodyToMono(ResponseEnvelope.class).block();
		return response.toString();
	}

	private String printAllMeeting(List<MeetingSchedulingDataType> allRetrievedMeetings) {
		StringBuilder builder = new StringBuilder();
		for (MeetingSchedulingDataType meeting : allRetrievedMeetings) {
			builder.append("Meeting [id=").append(meeting.getMeetingID()).append("], proposedTimes={");
			for (String meetingProposals : meeting.getMeetingProposals()) {
				builder.append("\n").append(meetingProposals);
			}
			builder.append("}");
		}
		return builder.toString();
	}

}

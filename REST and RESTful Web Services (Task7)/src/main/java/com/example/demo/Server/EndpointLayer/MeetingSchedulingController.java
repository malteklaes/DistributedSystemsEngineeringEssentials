package com.example.demo.Server.EndpointLayer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Server.PersistenceLayer.MeetingSchedulingDataType;
import com.example.demo.Server.ServiceLayer.MeetingSchedulingService;

// endpoints for scheduleMeeting-App
/*
 User-story:
 First, you will design a simple RESTful HTTP API which enables a user to propose several meeting times, 
 remove and add times and publish a link of this doodle-like list encoded in JSON-format. 
 
 Users shall be able to choose zero or more preferred meeting times from this list. 
 
 CRUD
 
 */
@RestController
@RequestMapping("/api/scheduleMeetings")
public class MeetingSchedulingController {

	private MeetingSchedulingService service;

	@Autowired
	public MeetingSchedulingController(MeetingSchedulingService service) {
		this.service = service;
	}

	// @RequestMapping(path = "/newMeeting", method = RequestMethod.POST)
	@PostMapping("/newMeeting")
	public ResponseEnvelope createMeeting(@RequestBody MeetingSchedulingDataType meetingTime) {
		String createdMeetingID = service.createMeeting(meetingTime);
		return new ResponseEnvelope(createdMeetingID, EHTTPStatus.CREATED);
	}

	@PostMapping("/{id}/meetingTimeProposal")
	public ResponseEnvelope addTimeToMeeting(@PathVariable("id") Integer meetingID, @RequestBody String meetingTime) {
		String addedMeetingID = service.addTimeToMeeting(meetingID + "", meetingTime);
		if (addedMeetingID == null)
			return new ResponseEnvelope(EHTTPStatus.NOTFOUND.getStatusCode(), EHTTPStatus.NOTFOUND);
		else
			return new ResponseEnvelope(addedMeetingID, EHTTPStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEnvelope getMeetingByID(@PathVariable("id") Integer meetingID) {
		MeetingSchedulingDataType foundMeeting = service.retrieveMeetingByID(meetingID + "");
		if (foundMeeting == null)
			return new ResponseEnvelope(EHTTPStatus.NOTFOUND.getStatusCode(), EHTTPStatus.NOTFOUND);
		else
			return new ResponseEnvelope(foundMeeting, EHTTPStatus.OK);
	}

	@GetMapping("/allMeetings")
	public ResponseEnvelope getAllMeetings() {
		List<MeetingSchedulingDataType> allMeetings = service.retrieveAllMeeting();
		return new ResponseEnvelope(allMeetings, EHTTPStatus.OK);
	}

	@PutMapping("/chooseTimeSlot/{meetingID}/{timeIndex}")
	public ResponseEnvelope updateMeetingWithChosenTimeslot(@PathVariable("meetingID") Integer meetingID,
			@PathVariable("timeIndex") Integer timeIndex) {
		MeetingSchedulingDataType updatedMeeting = service.chooseTimeSlot(meetingID + "", timeIndex);

		if (updatedMeeting == null)
			return new ResponseEnvelope(EHTTPStatus.NOTMODIFIED.getStatusCode(), EHTTPStatus.NOTMODIFIED);
		else
			return new ResponseEnvelope(updatedMeeting, EHTTPStatus.OK);
	}

	@DeleteMapping("/deleteMeetingByID/{meetingId}")
	public ResponseEnvelope deleteMeeting(@PathVariable("meetingId") Integer meetingID) {
		boolean successfulMeetingDeletion = service.deleteMeetingByID(meetingID + "");
		if (successfulMeetingDeletion)
			return new ResponseEnvelope(meetingID, EHTTPStatus.OK);
		else
			return new ResponseEnvelope(EHTTPStatus.NOTFOUND.getStatusCode(), EHTTPStatus.NOTFOUND);
	}
}

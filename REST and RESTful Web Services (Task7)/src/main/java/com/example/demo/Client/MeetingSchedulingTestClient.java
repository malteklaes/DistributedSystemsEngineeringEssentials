package com.example.demo.Client;

import utils.Colors;

public class MeetingSchedulingTestClient {

	private static RestClient restClient;

	public static void main(String[] args) {
		restClient = new RestClient();
		System.out.println("Client requests...");

		// [1] POST create new meeting with an ID
		System.out.println(Colors.printHighlighted(Colors.GREEN,
				"\n--------------------------- post request (CREATE new meeting):"));
		System.out.println(
				Colors.printHighlighted(Colors.YELLOW, "[Client] (create post): ") + restClient.createMeeting());
		System.out.println(
				Colors.printHighlighted(Colors.YELLOW, "[Client] (create post): ") + restClient.createMeeting());

		// [2] POST (add time to meeting)
		System.out.println(Colors.printHighlighted(Colors.GREEN,
				"\n--------------------------- post request (ADD new time to meeting):"));
		System.out.println(Colors.printHighlighted(Colors.YELLOW, "[Client] (add new time post): ")
				+ restClient.addTimeToMeeting(0));
		System.out.println(Colors.printHighlighted(Colors.YELLOW, "[Client] (add new time post): ")
				+ restClient.addTimeToMeeting(42));

		// [3] GET (get meeting by ID)
		System.out.println(Colors.printHighlighted(Colors.GREEN,
				"\n--------------------------- GET request (get meeting by ID):"));
		System.out.println(Colors.printHighlighted(Colors.YELLOW, "[Client] (add new time post): ")
				+ restClient.getMeetingByID(0));
		System.out.println(Colors.printHighlighted(Colors.YELLOW, "[Client] (add new time post): ")
				+ restClient.getMeetingByID(13));

		// [4] GET (get all meetings)
		System.out.println(Colors.printHighlighted(Colors.GREEN,
				"\n--------------------------- GET ALL request (get all meetings):"));
		System.out.println(
				Colors.printHighlighted(Colors.YELLOW, "[Client] (add new time post): ") + restClient.getAllMeeting());

		// [5] PUT (choose specific timeslot (index)
		System.out.println(Colors.printHighlighted(Colors.GREEN,
				"\n--------------------------- PUT request (choose specific timeslot (index):"));
		System.out.println(Colors.printHighlighted(Colors.YELLOW, "[Client] (choose time slot): ")
				+ restClient.chooseTimeSlot(0, 0));
		System.out.println(Colors.printHighlighted(Colors.YELLOW,
				"[Client] (choose time slot, try to choose not avaiable meetingID): ")
				+ restClient.chooseTimeSlot(42, 0));
		System.out.println(Colors.printHighlighted(Colors.YELLOW,
				"[Client] (choose time slot, try to choose not avaible timeIndex from avaible meetingID): ")
				+ restClient.chooseTimeSlot(0, 42));

		// [6] DELETE (delete meeting by ID)
		System.out.println(Colors.printHighlighted(Colors.GREEN,
				"\n--------------------------- DELETE request (delete meeting by ID):"));
		System.out.println(Colors.printHighlighted(Colors.YELLOW, "[Client] (delete meeting successful?): ")
				+ restClient.deleteMeetingByID(0));
		System.out.println(Colors.printHighlighted(Colors.YELLOW, "[Client] (delete meeting successful?): ")
				+ restClient.deleteMeetingByID(42));

		System.out.println("\n...done");
	}

}

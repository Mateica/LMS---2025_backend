package model;

import java.time.LocalDateTime;

public class Message {
	private Long id;
	private LocalDateTime timePublished;
	private RegisteredUser sender;
	private RegisteredUser receiver;
	private File attachments;
}

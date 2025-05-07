package model;

import java.time.LocalDateTime;

public class Notification {
	private Long id;
	private LocalDateTime timePublished;
	private String content;
	private String title;
	private File attachments;
}

package com.adecco.mentenance.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@ConfigurationProperties("storage")
@ComponentScan
public class StorageProperties {

	/**
	 * Folder location for storing files
	 */
	private String tasksLocation = "tasks";

	public String getTasksLocation() {
		return tasksLocation;
	}
}

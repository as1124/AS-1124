package com.as1124.spring.framework.bean;

import com.as1124.spring.framework.IMedia;
import com.as1124.spring.framework.IMediaPlayer;

/**
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public class VideoPlayer implements IMediaPlayer {

	private IMedia video;

	public VideoPlayer() {
		// default constructor
	}

	public VideoPlayer(IMedia media) {
		this.video = media;
	}

	@Override
	public void startPlay() {
		if (video != null) {
			video.getMediaContent();
		}
	}

	public void setVideo(IMedia video) {
		this.video = video;
	}
}

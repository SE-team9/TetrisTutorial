package tetris;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioPlayer {
	private String soundsFolder = "tetrissounds" + File.separator;
	private String clearLinePath = soundsFolder + "line.wav";
	private String gameoverPath = soundsFolder + "success.wav";
	
	private Clip clearLineSound, gameoverSound;
	
	public AudioPlayer() {
		try {
			clearLineSound = AudioSystem.getClip();
			gameoverSound = AudioSystem.getClip();
			
			clearLineSound.open(AudioSystem.getAudioInputStream(new File(clearLinePath).getAbsoluteFile()));
			gameoverSound.open(AudioSystem.getAudioInputStream(new File(gameoverPath).getAbsoluteFile()));
			
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
	}
	
	public void playClearLine() {
		// 사운드 start 메소드가 반복되려면 프레임 위치를 0으로 지정해줘야 함.
		clearLineSound.setFramePosition(0); 
		clearLineSound.start();
	}
	
	public void playGameover() {
		gameoverSound.setFramePosition(0);
		gameoverSound.start();
	}
}

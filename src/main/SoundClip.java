package main;

import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
import javax.swing.*;

// To play sound using Clip, the process need to be alive.
// Hence, we use a Swing application.
public class SoundClip extends JFrame {
	public URL urls;
	public URL urlm;
	public URL urlc;
	public URL urln;

	// Constructor
	public SoundClip() {
		
			urls = this.getClass().getClassLoader().getResource("./sounds/assassins_creed_4.wav");
			urlm = this.getClass().getClassLoader().getResource("./sounds/morning.wav");
			urlc = this.getClass().getClassLoader().getResource("./sounds/combat.wav");
			urln = this.getClass().getClassLoader().getResource("./sounds/night.wav");
	}

		public void start(){
			try {
				// Open an audio input stream.
				
				AudioInputStream audioIn = AudioSystem.getAudioInputStream(this.urls);
				// Get a sound clip resource.
				Clip clip = AudioSystem.getClip();
				// Open audio clip and load samples from the audio input stream.
				clip.open(audioIn);
				clip.start();
			} catch (UnsupportedAudioFileException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			}
		}
		
		
		public void morningSong(){
			try {
				// Open an audio input stream.
				AudioInputStream audioIn = AudioSystem.getAudioInputStream(urlm);
				// Get a sound clip resource.
				Clip clip = AudioSystem.getClip();
				// Open audio clip and load samples from the audio input stream.
				clip.open(audioIn);
				clip.start();
			} catch (UnsupportedAudioFileException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			}
		}
		
		
		public void combatSong(){
			try {
				// Open an audio input stream.
				AudioInputStream audioIn = AudioSystem.getAudioInputStream(urlc);
				// Get a sound clip resource.
				Clip clip = AudioSystem.getClip();
				// Open audio clip and load samples from the audio input stream.
				clip.open(audioIn);
				clip.start();
			} catch (UnsupportedAudioFileException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			}
		}
		
		
		public void nightSong(){
			try {
				// Open an audio input stream.
				AudioInputStream audioIn = AudioSystem.getAudioInputStream(urln);
				// Get a sound clip resource.
				Clip clip = AudioSystem.getClip();
				// Open audio clip and load samples from the audio input stream.
				clip.open(audioIn);
				clip.start();
			} catch (UnsupportedAudioFileException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			}
		}
	}


package coolkey;

import javax.sound.sampled.LineUnavailableException;

/**
 * Przechowuje dźwięki do wielokrotnego odtwarzania.
 */
public class SoundBank {

	public final Sound TYPEWRITER;
	public final Sound MISTAKE;
	public final Sound EXPLOSION;
	public final Sound EXPLOSION2;

	/**
	 * Tworzy nową bazę dźwięków. Należy uważać, żeby sumaryczna ilość linii
	 * zarezerwowana dla wszystkich dźwięków nie przekroczyła 32 (tyle
	 * najwięcej linii można otworzyć na raz w Java Sound API).
	 *
	 * @throws LineUnavailableException jeśli karta dźwiękowa jest niedostępna.
	 */
	public SoundBank() throws LineUnavailableException {
		TYPEWRITER = new Sound(CoolKey.SOUND_DIRECTORY + "typewriter.wav", 12);
		MISTAKE = new Sound(CoolKey.SOUND_DIRECTORY + "mistake.wav", 12);
		EXPLOSION = new Sound(CoolKey.SOUND_DIRECTORY + "explosion.wav", 3);
		EXPLOSION2 = new Sound(CoolKey.SOUND_DIRECTORY + "explosion2.wav", 5);
	}
}

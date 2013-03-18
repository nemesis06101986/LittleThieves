package com.supercompany.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


 /**
  * This class holds all the resources that are going to be used within the game.
  * @author Mario Ferreira Vilanova
  *
  */
public class Assets {
	
	public static TextureAtlas atlas;
	
	public static TextureRegion background;
	public static TextureRegion banner;
	public static TextureRegion cielo;
	public static TextureRegion Topo0000;
	public static TextureRegion Topo0001;
	public static TextureRegion Topo0002;
	public static TextureRegion Topo0003;
	public static TextureRegion Topo0004;
	public static TextureRegion Topo0005;
	public static TextureRegion Topo0006;
	public static TextureRegion Topo0007;
	public static TextureRegion Topo0008;
	public static TextureRegion Topo0009;
	public static TextureRegion Topo0010;
	public static TextureRegion Topo0011;
	public static TextureRegion Topo0012;
	public static TextureRegion Topo0013;
	public static TextureRegion Topo0014;
	public static TextureRegion Topo0015;
	public static TextureRegion Topo0016;
	public static TextureRegion Topo0017;
	public static TextureRegion Topo0018;
	public static TextureRegion Topo0019;
	public static TextureRegion Topo0020;
	public static TextureRegion Topo0021;
	public static TextureRegion Topo0022;
	public static TextureRegion Topo0023;
	public static TextureRegion Topo0024;
	public static TextureRegion Topo0025;
	public static TextureRegion Topo0026;
	public static TextureRegion Topo0027;
	public static TextureRegion Topo0029;
	public static TextureRegion Topo0030;
	public static TextureRegion Topo0031;
	public static TextureRegion Topo0032;
	public static TextureRegion Topo0033;
	public static TextureRegion Topo0034;
	public static TextureRegion Topo0035;
	public static TextureRegion Topo0036;
	public static TextureRegion Topo0037;
	public static TextureRegion Topo0038;
	public static TextureRegion Topo0039;
	public static TextureRegion Topo0040;
	public static TextureRegion Topo0041;
	public static TextureRegion zanahoria;
	public static TextureRegion valla;
	
	public static TextureRegion[] saliendoFrames;
	public static TextureRegion[] entrandoFrames;
	public static TextureRegion[] fueraFrames;
	public static TextureRegion[] hitFrames;
	
	public static BitmapFont fontBig, fontSmall, fontHuge;
	
	public static Sound hitSound;
	public static Sound buttonPressed;
	public static Music gameOverSound;
	public static Music mainTheme;
	public static Music menuTheme;
	
	/**
	 * Initializes all the resources.
	 */
	public static void load(){
		atlas = new TextureAtlas (Gdx.files.internal("data/pack"));
		
		background = atlas.findRegion("Cesped0000");
		banner = atlas.findRegion("LittleThieves0000");
		cielo = atlas.findRegion("Cielo0000");
		
		Topo0000 = atlas.findRegion("Topo0000");
		Topo0001 = atlas.findRegion("Topo0001");
		Topo0002 = atlas.findRegion("Topo0002");
		Topo0003 = atlas.findRegion("Topo0003");
		Topo0004 = atlas.findRegion("Topo0004");
		Topo0005 = atlas.findRegion("Topo0005");
		Topo0006 = atlas.findRegion("Topo0006");
		Topo0007 = atlas.findRegion("Topo0007");
		Topo0008 = atlas.findRegion("Topo0008");
		Topo0009 = atlas.findRegion("Topo0009");
		Topo0010 = atlas.findRegion("Topo0010");
		Topo0011 = atlas.findRegion("Topo0011");
		Topo0012 = atlas.findRegion("Topo0012");
		Topo0013 = atlas.findRegion("Topo0013");
		Topo0014 = atlas.findRegion("Topo0014");
		Topo0015 = atlas.findRegion("Topo0015");
		Topo0016 = atlas.findRegion("Topo0016");
		Topo0017 = atlas.findRegion("Topo0017");
		Topo0018 = atlas.findRegion("Topo0018");
		Topo0019 = atlas.findRegion("Topo0019");
		Topo0020 = atlas.findRegion("Topo0020");
		Topo0021 = atlas.findRegion("Topo0021");
		Topo0022 = atlas.findRegion("Topo0022");
		Topo0023 = atlas.findRegion("Topo0023");
		Topo0024 = atlas.findRegion("Topo0024");
		Topo0025 = atlas.findRegion("Topo0025");
		Topo0026 = atlas.findRegion("Topo0026");
		Topo0027 = atlas.findRegion("Topo0027");
		Topo0029 = atlas.findRegion("Topo0029");
		Topo0030 = atlas.findRegion("Topo0030");
		Topo0031 = atlas.findRegion("Topo0031");
		Topo0032 = atlas.findRegion("Topo0032");
		Topo0033 = atlas.findRegion("Topo0033");
		Topo0034 = atlas.findRegion("Topo0034");
		Topo0035 = atlas.findRegion("Topo0035");
		Topo0036 = atlas.findRegion("Topo0036");
		Topo0037 = atlas.findRegion("Topo0037");
		Topo0038 = atlas.findRegion("Topo0038");
		Topo0039 = atlas.findRegion("Topo0039");
		Topo0040 = atlas.findRegion("Topo0040");
		
		zanahoria = atlas.findRegion("Zanahoria0000");
		valla = atlas.findRegion("valla0000");
		
		saliendoFrames = new TextureRegion[14];
		saliendoFrames[0] = Topo0001;
		saliendoFrames[1] = Topo0002;
		saliendoFrames[2] = Topo0003;
		saliendoFrames[3] = Topo0004;
		saliendoFrames[4] = Topo0005;
		saliendoFrames[5] = Topo0006;
		saliendoFrames[6] = Topo0007;
		saliendoFrames[7] = Topo0008;
		saliendoFrames[8] = Topo0009;
		saliendoFrames[9] = Topo0010;
		saliendoFrames[10] = Topo0011;
		saliendoFrames[11] = Topo0012;
		saliendoFrames[12] = Topo0013;
		saliendoFrames[13] = Topo0014;
		
		entrandoFrames = new TextureRegion[12];
		entrandoFrames[0] = Topo0016;
		entrandoFrames[1] = Topo0017;
		entrandoFrames[2] = Topo0018;
		entrandoFrames[3] = Topo0019;
		entrandoFrames[4] = Topo0020;
		entrandoFrames[5] = Topo0021;
		entrandoFrames[6] = Topo0022;
		entrandoFrames[7] = Topo0023;
		entrandoFrames[8] = Topo0024;
		entrandoFrames[9] = Topo0025;
		entrandoFrames[10] = Topo0026;
		entrandoFrames[11] = Topo0027;
		
		fueraFrames = new TextureRegion[2];
		fueraFrames[0] = Topo0015;
		fueraFrames[1] = Topo0016;
		
		hitFrames = new TextureRegion[12];
		hitFrames[0] = Topo0029;
		hitFrames[1] = Topo0030;
		hitFrames[2] = Topo0031;
		hitFrames[3] = Topo0032;
		hitFrames[4] = Topo0033;
		hitFrames[5] = Topo0034;
		hitFrames[6] = Topo0035;
		hitFrames[7] = Topo0036;
		hitFrames[8] = Topo0037;
		hitFrames[9] = Topo0038;
		hitFrames[10] = Topo0039;
		hitFrames[11] = Topo0040;
		
		fontBig = new BitmapFont(Gdx.files.internal("data/fuenteBig.fnt"), Gdx.files.internal("data/fuenteBig.png"),false);
		fontSmall = new BitmapFont(Gdx.files.internal("data/fuenteSmall.fnt"), Gdx.files.internal("data/fuenteSmall.png"),false);
		fontHuge = new BitmapFont(Gdx.files.internal("data/fuenteHuge.fnt"), Gdx.files.internal("data/fuenteHuge.png"),false);
		
		hitSound = Gdx.audio.newSound(Gdx.files.internal("data/topoAlcanzado.mp3"));
		buttonPressed = Gdx.audio.newSound(Gdx.files.internal("data/buttonPressed.wav"));
		gameOverSound = Gdx.audio.newMusic(Gdx.files.internal("data/gameOver.wav"));
		gameOverSound.setLooping(false);
		mainTheme = Gdx.audio.newMusic(Gdx.files.internal("data/mainTheme.mp3"));
		mainTheme.setLooping(true);
		menuTheme = Gdx.audio.newMusic(Gdx.files.internal("data/menuTheme.mp3"));
		menuTheme.setLooping(true);
		
	}

}

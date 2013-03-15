package com.supercompany.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.supercompany.game.Assets;
import com.supercompany.game.Settings;
import com.supercompany.game.TopillosGame;
import com.supercompany.models.Background;
import com.supercompany.models.Topo;

/**
 * Screen used to display the result of the last game . It always says game over but it also shows a new record banner when this happens.
 * The normal camera is set to be 10x15
 * The user interface camera is 320x480
 * @author Mario Ferreira Vilanova
 *
 */
public class EndOfGameScreen implements Screen{
	final static float CAM_HEIGHT = 15;
	final static float CAM_WIDTH = 10;
	final static float UICAM_HEIGHT = 480;
	final static float UICAM_WIDTH = 320;
	final static String DONE = "SCORES";
	final static String RECORD = "Record: ";
	final static int START_X = 1;
	final static int START_Y = 2;
	
	Game game;
	
	OrthographicCamera cam;
	SpriteBatch batcher;
	
	OrthographicCamera uiCam;
	SpriteBatch uiBatcher;
	
	BoundingBox doneBounds;
	Vector3 touchPoint;
	
	BitmapFont font, hugefont;
	Topo topo;
	Background background;
	boolean endGameSoundPlayed;
	
	/**
	 * Initializes the screen
	 * @param game a game should be passed as a parameter. It's used to switch to other screens
	 */
	public EndOfGameScreen (Game game){
		this.game = game;
		
		cam = new OrthographicCamera (CAM_WIDTH , CAM_HEIGHT); // camara del juego en sí
		cam.position.set(CAM_WIDTH/2f, CAM_HEIGHT/2f, 0);
		batcher = new SpriteBatch();
		
		uiCam = new OrthographicCamera (UICAM_WIDTH , UICAM_HEIGHT); // camara de la interfaz. Se usará más adelante para añadir las puntuaciones y demás datos sobre la partida
		uiCam.position.set(UICAM_WIDTH/2f, UICAM_HEIGHT/2f, 0);
		uiBatcher = new SpriteBatch();
		
		font = Assets.fontBig;
		hugefont = Assets.fontHuge;
		
		Vector3 start = new Vector3((UICAM_WIDTH/10)*START_X,(UICAM_HEIGHT/15)*(START_Y-1),0);
		Vector3 end = new Vector3((UICAM_WIDTH/10)*START_X+font.getBounds(DONE).width,(UICAM_HEIGHT/15)*START_Y+font.getXHeight(),0);
		doneBounds = new BoundingBox(start, end);
		touchPoint = new Vector3();
		
		topo = new Topo(1, 7, 2, 2, 0, Topo.ESTADO_VACIO, 2);
		topo.autoGenerate = true;
		background = new Background();
		
		endGameSoundPlayed = false;
	}

	@Override
	public void dispose() {
		batcher.dispose();
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void render(float delta) {
		// actualizar variables
		if (!endGameSoundPlayed){
			endGameSoundPlayed = !Assets.gameOverSound.isPlaying();
			if (Settings.lastGamePoints > Settings.record){ // se ha batido un nuevo record
				if (!Assets.gameOverSound.isPlaying()){
					Assets.gameOverSound.play(); // este sonido hay que sustituirlo por uno distinto para cuando se bata un record	
				}
			}else{
				if (!Assets.gameOverSound.isPlaying()){
					Assets.gameOverSound.play();
				}	
			}	
		}

		if (!Assets.gameOverSound.isPlaying() && !Assets.menuTheme.isPlaying()){
			Assets.menuTheme.play();
		}
		
		if (Gdx.input.isKeyPressed(Keys.BACK)){
			Settings.backpressed = Settings.TIME_TO_PRESS_BACK_AGAIN;
			game.setScreen(((TopillosGame)game).mainMenuScreen);
		}
		
		// comprobaciones y acciones si se ha tocado la pantalla
		if (Gdx.input.justTouched()){
			uiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
			
			if (doneBounds.contains(touchPoint)){
				Assets.buttonPressed.play();
				game.setScreen(((TopillosGame)game).scoresScreen);
			}
		}		
		
		GL10 gl = Gdx.graphics.getGL10();
		gl.glClearColor(0, 1, 0, 1);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		cam.update();
		batcher.setProjectionMatrix(cam.combined);
		
		uiCam.update();
		uiBatcher.setProjectionMatrix(uiCam.combined);
		
		// dibujar la escena
		batcher.begin();
		
		background.draw(batcher, 0, 0, 10, 15); // dibujamos el fondo
		batcher.draw(topo.getDrawing(delta), topo.position.x, topo.position.y, 2, 2); // dibujamos el topillo
		
		batcher.end();	
		
		// dibujar la interfaz de usuario
		uiBatcher.begin();
		
		hugefont.draw(uiBatcher, "GAME OVER", (UICAM_WIDTH/10)*1.5f, (UICAM_HEIGHT/15)*10);
		if (Settings.lastGamePoints >= Settings.record){
			hugefont.draw(uiBatcher, "NEW RECORD!", (UICAM_WIDTH/10)*1f, (UICAM_HEIGHT/15)*8);	
		}
		font.draw(uiBatcher, "Score: "+ Settings.lastGamePoints, (UICAM_WIDTH/10)*3, (UICAM_HEIGHT/15)*5);
		font.draw(uiBatcher, DONE, (UICAM_WIDTH/10)*START_X, (UICAM_HEIGHT/15)*START_Y);
			
		uiBatcher.end();
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void show() {
		
	}


}

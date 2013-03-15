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
 * Screen used to display the five highest scores reached in this favulous game :D
 * The normal camera is set to be 10x15
 * The user interface camera is 320x480
 * @author Mario Ferreira Vilanova
 *
 */
public class ScoresScreen implements Screen{
	final static float CAM_HEIGHT = 15;
	final static float CAM_WIDTH = 10;
	final static float UICAM_HEIGHT = 480;
	final static float UICAM_WIDTH = 320;
	final static String DONE = "MAIN MENU";
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
	
	BitmapFont font;
	Topo topo;
	Background background;
	
	/**
	 * Initializes the screen
	 * @param game game a game should be passed as a parameter. It's used to switch to other screens
	 */
	public ScoresScreen (Game game){
		this.game = game;
		
		cam = new OrthographicCamera (CAM_WIDTH , CAM_HEIGHT); // camara del juego en sí
		cam.position.set(CAM_WIDTH/2f, CAM_HEIGHT/2f, 0);
		batcher = new SpriteBatch();
		
		uiCam = new OrthographicCamera (UICAM_WIDTH , UICAM_HEIGHT); // camara de la interfaz. Se usará más adelante para añadir las puntuaciones y demás datos sobre la partida
		uiCam.position.set(UICAM_WIDTH/2f, UICAM_HEIGHT/2f, 0);
		uiBatcher = new SpriteBatch();
		
		font = Assets.fontBig;
		
		Vector3 start = new Vector3((UICAM_WIDTH/10)*START_X,(UICAM_HEIGHT/15)*(START_Y-1),0);
		Vector3 end = new Vector3((UICAM_WIDTH/10)*START_X+font.getBounds(DONE).width,(UICAM_HEIGHT/15)*START_Y+font.getXHeight(),0);
		doneBounds = new BoundingBox(start, end);
		touchPoint = new Vector3();
		
		topo = new Topo(1, 7, 2, 2, 0, Topo.ESTADO_VACIO, 2);
		topo.autoGenerate = true;
		background = new Background();
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
		if (!Assets.menuTheme.isPlaying()){
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
				game.setScreen(((TopillosGame)game).mainMenuScreen);
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
		
		font.draw(uiBatcher, "HIGH SCORES", (UICAM_WIDTH/10)*2.2f, (UICAM_HEIGHT/15)*10);
		for (int i = 0; i < Settings.scores.length; i++){
			font.draw(uiBatcher, ""+(i+1)+": "+Settings.scores[i], (UICAM_WIDTH/10)*3, (UICAM_HEIGHT/15)*(10-(1.2f*(i+1))));
			//font.draw(uiBatcher, ""+Settings.scores[i], (UICAM_WIDTH/10)*4.2f, (UICAM_HEIGHT/15)*(10-(1.2f*(i+1))));	
		}
		
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

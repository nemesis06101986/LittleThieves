package com.supercompany.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
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
 * As it's name indicates. This is the menu screen. As all other screens in the game, it uses two cameras. One to display the drawings and another one for the user interface
 * The normal camera is set to be 10x15
 * The user interface camera is 320x480
 * @author Mario Ferreira Vilanova
 *
 */
public class MainMenuScreen implements Screen{
	final static float CAM_HEIGHT = 15;
	final static float CAM_WIDTH = 10;
	final static float UICAM_HEIGHT = 480;
	final static float UICAM_WIDTH = 320;
	final static String START = "START";
	final static String SCORES = "SCORES";
	final static int START_X = 2;
	final static int START_Y = 5;
	final static int SCORES_X = 2;
	final static int SCORES_Y = 3;
	
	Game game;
	
	OrthographicCamera cam;
	SpriteBatch batcher;
	
	OrthographicCamera uiCam;
	SpriteBatch uiBatcher;
	
	BoundingBox startBounds, scoresBounds;
	Vector3 touchPoint;
	
	BitmapFont font;
	Topo topo, topo1, topo2, topo3;
	Background background;
	
	/**
	 * Initializes the screen
	 * @param game game a game should be passed as a parameter. It's used to switch to other screens
	 */
	public MainMenuScreen (Game game){
		this.game = game;
		
		cam = new OrthographicCamera (CAM_WIDTH , CAM_HEIGHT); // camara del juego en sí
		cam.position.set(CAM_WIDTH/2f-CAM_WIDTH, CAM_HEIGHT/2f, 0);
		batcher = new SpriteBatch();
		
		uiCam = new OrthographicCamera (UICAM_WIDTH , UICAM_HEIGHT); // camara de la interfaz. Se usará más adelante para añadir las puntuaciones y demás datos sobre la partida
		uiCam.position.set(UICAM_WIDTH/2f-UICAM_WIDTH, UICAM_HEIGHT/2f, 0);
		uiBatcher = new SpriteBatch();
		
		font = Assets.fontBig;
		
		Vector3 start = new Vector3((UICAM_WIDTH/10)*START_X, (UICAM_HEIGHT/15)*(START_Y-1),0);
		Vector3 end = new Vector3((UICAM_WIDTH/10)*START_X+font.getBounds(START).width,(UICAM_HEIGHT/15)*START_Y+font.getXHeight(),0);
		startBounds = new BoundingBox(start, end);
		
		start = new Vector3((UICAM_WIDTH/10)*SCORES_X, (UICAM_HEIGHT/15)*(SCORES_Y-1),0);
		end = new Vector3((UICAM_WIDTH/10)*SCORES_X+font.getBounds(SCORES).width,(UICAM_HEIGHT/15)*SCORES_Y+font.getXHeight(),0);
		scoresBounds = new BoundingBox(start, end);
		
		touchPoint = new Vector3();
		
		topo = new Topo(1, 7, 2, 2, 0, Topo.ESTADO_VACIO, 2);
		topo.autoGenerate = true;
		topo1 = new Topo(2, -7, 2, 2, 0, Topo.ESTADO_METIENDOSE, 2);
		topo1.autoGenerate = true;
		topo2 = new Topo(3, -5, 8, 2, 0, Topo.ESTADO_SALIENDO, 2);
		topo2.autoGenerate = true;
		topo3 = new Topo(4, -2, 3, 2, 0, Topo.ESTADO_FUERA, 2);
		topo3.autoGenerate = true;
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
		
		if (Settings.backpressed > 0){
			Settings.backpressed = Settings.backpressed - delta;
		}
		
		if (Settings.backpressed <= 0 && Gdx.input.isKeyPressed(Keys.BACK)){
			  Gdx.app.exit();
		}
		
		// comprobaciones y acciones si se ha tocado la pantalla
		if (Gdx.input.justTouched()){
			uiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
			
			if (startBounds.contains(touchPoint)){
				game.setScreen(((TopillosGame)game).gameScreen);
				cam.position.x = CAM_WIDTH/2f;
				uiCam.position.x = UICAM_WIDTH/2f;
				Assets.menuTheme.stop();
				Assets.buttonPressed.play();
			}else if (scoresBounds.contains(touchPoint)){
				Assets.buttonPressed.play();
				game.setScreen(((TopillosGame)game).scoresScreen);
			}
			
			cam.position.set(CAM_WIDTH/2f, CAM_HEIGHT/2f, 0);
			uiCam.position.set(UICAM_WIDTH/2f, UICAM_HEIGHT/2f, 0);
		}	

		GL10 gl = Gdx.graphics.getGL10();
		gl.glClearColor(0, 1, 0, 1);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		// mover las camara
		if (cam.position.x < CAM_WIDTH/2f){
			cam.position.x = cam.position.x + delta;
			if (cam.position.x > CAM_WIDTH/2f){
				cam.position.x = CAM_WIDTH/2f;
			}
		}
		cam.update();
		batcher.setProjectionMatrix(cam.combined);
		
		if (uiCam.position.x < UICAM_WIDTH/2f){
			uiCam.position.x = uiCam.position.x + delta*40;
		}
		
		uiCam.update();
		uiBatcher.setProjectionMatrix(uiCam.combined);
		
		// dibujar la escena
		batcher.begin();
		
		background.draw(batcher, -10, 0, 10, 15); // dibujamos el fondo
		batcher.draw(topo.getDrawing(delta), topo.position.x, topo.position.y, 2, 2); // dibujamos el topillo
		batcher.draw(topo1.getDrawing(delta), topo1.position.x, topo1.position.y, 2, 2); // dibujamos el topillo
		batcher.draw(topo2.getDrawing(delta), topo2.position.x, topo2.position.y, 2, 2); // dibujamos el topillo
		batcher.draw(topo3.getDrawing(delta), topo3.position.x, topo3.position.y, 2, 2); // dibujamos el topillo
		
		batcher.end();	
		
		// dibujar la interfaz de usuario
		uiBatcher.begin();
		
		uiBatcher.draw(Assets.banner, 0, 180, 320, 240);
		font.draw(uiBatcher, START, (UICAM_WIDTH/10)*START_X, (UICAM_HEIGHT/15)*START_Y);
		font.draw(uiBatcher, SCORES, (UICAM_WIDTH/10)*SCORES_X, (UICAM_HEIGHT/15)*SCORES_Y);	
			
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

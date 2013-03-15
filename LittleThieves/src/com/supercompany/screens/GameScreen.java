package com.supercompany.screens;

import java.util.Iterator;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.supercompany.game.Assets;
import com.supercompany.game.Settings;
import com.supercompany.game.TopillosGame;
import com.supercompany.models.Background;
import com.supercompany.models.Topo;
import com.supercompany.models.World;

/**
 * Screen where all the fun happens!!! Aren't you happy?
 * The normal camera is set to be 10x15
 * The user interface camera is 320x480
 * @author Mario Ferreira Vilanova
 *
 */
public class GameScreen implements Screen{
	final static float CAM_HEIGHT = 15;
	final static float CAM_WIDTH = 10;
	final static float UICAM_HEIGHT = 480;
	final static float UICAM_WIDTH = 320;

	Game game;
	World world;
	
	OrthographicCamera cam, uiCam;
	SpriteBatch batcher, uiBatcher;
	
	Vector3 touchPoint;
	BitmapFont font;
	
	Background background;
	String lifesText, pointsText;
	
	/**
	 * Initializes the screen
	 * @param game game a game should be passed as a parameter. It's used to switch to other screens
	 */
	public GameScreen (Game game){
		this.game = game;

		cam = new OrthographicCamera (CAM_WIDTH , CAM_HEIGHT); // camara del juego en sí
		cam.position.set(CAM_WIDTH/2f, CAM_HEIGHT/2f, 0);
		batcher = new SpriteBatch();
		
		uiCam = new OrthographicCamera (UICAM_WIDTH , UICAM_HEIGHT); // camara de la interfaz. Se usará más adelante para añadir las puntuaciones y demás datos sobre la partida
		uiCam.position.set(UICAM_WIDTH/2f, UICAM_HEIGHT/2f, 0);
		uiBatcher = new SpriteBatch();
		
		touchPoint = new Vector3();
		font = Assets.fontSmall;
		
		world = new World();
		background = new Background();
	}

	@Override
	public void dispose() {
		batcher.dispose();
		uiBatcher.dispose();
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
		if (!Assets.mainTheme.isPlaying()){
			Assets.mainTheme.play();
		}
		
		if (Gdx.input.isKeyPressed(Keys.BACK)){
			  world.lifes = -1;
			  Settings.backpressed = Settings.TIME_TO_PRESS_BACK_AGAIN;
		}
		
		GL10 gl = Gdx.graphics.getGL10();
		gl.glClearColor(0, 1, 0, 1);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		cam.update();
		batcher.setProjectionMatrix(cam.combined);
		uiCam.update();
		uiBatcher.setProjectionMatrix(uiCam.combined);
		
		if (Gdx.input.justTouched()){
			cam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
			world.hit(touchPoint);
		}
		
		world.update(delta);
		
		if (world.isFinished){
			updateRecord();
			if (world.lifes == -1){
				game.setScreen(((TopillosGame)game).mainMenuScreen);
			}else{
				game.setScreen(((TopillosGame)game).endOfGameScreen);	
			}
			
			Assets.mainTheme.stop();
			
		}
		
		pointsText = "Points: " + world.points;	
		lifesText = "Lifes left: " + world.lifes;
		
		batcher.begin();
		
		background.draw(batcher, 0, 0, 10, 15);
		Iterator<Topo> iter = world.topos.iterator();
		while (iter.hasNext()){
			Topo temp = iter.next();
			batcher.draw(temp.getDrawing(Gdx.graphics.getDeltaTime()), temp.position.x, temp.position.y, 2, 2);
		}
		
		batcher.end();
		
		
		uiBatcher.begin();
			font.draw(uiBatcher, "Points x " + world.points, 5, UICAM_HEIGHT-5);
			for (int i = world.lifes; i > 0; i--){
				uiBatcher.draw(Assets.zanahoria, UICAM_WIDTH-5-(i*Assets.zanahoria.getRegionWidth()), UICAM_HEIGHT-5-Assets.zanahoria.getRegionHeight(), Assets.zanahoria.getRegionWidth(), Assets.zanahoria.getRegionHeight());
			}
		uiBatcher.end();
		
	}
	
	public void updateRecord (){
		if (Settings.record < world.points){
			Settings.record = world.points;
			Settings.save();
		}
		Settings.updateScores(world.points);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void resume() {
	}

	@Override
	public void show() {
		world.initiate();
	}

}

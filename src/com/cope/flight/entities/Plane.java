package com.cope.flight.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Plane extends Entity {
	
	Vector2 velocity, acceleration, force;
	float mass;
	ShapeRenderer sr;
	boolean onTouch, offTouch, alive;
	Rectangle bounds;
	Sprite goldFish;
	float targetAngle;
	Animation animation;
	float loopTime, stateTime;
	TextureRegion[] frames;
	
	public Plane(){
		sr = new ShapeRenderer();
		height = Gdx.graphics.getWidth() / 9;
		width = height;
		mass = 1;
		
		x = Gdx.graphics.getWidth() / 5;
		y = Gdx.graphics.getHeight() / 2 - width;
		
		velocity = new Vector2();
		acceleration = new Vector2();
		force = new Vector2();
		
		alive = true;
		onTouch = true;
		offTouch = false;
		
		bounds = new Rectangle();
		
		goldFish = new Sprite(new Texture("floppyFish_1.png"));
		goldFish.setSize(width, height);
		goldFish.setOrigin(goldFish.getScaleX() / 2, goldFish.getScaleY() / 2);
		
		frames = new TextureRegion[2];
		frames[0] = new TextureRegion(new Texture("floppyFish_1.png"));
		frames[1] = new TextureRegion(new Texture("floppyFish_2.png"));
		loopTime = 0.5f;
		animation = new Animation(loopTime, frames);
		stateTime = 0f;
		
		goldFish.setRegion(animation.getKeyFrame(0));
		
	}
	
	public void update(float delta, SpriteBatch batch){
		bounds.set(x, y, width, height);
		
		if (alive) {
			acceleration.y = -2.7f * Gdx.graphics.getHeight();
			if (stateTime <= loopTime){
				stateTime += Gdx.graphics.getDeltaTime();
			}else{
				stateTime = 0f;
			}
			goldFish.setRegion(animation.getKeyFrame(stateTime/loopTime));
		}
		velocity.x += acceleration.x * delta;
		velocity.y += acceleration.y * delta;
		
		if (!alive) {
			velocity.y = Gdx.graphics.getHeight() / 5;
			velocity.x = (float) (Gdx.graphics.getWidth() * Math.sin(y / 100) / 5);
		}
		else {
			x += velocity.x * delta;
		}
		y += velocity.y * delta;
		
		force.set(0, 0);
		
//		sr.begin(ShapeType.Line);
//		sr.setColor(255, 0, 0, 0.4f);
//		sr.rect(x, y, bounds.width, bounds.height);
//		sr.end();
		
		goldFish.setPosition(x, y);
		float currentAngle = goldFish.getRotation();
		
		if (alive) {
			targetAngle = (float) (2 * 360 * Math.atan(velocity.y
					/ (3 * Gdx.graphics.getWidth())) / (2 * Math.PI));
			currentAngle += 0.05f * (targetAngle - currentAngle);
			goldFish.rotate(0.05f * (targetAngle - currentAngle));
		}else{
			targetAngle = 180;
			currentAngle += 0.02f * (targetAngle - currentAngle);
			goldFish.rotate(0.02f * (targetAngle - currentAngle));
		}
//		goldFish.setRotation(currentAngle);
		
		batch.begin();
		goldFish.draw(batch);
		batch.end();
	}
	
	public void kill(){
		alive = false;
	}
	
	public boolean isAlive(){
		return alive;
	}
	
	public Rectangle getBounds(){
		return bounds;
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(float x, float y) {
		velocity.set(x, y);
	}

	public Vector2 getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(Vector2 acceleration) {
		this.acceleration = acceleration;
	}

	public Vector2 getForce() {
		return force;
	}

	public void setForce(Vector2 force) {
		this.force = force;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getMass() {
		return mass;
	}

	public void setMass(float mass) {
		this.mass = mass;
	}

}

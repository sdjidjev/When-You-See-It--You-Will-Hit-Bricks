/**
 * BRICK-KNOCKOUT
 * 
 * @author Stephanie Djidjev
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class BrickKnockout extends GraphicsProgram {
    public static final int APPLICATION_WIDTH = 400;
    public static final int APPLICATION_HEIGHT = 600;
    private static final int WIDTH = APPLICATION_WIDTH;
    private static final int HEIGHT = APPLICATION_HEIGHT;
    private static final int NBRICKS_PER_ROW = 10;
    private static final int NBRICK_ROWS = 10;
    private static final double BRICK_SEP = 4.0;
    private static final double BRICK_WIDTH =
        (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;
    private static final double BRICK_HEIGHT = BRICK_WIDTH/4;
    private static final double BRICK_Y_OFFSET = 70;
    private static final double BALL_RADIUS = BRICK_HEIGHT;
    private static final double PADDLE_WIDTH = 60;
    private static final double PADDLE_HEIGHT = 10;
    private static final double PADDLE_Y_OFFSET = 30;
    private static final double PAUSE_TIME_MS = 15;
    private static final double BALL_SPEED_Y = 4.0;
    private static final double MAX_BALL_SPEED_X = 4.0;
    private static final int NTURNS = 3;
    private double brickCounter = 0;
    public BrickKnockout() 
    {
        start();
        last = new GPoint(0,0);
    }

    public void run() 
    {
        setUpBRICKS();
        rect2 = new GRect((WIDTH/2)-PADDLE_WIDTH/2, HEIGHT - PADDLE_Y_OFFSET, PADDLE_WIDTH, PADDLE_HEIGHT);

        rect2.setFilled(true);
        add(rect2); 
        addMouseListeners();
        addKeyListeners();
        createBall();
        waitForClick();

        while(!hitBottom()&& !allBlocks())
        {
            bounceBall();
        }
        if (hitBottom()){
            LOSER = new GLabel("YOU LOST", 50, 60);
            add(LOSER);
        }

        if (allBlocks()){
            GLabel WINNER = new GLabel("YOU LOST", 50, 60);
            add(WINNER);
        }

    }

    private boolean allBlocks(){

        return brickCounter == 0;
    }

    private boolean hitBottom()
    {
        return ball.getY() >= HEIGHT - BALL_RADIUS*2;
    }

    private void setUpBRICKS()
    {

        for( int i = 0; i < NBRICK_ROWS; i++)
        {
            brickCounter++;
            for(int j = 0; j < NBRICKS_PER_ROW; j++)
            {
                brickCounter++;
                GRect rect = new GRect(i*BRICK_WIDTH + BRICK_SEP*i, BRICK_Y_OFFSET + j*BRICK_HEIGHT + BRICK_SEP*j, BRICK_WIDTH, BRICK_HEIGHT);
                rect.setFilled(true);
                add(rect);
                if (j == 0 || j == 1){
                    rect.setFillColor( Color.RED );
                    rect.setColor( Color.RED);}
                if (j==2 || j == 3){
                    rect.setFillColor( Color.ORANGE );
                    rect.setColor( Color.ORANGE);}
                if(j ==4 || j ==5){
                    rect.setFillColor( Color.YELLOW);
                    rect.setColor( Color.YELLOW);
                }
                if( j == 6 || j == 7){
                    rect.setFillColor( Color.GREEN);
                    rect.setColor( Color.GREEN);
                }
                if( j == 8 || j == 9){
                    rect.setFillColor( Color.CYAN);
                    rect.setColor( Color.CYAN);}
            }
        }

    }

    // Called on mouse drag to reposition the object
    public void mouseMoved(MouseEvent e) 
    {
        if(e.getX() >= (PADDLE_WIDTH/2) && e.getX() <= (WIDTH - PADDLE_WIDTH/2))
        {
            rect2.setLocation(e.getX() - (PADDLE_WIDTH/2),HEIGHT - PADDLE_Y_OFFSET);
        }

    }

    public void keyPressed(KeyEvent e) {     
        boolean chocolate = true;
    }

    private void createBall()
    {
        ball = new GOval(WIDTH/2 - (BALL_RADIUS/2), HEIGHT/2 - (BALL_RADIUS/2) , BALL_RADIUS*2, BALL_RADIUS*2);
        ball.setFilled(true);
        add(ball);
        vx = Math.random()*(MAX_BALL_SPEED_X - 1.0) + 1.0;
        if (Math.random() < 0.5)
        {
            vx = -vx;
        }
        vy = BALL_SPEED_Y;

    }

    private void bounceBall() 
    {
        moveBall();
        checkforCollision();
        pause(PAUSE_TIME_MS);
    }

    /** Update and move ball */
    private void moveBall() 
    {
        ball.move(vx,vy);
    }

    private void checkforCollision()
    {
        if(ball.getX() >= WIDTH - BALL_RADIUS*2 || ball.getX() <= 0)
        {
            vx = -vx;
        }
        if(ball.getY() <= 0)
        {
            vy = -vy;
        }
        if (getObjectCollided()!= null){
            GObject collider = getObjectCollided();
            if(collider == rect2){
                vy = -vy;
            }
            else{
                remove(collider);
                vy = -vy;
                brickCounter--;

            } 

        }
    }

    private GObject getObjectCollided(){
        if(getElementAt(ball.getX(),ball.getY()) !=null  )
        {           
            return getElementAt(ball.getX(),ball.getY());
        }
        if (getElementAt(ball.getX()+BALL_RADIUS*2,ball.getY()+BALL_RADIUS*2)!= null)
        {
            return getElementAt(ball.getX()+BALL_RADIUS*2,ball.getY()+BALL_RADIUS*2);
        }
        if (getElementAt(ball.getX()+BALL_RADIUS*2,ball.getY())!= null)
        {
            return getElementAt(ball.getX()+BALL_RADIUS*2,ball.getY());
        }
        if (getElementAt(ball.getX(),ball.getY()+BALL_RADIUS*2)!= null)
        {
            return getElementAt(ball.getX(),ball.getY()+BALL_RADIUS*2);
        }
        return null;

    }

    private double x, y;
    private Graphics background;
    public boolean chocolate = true;
    private GOval ball;
    private GRect rect2;
    private GRect rect;
    private GLabel LOSER;
    private GObject gobj;
    private static GPoint last; 
    private double vx, vy;
    private MouseEvent e;
    private Color [] list = {Color.RED, Color.BLUE, Color.CYAN, Color.GREEN, Color.ORANGE, Color.YELLOW, Color.PINK};
}


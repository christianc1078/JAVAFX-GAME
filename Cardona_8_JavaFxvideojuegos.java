package cardona_8_javafxvideojuegos;

import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.scene.shape.Shape;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import java.util.Random;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author christianc1078
 */
public class Cardona_8_JavaFxvideojuegos extends Application {

    public ArrayList<Benicio> Andrea;

    ArrayList<Rectangle> badblockz = new ArrayList();
    ArrayList<String> input = new ArrayList<String>();
    Player Rene;
    Rectangle rect;
    Rectangle losescreen;
    Rectangle floor;
    Rectangle startscreen;
    Boolean letsplay = false;
    Ball ballz;
    Group root;
    Group Win;
    Random cool = new Random();
    Random estaban = new Random();
    Random newcool = new Random();
    
//    GraphicsContext gc;
    boolean bum = false;

    @Override
    public void start(Stage primaryStage) {
        root = new Group();
        Win = new Group();
        Scene scene = new Scene(root);
        primaryStage.setTitle("box check");
        primaryStage.setScene(scene);

        Canvas canvas = new Canvas(600, 600);
        Image startscreen = new Image("file:src/Start.png");
        ImageView ss = new ImageView(startscreen);
        
        

//        Image losescreen = new Image("file:src/Start.png");
//        ImageView ls = new ImageView(losescreen);

        ss.setFitHeight(canvas.getHeight());
        ss.setFitWidth(canvas.getWidth());

        Image winner = new Image("file:src/win.png");
        ImageView gamewin = new ImageView(winner);

        gamewin.setFitHeight(canvas.getHeight());
        gamewin.setFitWidth(canvas.getWidth());

        //Notice gc is not being used yet
//        gc = canvas.getGraphicsContext2D();
//        gc.setFill(Color.BLACK);
//        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());    	//notice we are creating shape objects

        Rene = new Player(250);

        floor = new Rectangle(0, 500, 0, 150);
        floor.setFill(Color.WHITE);
        //Rene = new Rectangle(250, 590, 100, 5);

        rect = new Rectangle(150, 50, 25, 25);
        rect.setFill(Color.BLUE);

        ballz = new Ball(250, 500, 13);
        ballz.setFill(Color.AQUA);

        // notice the difference in how an ArrayList adds items
        badblockz.add(rect);

        Andrea = new ArrayList();
        //idk Rectangle blue = new Rectangle(1, 1);
        for (int i = 0; i < canvas.getHeight(); i += 60) {
            for (int j = 0; j < canvas.getWidth() / 2; j += 60) {
                Andrea.add(new Benicio(i, j));
            }
        }

        //we have created an animation timer --- the class MUST be overwritten - look below
        AnimationTimer timer = new MyTimer();

        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                ballz.setX(event.getX());
                ballz.setY(event.getY());
                System.out.println("mouse click detected! " + event.getSource());

                ballz.setFill(Color.DARKSLATEBLUE);

            }

        });

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                //  String code = event.getCode().toString();
                //	input.remove( code );

                if (letsplay) {

                    Rene.move();
                }
                if (event.getCode() == KeyCode.ENTER) {
                    root.getChildren().remove(ss);
                    letsplay = true;
                    canvas.setWidth(600);
                    canvas.setHeight(600);
                    bum = true;

                }

                if (bum == true) {
                    root.getChildren().add(ballz);

                }

                if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D) { // don't use toString here!!!
                    Rene.setRight();

                    checkBounds(Rene);
                } else if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A) {
                    Rene.setLeft();

                    checkBounds(Rene);
                } else if (event.getCode() == KeyCode.UP) {
                    Rene.setRight();

                    checkBounds(Rene);
                } else if (event.getCode() == KeyCode.DOWN) {
                    Rene.setY(Rene.getY() + 0);

                    checkBounds(Rene);
                }
            }
        });
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT) {
                    // rectangleVelocity.set(0);
                }
            }
        });

        root.getChildren().add(canvas);
        //notice we are manually adding the shape objects to the "root" window

        // root.getChildren().add(Rene);
        //  root.getChildren().add(rect);
        for (Benicio CoolKid : Andrea) {
            root.getChildren().add(CoolKid.picture);
        }

        root.getChildren().add(floor);
           
        root.getChildren().add(ss);
        

     
        
        //root.getChildren().add(ss);

        timer.start();
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     *
     * The same as before main just calls the described above
     */
    ///  vvvvvvvvvvvv   MAIN vvvvvvvvvvv
    public static void main(String[] args) {
        launch(args);
    }

    //// ^^^^^^^^^^^  MAIN ^^^^^^^^^^^^^
    // we create our time here --- to animate
    private class MyTimer extends AnimationTimer {

        boolean movedown = true;

        /// handle is defined by the abstract parent class -- it must be redined
        /// this is what happens again and again until stop()
        @Override
        public void handle(long now) {
            // You can look at the key presses here as well -- this is one of many. Try others
//        	if (input.contains("LEFT")) {
//            	box.setX(box.getX() - .5);
//        	}

            doHandle();
            /// notice doHandle()  is what happens again and again it's defined below
        }

        private void doHandle() {
//            if (ballz.score >= 5) {
//                win.setOpacity(100);
//              
//                System.out.println("agjhweuigtaihg");
//            }
            if (bum) {
                for (Benicio CoolKid : Andrea) {
                    if (ballz.getBoundsInParent().intersects(CoolKid.getBoundsInParent())) {

                        root.getChildren().remove(CoolKid.picture);
                        System.out.println(ballz.score);
                        CoolKid.setVisible(false);
                        int a = estaban.nextInt(50);
                        CoolKid.setX(-320);
                        ballz.score = ballz.score + 1;

                        // CoolKid.setWidth(0);
                        //CoolKid.setHeight(0);
                        int m = cool.nextInt(50);
                        if (m % 2 == 0) {
                            ballz.isgoleft = true;
                            ballz.isgoright = false;
                        } else {
                            ballz.isgoleft = false;
                            ballz.isgoright = true;
                        }
                        if (ballz.score >= 5) {
                            Win.getChildren().add(gamewin);
                        }
                            
                        
//                         if (ballz.getBoundsInParent().intersects(floor.getBoundsInParent())){
//                               int m = newcool.nextInt(50);
//                        if (m % 2 == 0) {
                    }

                }
            }
            //}

            {
                if (bum == true) {
                    ballz.move();
                }
            }
            Rene.move();
            if (movedown && rect.getY() < 555) {
                rect.setY(rect.getY() + .5);
            }
            if (!movedown && rect.getY() > 0) {
                rect.setY(rect.getY() - .5);
            }
            if (rect.getY() > 550) {
                movedown = false;
            }
            if (rect.getY() < 1) {
                movedown = true;
            }

            // stop();
            // System.out.println("Animation stopped");
        }
    }

    private void checkBounds(Rectangle box) {
        // checkBounds is called in two different locations --- it's really only necessary in the animation dohandle
        // experiment - check the differences

        boolean collisionDetected = false;

        // notice the difference in how an ArrayList iterates through items
//    	for (Rectangle badblock : badblockz) {
//        	if (box.getBoundsInParent().intersects(badblock.getBoundsInParent())) {
//            	collisionDetected = true;
//            	System.out.println("work");
//            	badblock.setFill(Color.RED);
//        	} else {
//            	badblock.setFill(Color.BLUE);
//        	}
//    	}
        if (collisionDetected) {
            box.setFill(Color.RED);
        } else {
            box.setFill(Color.CYAN);

            Rene.setFill(Color.CRIMSON);
        }
    }

}

class Player extends Rectangle {

    int health;
    boolean ismoveleft, ismoveright;

    public Player(double x) {

        super(x, 585, 100, 10);
        this.setFill(Color.CRIMSON);
    }

    public Player(double x, double y) {
        super(x, y);
        this.setFill(Color.AQUA);
    }

    void move() {
        if (this.getX() > 595) {
            this.setX(5);
        }
        if (this.getX() < 5) {
            this.setX(595);
        }

        if (ismoveleft) {
            this.moveLeft();
        } else {
            this.moveRight();
        }

    }

    void setLeft() {
        ismoveleft = true;
        ismoveright = false;
    }

    void setRight() {
        ismoveleft = false;
        ismoveright = true;
    }

    void moveLeft() {
        this.setX(this.getX() - .25);

    }

    void moveRight() {
        this.setX(this.getX() + .25);

    }

}

class Benicio extends Rectangle {

    boolean isgoup, isgodown, isgoright, isgoleft;
    boolean isvisable = true;
    Image boxz = new Image("block.png");
    public ImageView picture = new ImageView(boxz);

    public Benicio(double x, double y) {
        super(x, y, 25, 25);
        this.picture.setX(x);
        this.picture.setY(y);
        this.picture.setFitHeight(25);
        this.picture.setFitWidth(25);

        //	boolean isvisable = this.isvisable == true;
    }

    void setisvisable(boolean CoolKid) {
        this.isvisable = CoolKid;
    }

}

class Ball extends Circle {

    Image ballz = new Image("file:src/Basketball.png");
    public ImageView picture1 = new ImageView(ballz);

    boolean isgoup, isgodown, isgoright, isgoleft;
    double x;
    double y;

    public Ball(double centerX, double centerY) {
        super(centerX, centerY, 20);
        isgodown = true;
        this.picture1.setX(x);
        this.picture1.setY(y);
        this.picture1.setFitHeight(50);
        this.picture1.setFitWidth(50);
        this.setDown();
        this.setRight();
        this.setLeft();
        this.setUp();
        this.setCenterX(centerX);
        this.setCenterY(centerY);
        this.setX(500);
        this.setY(500);
    }

    Ball(double centerX, double centerY, int r) {
        super(centerX, centerY, r);
        isgodown = true;
        this.picture1.setX(x);
        this.picture1.setY(y);
        this.picture1.setFitHeight(50);
        this.picture1.setFitWidth(50);
        this.setDown();
        this.setRight();
        this.setLeft();
        this.setUp();
        this.setCenterX(centerX);
        this.setCenterY(centerY);
        this.setX(500);
        this.setY(500);
    }

    public boolean isgoup() {
        return isgoup;
    }

    public void setgoup() {
        this.isgoup = true;
    }

    public boolean isgodown() {
        return isgodown;
    }

    public void setgodown() {
        this.isgodown = true;
    }

    public boolean isgoright() {
        return isgoright;
    }

    public void setgoright() {
        this.isgoright = true;
    }

    public boolean isgoleft() {
        return isgoleft;
    }

    public void setgoleft() {
        this.isgoleft = true;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    void move() {

        if (this.isgoup) {
            this.moveUp();
        } else if (this.isgodown) {
            this.moveDown();
        }
        if (this.isgoleft) {
            this.moveLeft();
        } else if (this.isgoright) {
            this.moveRight();
        }

        this.setX(this.x);
        this.setY(this.y);
        this.setCenterX(this.x);
        this.setCenterY(this.y);
    }

    private void moveUp() {
        if (this.getY() > 5) {
            this.setY(this.getY() - .2);
        } else {
            this.setDown();
        }
    }

    private void moveDown() {
        if (this.getY() < 598) {
            this.setY(this.getY() + .2);
        } else {
            this.setUp();
        }
    }

    private void moveRight() {
        if (this.getX() < 598) {
            this.setX(this.getX() + .2);
        } else {
            this.setLeft();
        }
    }

    private void moveLeft() {
        if (this.getX() > 5) {
            this.setX(this.getX() - .2);
        } else {
            this.setRight();
        }
    }

    private void setUp() {
        this.isgoup = true;
        this.isgodown = false;
        this.isgoleft = false;
        this.isgoright = false;
    }

    private void setDown() {
        this.isgoup = false;
        this.isgodown = true;
        this.isgoleft = false;
        this.isgoright = false;
    }

    private void setRight() {
        this.isgoup = false;
        this.isgodown = false;
        this.isgoleft = false;
        this.isgoright = true;
    }

    private void setLeft() {
        this.isgoup = true;
        this.isgodown = false;
        this.isgoleft = true;
        this.isgoright = false;
    }
    int score = 0;

}

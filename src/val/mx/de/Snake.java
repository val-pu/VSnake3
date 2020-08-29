package val.mx.de;

import sas.Circle;
import sas.View;

import java.awt.*;
import java.util.Random;

public class Snake {

    private Queue<Circle> python, python2;
    private View v;
    private Circle head, tail, food;
    private Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

    public Snake() {


        food = new Circle(82, 82, 19);
        food.setColor(Color.RED);
        v = new View((int) screen.getWidth(), (int) screen.getHeight());
        python = new Queue<>();
        python2 = new Queue<>();
        head = new Circle(202, 202, 19);
        python.enqueue(head);
        tail = head;
        run();
    }

    public void run() {

        while (true) {
            int lastDirection = -1;
            while (!python.isEmpty()) {
                Circle c = python.front();

                if (lastDirection != -1) {
                    int newDir = lastDirection;
                    lastDirection = (int) c.getDirection();
                    c.setDirection(newDir);

                } else {
                    lastDirection = (int) c.getDirection();
                }
                if (v.keyUpPressed()) head.setDirection(0);
                else if (v.keyDownPressed()) head.setDirection(180);
                else if (v.keyLeftPressed()) head.setDirection(270);
                else if (v.keyRightPressed()) head.setDirection(90);
                c.move(40);


                python2.enqueue(c);
                python.dequeue();
            }

            while (!python2.isEmpty()) {
                python.enqueue(python2.front());
                System.out.println(python.front().equals(head));

                System.out.println(python.front().intersects(head));

                if(python.front().getShapeX() != head.getShapeX() && python.front().getShapeY() != head.getShapeY()) {

                    if (python.front().intersects(head)) System.exit(0);
                }


                python2.dequeue();
            }

            if (head.intersects(food)) {
                Random r = new Random();

                food.moveTo(r.nextInt((int) screen.getWidth() / 40) * 40 + 2, r.nextInt((int) screen.getHeight() / 40) * 40 + 2);
                add();
            }

            if (v.keyEnterPressed()) add();

            v.wait(60);
        }


    }

    private void add() {

        switch ((int) tail.getDirection()) {
            case 90:
                tail = new Circle(tail.getShapeX() - 40, tail.getShapeY(), 20);
                break;
            case 180:
                tail = new Circle(tail.getShapeX(), tail.getShapeY() - 40, 20);
                break;
            case 0:
                tail = new Circle(tail.getShapeX(), tail.getShapeY() + 40, 20);
                break;
            case 270:
                tail = new Circle(tail.getShapeX() + 40, tail.getShapeY(), 20);
                break;
        }
        python.enqueue(tail);
    }


}

package org.example.miniproyecto_3.View.Assets;


import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

/**
 * Provides methods to draw 2D shapes used to represent hits and misses
 * on the board in the Battleship game.
 * <p>
 * This class creates graphical shapes such as markers for hit cells on ships
 * or miss indicators on water cells using JavaFX shapes.
 * </p>
 */
public class ShapeDrawer {

    /**
     * Constructs a new {@code ShapeDrawer} instance.
     */
    public ShapeDrawer(){

    }

    /**
     * Draws an "X" shape composed of two red crossed lines.
     * <p>
     * This shape is used to mark a missed shot on an empty {@link org.example.miniproyecto_3.Model.Cell}.
     * </p>
     *
     * @return a {@link javafx.scene.Group} containing two {@link javafx.scene.shape.Line} objects
     *         forming a red "X" with rounded line caps and beveled line joins.
     */
    public Group drawX(){
        Line line1 = new Line(-95.55644226074219, -4.278011322021484, -68.49995422363281, -31.420866012573242);
        line1.setStroke(Color.web("#d70d0d"));
        line1.setStrokeLineCap(StrokeLineCap.ROUND);
        line1.setStrokeLineJoin(StrokeLineJoin.BEVEL);
        line1.setStrokeWidth(5.0);

        Line line2 = new Line(-95.55644226074219, -4.278011322021484, -68.49995422363281, -31.420866012573242);
        line2.setStroke(Color.web("#d70d0d"));
        line2.setStrokeLineCap(StrokeLineCap.ROUND);
        line2.setStrokeLineJoin(StrokeLineJoin.BEVEL);
        line2.setStrokeWidth(5.0);
        line2.setRotate(90.0);

        Group group = new Group(line1, line2);

        return group;
    }

    /**
     * Creates a {@link javafx.scene.shape.Polygon} with specified fill and stroke colors, position, and points.
     *
     * @param fillColor  the fill color in web format (e.g., "#ff0000")
     * @param strokeColor the stroke color in web format (e.g., "#000000")
     * @param layoutX    the x-coordinate position of the polygon layout
     * @param layoutY    the y-coordinate position of the polygon layout
     * @param points     the points of the polygon as a varargs of doubles representing x,y coordinates alternately
     * @return a {@link javafx.scene.shape.Polygon} configured with the specified properties
     *
     * @see javafx.scene.shape.Polygon
     * @see javafx.scene.paint.Color
     * @see javafx.scene.shape.StrokeType
     */
    private Polygon createPolygon(String fillColor, String strokeColor, double layoutX, double layoutY, double... points) {
        Polygon polygon = new Polygon(points);
        polygon.setFill(Color.web(fillColor));
        polygon.setStroke(Color.web(strokeColor));
        polygon.setStrokeType(StrokeType.OUTSIDE);
        polygon.setStrokeWidth(0.0);
        polygon.setLayoutX(layoutX);
        polygon.setLayoutY(layoutY);
        return polygon;
    }

    /**
     * Creates a {@link javafx.scene.Group} representing a stylized explosion graphic.
     * <p>
     * The explosion consists of multiple layered polygons and an ellipse with orange and yellow colors,
     * designed to visually simulate an explosion effect, for a hit on a {@link org.example.miniproyecto_3.Model.Cell}.
     * </p>
     *
     * @return a {@link javafx.scene.Group} containing the shapes that form the explosion graphic
     *
     * @see javafx.scene.Group
     * @see javafx.scene.shape.Polygon
     * @see javafx.scene.shape.Ellipse
     * @see javafx.scene.paint.Color
     */
    public Group drawExplosion(){
        Group group = new Group();

        group.setLayoutX(0);

        // ORANGE-GLOW POLYGONS
        group.getChildren().addAll(
                createPolygon("#ff6d44", "#ffd363", -8, -1,
                        -4.981183, -12.285317, 1.9094243, 1.2169718, 13.529345, -2.8798642),
                createPolygon("#ff6d44", "#ffd363", 20, 25,
                        -14.470655, -43.280937, -24.180403, -31.439766, -11.977155, -26.276039),
                createPolygon("#ff6d44", "#ffd363", 28, 27,
                        -12.164956, -41.094124, -25.761665, -35.277626, -18.5, -27.0),
                createPolygon("#ff6d44", "#ffd363", 34, 31,
                        -15.521997, -30.280739, -25.761665, -35.277626, -27.164955, -26.094126),
                createPolygon("#ff6d44", "#ffd363", 32, 39,
                        -17.889694, -28.271122, -22.499987, -38.999977, -29.551376, -32.094181),
                createPolygon("#ff6d44", "#ffd363", 2, 9,
                        -16.048616, -9.57795, -4.45067, -2.237, -8.307785, -11.990334),
                createPolygon("#ff6d44", "#ffd363", 12, 19,
                        -24.119995, -5.704629, -9.593523, -13.094126, -18.090576, -18.783028),
                createPolygon("#ff6d44", "#ffd363", 22, 29,
                        -22.548567, -11.308422, -15.149004, -24.426214, -25.948561, -24.573433)
        );

        // YELLOW POLYGONS
        group.getChildren().addAll(
                createPolygon("#fbe328", "#ffae34", 0, 8,
                        -7.500007, 0.4999952, 0.591464, -4.379887, -2.857114, -8.52274),
                createPolygon("#fbe328", "#ffae34", 10, 18,
                        -9.408362, -6.52274, -4.9706445, -16.274597, -11.316706, -16.274605),
                createPolygon("#fbe328", "#ffae34", 17, 13,
                        -7.83429, -6.27604, -12.262848, -11.990335, -14.83429, -9.847481),
                createPolygon("#fbe328", "#ffae34", 14, 5,
                        0.620761, -4.499974, -7.7142663, -6.857154, -9.834272, -2.990334),
                createPolygon("#fbe328", "#ffae34", 12, -1,
                        -0.4285431, -9.000027, -8.977156, -2.4189229, -6.459558, 0.58107704),
                createPolygon("#fbe328", "#ffae34", 4, -1,
                        0.0293145, -11.066663, -4.551376, -4.379887, 0.0293431, -4.379887),
                createPolygon("#fbe328", "#ffae34", -5, 0,
                        -3.0000124, -8.100961, 1.8800051, -1.0941855, 6.16571, -1.0941855),
                createPolygon("#fbe328", "#ffae34", 3, -1,
                        -12.010564, 0.58107704, -4.157651, 3.5485797, -4.8370943, -1.3798871)
        );

        // CENTRAL YELLOW ELLIPSE
        Ellipse ellipse = new Ellipse(6, 6);
        ellipse.setLayoutX(1);
        ellipse.setFill(Color.web("#fbe328"));
        ellipse.setStroke(Color.web("#ffae34"));
        ellipse.setStrokeType(StrokeType.OUTSIDE);
        ellipse.setStrokeWidth(0);

        group.getChildren().add(ellipse);

        return group;
    }

    /**
     * Creates a {@link javafx.scene.Group} containing the 2D shapes that visually represent a skull.
     * <p>
     * This graphic is used as a marker or symbol within the game UI for a sunken {@link org.example.miniproyecto_3.Model.Ship}
     * The exact composition of the skull is constructed using JavaFX shapes grouped together.
     * </p>
     *
     * @return a {@link javafx.scene.Group} containing the skull shapes
     *
     * @see javafx.scene.Group
     * @see javafx.scene.shape.Shape
     */
    public Group drawSkull(){
        Group parentGroup = new Group();

        // Reusable inner groups (the "eyes")
        Group bone1 = drawBone(45);
        Group bone2 = drawBone(-45);

        // Rectangle "head"
        Rectangle head = new Rectangle(14.0, 15.0);
        head.setArcHeight(5.0);
        head.setArcWidth(5.0);
        head.setLayoutX(-7.0);
        head.setLayoutY(-2.0);
        head.setFill(Color.web("#fffeef"));
        head.setStroke(Color.BLACK);
        head.setStrokeType(StrokeType.INSIDE);

        // Hair or decoration lines
        Line line1 = new Line(-37.77852249145508, 4.4285407066345215, -37.778533935546875, 11.857099533081055);
        line1.setLayoutX(34.0);

        Line line2 = new Line(-37.77852249145508, 4.4285407066345215, -37.778533935546875, 11.857099533081055);
        line2.setLayoutX(38.0);

        Line line3 = new Line(-37.77852249145508, 4.4285407066345215, -37.778533935546875, 11.857099533081055);
        line3.setLayoutX(42.0);

        // Face ellipse
        Ellipse face = new Ellipse(11.0, 9.0);
        face.setFill(Color.web("#fffeef"));
        face.setStroke(Color.BLACK);
        face.setStrokeType(StrokeType.INSIDE);

        // Eyelashes or brows?
        Ellipse brow1 = new Ellipse(4.0, 3.0);
        brow1.setLayoutX(-5.0);
        brow1.setLayoutY(-1.0);
        brow1.setRotate(-15.0);
        brow1.setStroke(Color.web("#1f1f1f"));
        brow1.setStrokeType(StrokeType.INSIDE);

        Ellipse brow2 = new Ellipse(4.0, 3.0);
        brow2.setLayoutX(5.0);
        brow2.setLayoutY(-1.0);
        brow2.setRotate(15.0);
        brow2.setStroke(Color.web("#1f1f1f"));
        brow2.setStrokeType(StrokeType.INSIDE);

        // Mouth or nose triangle
        Polygon nose = new Polygon(
                -1.5713545083999634, 5.7142462730407715,
                3.2857754230499268, 5.714263916015625,
                0.7143511176109314, 2.5713894367218018
        );
        nose.setLayoutX(-1.0);
        nose.setStroke(Color.web("#2c2c2c"));
        nose.setStrokeLineCap(StrokeLineCap.ROUND);
        nose.setStrokeType(StrokeType.INSIDE);

        // Add all elements to parent group
        parentGroup.getChildren().addAll(
                bone1, bone2,
                head,
                line1, line2, line3,
                face,
                brow1, brow2,
                nose
        );

        return parentGroup;
    }

    /**
     * Creates a {@link javafx.scene.Group} representing a bone shape, rotated by the specified angle.
     * <p>
     * The bone graphic is constructed using JavaFX shapes and transformed according to the rotation parameter.
     * This is used as a part of the skull.
     * </p>
     *
     * @param rotation the angle in degrees to rotate the bone shape
     * @return a {@link javafx.scene.Group} containing the rotated bone shape
     *
     * @see javafx.scene.Group
     * @see javafx.scene.shape.Shape
     */
    public Group drawBone(double rotation){
        Group group = new Group();
        group.setLayoutX(-3.0);
        group.setLayoutY(-14.0);
        group.setRotate(rotation);

        Ellipse ellipse1 = new Ellipse(4.0, 3.0);
        ellipse1.setLayoutY(30.0);
        ellipse1.setFill(Color.web("#fffeef"));
        ellipse1.setStroke(Color.BLACK);
        ellipse1.setStrokeType(StrokeType.INSIDE);

        Ellipse ellipse2 = new Ellipse(4.0, 3.0);
        ellipse2.setLayoutX(6.0);
        ellipse2.setLayoutY(30.0);
        ellipse2.setFill(Color.web("#fffeef"));
        ellipse2.setStroke(Color.BLACK);
        ellipse2.setStrokeType(StrokeType.INSIDE);

        Ellipse ellipse3 = new Ellipse(4.0, 3.0);
        ellipse3.setFill(Color.web("#fffeef"));
        ellipse3.setStroke(Color.BLACK);
        ellipse3.setStrokeType(StrokeType.INSIDE);

        Ellipse ellipse4 = new Ellipse(4.0, 3.0);
        ellipse4.setLayoutX(6.0);
        ellipse4.setFill(Color.web("#fffeef"));
        ellipse4.setStroke(Color.BLACK);
        ellipse4.setStrokeType(StrokeType.INSIDE);

        Rectangle verticalBar = new Rectangle(6.0, 31.0);
        verticalBar.setArcHeight(5.0);
        verticalBar.setArcWidth(5.0);
        verticalBar.setFill(Color.web("#fffeef"));
        verticalBar.setStroke(Color.BLACK);
        verticalBar.setStrokeType(StrokeType.INSIDE);

        Rectangle topCap = new Rectangle(6.0, 3.0);
        topCap.setLayoutY(-1.0);
        topCap.setFill(Color.web("#fffeef"));
        topCap.setStroke(Color.BLACK);
        topCap.setStrokeType(StrokeType.INSIDE);
        topCap.setStrokeWidth(0.0);

        Rectangle bottomCap = new Rectangle(6.0, 3.0);
        bottomCap.setLayoutY(28.0);
        bottomCap.setFill(Color.web("#fffeef"));
        bottomCap.setStroke(Color.BLACK);
        bottomCap.setStrokeType(StrokeType.INSIDE);
        bottomCap.setStrokeWidth(0.0);

        group.getChildren().addAll(
                ellipse1, ellipse2, ellipse3, ellipse4,
                verticalBar, topCap, bottomCap
        );

        return group;
    }
}

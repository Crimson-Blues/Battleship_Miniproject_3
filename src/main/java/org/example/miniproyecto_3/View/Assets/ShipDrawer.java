package org.example.miniproyecto_3.View.Assets;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;


public class ShipDrawer {
    public ShipDrawer() {

    }
    public Pane drawSmallShip(){
        VBox smallShipPane = new VBox();
        //smallShipPane.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        smallShipPane.setAlignment(Pos.CENTER);
        smallShipPane.setPadding(Insets.EMPTY);
        smallShipPane.setSpacing(0);

        Group group = new Group();
        // Add shapes
        Rectangle rect1 = new Rectangle(-4.0, 3.0, 83.0, 46.0);
        rect1.setArcWidth(100.0);
        rect1.setArcHeight(80.0);
        rect1.setFill(Color.DODGERBLUE);
        rect1.setStroke(Color.BLACK);

        Rectangle rect2 = new Rectangle(10.0, 1.0, 69.0, 50.0);
        rect2.setArcWidth(100.0);
        rect2.setArcHeight(15.0);
        rect2.setFill(Color.DODGERBLUE);
        rect2.setStroke(Color.BLACK);

        Rectangle rect3 = new Rectangle(13.0, 7.0, 64.0, 40.0);
        rect3.setArcWidth(100.0);
        rect3.setArcHeight(15.0);
        rect3.setFill(Color.DODGERBLUE);
        rect3.setStroke(Color.BLACK);

        Polygon poly1 = new Polygon(
                23.999985, 10.571442, 19.857132, 10.571442, 23.999985, 23.0,
                5.000008, 10.571442, 5.000008, -8.571411, 25.500008, -17.0,
                19.85714, -5.142853, 23.999992, -5.142853, 32.142853, -14.428558,
                32.142853, 21.0
        );
        poly1.setLayoutX(28.0);
        poly1.setLayoutY(24.0);
        poly1.setFill(Color.web("#82dbff"));
        poly1.setStroke(Color.BLACK);

        Polygon poly2 = new Polygon(
                24.999985, 6.428558, 16.285706, 6.428558, 17.999985, 14.714294,
                9.285713, 9.0, 9.285713, -5.571442, 17.999992, -11.0,
                16.285713, -2.857147, 24.999992, -2.857147, 28.857147, -9.285706,
                28.857147, 11.428558
        );
        poly2.setLayoutX(28.0);
        poly2.setLayoutY(24.0);
        poly2.setFill(Color.web("#072b66"));
        poly2.setStroke(Color.BLACK);

        Circle circle = new Circle(41.0, 26.0, 6.0);
        circle.setStroke(Color.BLACK);

        Rectangle rect4 = new Rectangle(62.0, 12.0, 10.0, 30.0);
        rect4.setArcHeight(5.0);
        rect4.setArcWidth(5.0);
        rect4.setFill(Color.DODGERBLUE);
        rect4.setStroke(Color.BLACK);

        Rectangle rect5 = new Rectangle(22.0, 11.0, 10.0, 30.0);
        rect5.setArcHeight(5.0);
        rect5.setArcWidth(10.0);
        rect5.setFill(Color.DODGERBLUE);
        rect5.setStroke(Color.BLACK);
        rect5.setStrokeLineCap(StrokeLineCap.ROUND);

        Rectangle rect6 = new Rectangle(58.0, 15.0, 8.0, 22.0);
        rect6.setArcHeight(5.0);
        rect6.setArcWidth(5.0);
        rect6.setFill(Color.web("#eee086"));
        rect6.setStroke(Color.BLACK);

        Ellipse ellipse1 = new Ellipse(57.0, 26.0, 6.0, 9.0);
        ellipse1.setFill(Color.web("#e6ac0d"));
        ellipse1.setStroke(Color.BLACK);

        Ellipse ellipse2 = new Ellipse(57.0, 26.0, 4.0, 6.0);
        ellipse2.setFill(Color.WHITE);
        ellipse2.setStroke(Color.BLACK);

        Rectangle rect7 = new Rectangle(3.0, 30.0, 20.0, 7.0);
        rect7.setRotate(-8.0);
        rect7.setArcHeight(5.0);
        rect7.setArcWidth(2.0);
        rect7.setFill(Color.DODGERBLUE);
        rect7.setStroke(Color.BLACK);
        rect7.setStrokeLineCap(StrokeLineCap.ROUND);

        Rectangle rect8 = new Rectangle(17.0, 26.0, 9.0, 13.0);
        rect8.setRotate(-8.0);
        rect8.setArcHeight(5.0);
        rect8.setArcWidth(10.0);
        rect8.setFill(Color.DODGERBLUE);
        rect8.setStroke(Color.BLACK);
        rect8.setStrokeLineCap(StrokeLineCap.ROUND);

        Rectangle rect9 = new Rectangle(3.0, 15.0, 20.0, 7.0);
        rect9.setRotate(8.0);
        rect9.setArcHeight(5.0);
        rect9.setArcWidth(2.0);
        rect9.setFill(Color.DODGERBLUE);
        rect9.setStroke(Color.BLACK);
        rect9.setStrokeLineCap(StrokeLineCap.ROUND);

        Rectangle rect10 = new Rectangle(17.0, 13.0, 9.0, 13.0);
        rect10.setRotate(8.0);
        rect10.setArcHeight(5.0);
        rect10.setArcWidth(10.0);
        rect10.setFill(Color.DODGERBLUE);
        rect10.setStroke(Color.BLACK);
        rect10.setStrokeLineCap(StrokeLineCap.ROUND);

        group.getChildren().addAll(
                rect1, rect2, rect3, poly1, poly2, circle,
                rect4, rect5, rect6, ellipse1, ellipse2,
                rect7, rect8, rect9, rect10
        );

        return snapShot(group);
    }

    public Pane drawMediumShip(){

        Group group = new Group();
        group.setLayoutX(0.0);
        group.setLayoutY(0.0);

        // Helper method to simplify stroke settings
        StrokeType strokeType = StrokeType.INSIDE;

        group.getChildren().addAll(
                new Polygon(-10.142852783203125, 32.857147216796875, -10.142852783203125, -4.857147216796875, -50.28572082519531, 14.0) {{
                    setFill(Color.DODGERBLUE);
                    setStroke(Color.BLACK);
                    setStrokeType(strokeType);
                    setLayoutX(41.0);
                    setLayoutY(10.0);
                }},
                new Polygon(-10.142852783203125, 35.428558349609375, -10.142852783203125, -6.428558349609375, -48.28570556640625, 14.142852783203125) {{
                    setFill(Color.DODGERBLUE);
                    setStroke(Color.BLACK);
                    setStrokeType(strokeType);
                    setLayoutX(46.0);
                    setLayoutY(10.0);
                }},
                new Rectangle(149.0, 50.0) {{
                    setFill(Color.DODGERBLUE);
                    setLayoutX(2.0);
                    setLayoutY(-1.0);
                    setArcHeight(80.0);
                    setArcWidth(100.0);
                    setStroke(Color.BLACK);
                    setStrokeType(strokeType);
                }},
                new Rectangle(135.0, 50.0) {{
                    setFill(Color.DODGERBLUE);
                    setLayoutX(15.0);
                    setLayoutY(-1.0);
                    setArcHeight(80.0);
                    setArcWidth(100.0);
                    setStroke(Color.BLACK);
                    setStrokeType(strokeType);
                }},
                new Rectangle(115.0, 50.0) {{
                    setFill(Color.DODGERBLUE);
                    setLayoutX(36.0);
                    setLayoutY(-1.0);
                    setArcHeight(10.0);
                    setArcWidth(100.0);
                    setStroke(Color.BLACK);
                    setStrokeType(strokeType);
                }},
                new Rectangle(85.0, 42.0) {{
                    setFill(Color.DODGERBLUE);
                    setLayoutX(43.0);
                    setLayoutY(3.0);
                    setArcHeight(10.0);
                    setArcWidth(100.0);
                    setStroke(Color.BLACK);
                    setStrokeType(strokeType);
                }},
                new Circle(5.0) {{
                    setLayoutX(57.0);
                    setLayoutY(25.0);
                    setStroke(Color.BLACK);
                    setStrokeType(strokeType);
                }},
                new Circle(5.0) {{
                    setLayoutX(120.0);
                    setLayoutY(25.0);
                    setStroke(Color.BLACK);
                    setStrokeType(strokeType);
                }},
                new Rectangle(14.0, 20.0) {{
                    setFill(Color.web("#331fff"));
                    setLayoutX(85.0);
                    setLayoutY(14.0);
                    setStroke(Color.BLACK);
                    setStrokeType(strokeType);
                    setStrokeWidth(0.0);
                }},
                new Polygon(-18.28570556640625, 6.142852783203125, -18.28570556640625, -28.428558349609375, -2.4285888671875, -17.428558349609375, -2.4285888671875, -4.571441650390625) {{
                    setFill(Color.web("#331fff"));
                    setLayoutX(116.0);
                    setLayoutY(35.0);
                    setStroke(Color.BLACK);
                    setStrokeType(strokeType);
                    setStrokeWidth(0.0);
                }},
                new Arc(86.0, 24.0, 19.0, 17.0, 90.0, 180.0) {{
                    setFill(Color.web("#331fff"));
                    setStroke(Color.BLACK);
                    setStrokeType(strokeType);
                    setStrokeWidth(0.0);
                    setType(ArcType.ROUND);
                }},
                new Arc(84.0, 24.0, 15.0, 14.0, 90.0, 180.0) {{
                    setFill(Color.web("#331fff"));
                    setStroke(Color.BLACK);
                    setStrokeType(strokeType);
                    setType(ArcType.ROUND);
                }},
                new Rectangle(17.0, 16.0) {{
                    setFill(Color.web("#331fff"));
                    setLayoutX(83.0);
                    setLayoutY(16.0);
                    setStroke(Color.BLACK);
                    setStrokeType(strokeType);
                }},
                new Polygon(-25.857147216796875, -8.571441650390625, -25.857147216796875, -33.71429443359375, -14.428558349609375, -25.857147216796875, -14.428558349609375, -16.0) {{
                    setFill(Color.web("#331fff"));
                    setLayoutX(125.0);
                    setLayoutY(45.0);
                    setStroke(Color.BLACK);
                    setStrokeType(strokeType);
                }},
                // Add the rest of the rectangles with windows and details
                new Rectangle(18.0, 12.0) {{ setFill(Color.DODGERBLUE); setLayoutX(73.0); setLayoutY(18.0); setArcHeight(100.0); setArcWidth(50.0); setStroke(Color.BLACK); setStrokeWidth(0.0); setStrokeType(strokeType); }},
                new Rectangle(18.0, 12.0) {{ setFill(Color.DODGERBLUE); setLayoutX(79.0); setLayoutY(18.0); setArcHeight(100.0); setArcWidth(5.0); setStroke(Color.BLACK); setStrokeWidth(0.0); setStrokeType(strokeType); }},
                new Rectangle(16.0, 10.0) {{ setFill(Color.web("#90d2ff")); setLayoutX(74.0); setLayoutY(19.0); setArcHeight(100.0); setArcWidth(50.0); setStroke(Color.BLACK); setStrokeWidth(0.0); setStrokeType(strokeType); }},
                new Rectangle(16.0, 10.0) {{ setFill(Color.web("#90d2ff")); setLayoutX(80.0); setLayoutY(19.0); setArcHeight(100.0); setArcWidth(5.0); setStroke(Color.BLACK); setStrokeWidth(0.0); setStrokeType(strokeType); }},
                new Rectangle(12.0, 8.0) {{ setFill(Color.web("#ecf7ff")); setLayoutX(77.0); setLayoutY(20.0); setArcHeight(100.0); setArcWidth(50.0); setStroke(Color.BLACK); setStrokeWidth(0.0); setStrokeType(strokeType); }},
                new Rectangle(12.0, 8.0) {{ setFill(Color.web("#ecf7ff")); setLayoutX(82.0); setLayoutY(20.0); setArcHeight(100.0); setArcWidth(5.0); setStroke(Color.BLACK); setStrokeWidth(0.0); setStrokeType(strokeType); }},
                // Add the remaining mechanical parts and turrets with rotation
                new Rectangle(17.0, 40.0) {{ setFill(Color.DODGERBLUE); setLayoutX(129.0); setLayoutY(4.0); setArcHeight(5.0); setArcWidth(10.0); setStroke(Color.BLACK); setStrokeLineCap(StrokeLineCap.ROUND); setStrokeType(strokeType); }},
                new Rectangle(20.0, 5.0) {{ setFill(Color.DODGERBLUE); setLayoutX(9.0); setLayoutY(27.0); setArcHeight(5.0); setArcWidth(2.0); setStroke(Color.BLACK); setStrokeLineCap(StrokeLineCap.ROUND); setStrokeType(strokeType); }},
                new Rectangle(20.0, 5.0) {{ setFill(Color.DODGERBLUE); setLayoutX(9.0); setLayoutY(17.0); setArcHeight(5.0); setArcWidth(2.0); setStroke(Color.BLACK); setStrokeLineCap(StrokeLineCap.ROUND); setStrokeType(strokeType); }},
                new Rectangle(13.0, 19.0) {{ setFill(Color.DODGERBLUE); setLayoutX(22.0); setLayoutY(15.0); setArcHeight(5.0); setArcWidth(10.0); setStroke(Color.BLACK); setStrokeLineCap(StrokeLineCap.ROUND); setStrokeType(strokeType); }},
                new Rectangle(26.0, 7.0) {{ setFill(Color.DODGERBLUE); setLayoutX(4.0); setLayoutY(21.0); setArcHeight(5.0); setArcWidth(2.0); setStroke(Color.BLACK); setStrokeLineCap(StrokeLineCap.ROUND); setStrokeType(strokeType); }},
                // Add cannons
                new Rectangle(20.0, 5.0) {{ setFill(Color.DODGERBLUE); setLayoutX(135.0); setLayoutY(12.0); setRotate(-10.0); setArcHeight(5.0); setArcWidth(2.0); setStroke(Color.BLACK); setStrokeLineCap(StrokeLineCap.ROUND); setStrokeType(strokeType); }},
                new Rectangle(14.0, 5.0) {{ setFill(Color.DODGERBLUE); setLayoutX(137.0); setLayoutY(8.0); setRotate(-10.0); setArcHeight(5.0); setArcWidth(2.0); setStroke(Color.BLACK); setStrokeLineCap(StrokeLineCap.ROUND); setStrokeType(strokeType); }},
                new Rectangle(10.0, 16.0) {{ setFill(Color.DODGERBLUE); setLayoutX(133.0); setLayoutY(6.0); setRotate(-10.0); setArcHeight(2.0); setArcWidth(10.0); setStroke(Color.BLACK); setStrokeLineCap(StrokeLineCap.ROUND); setStrokeType(strokeType); }},
                new Rectangle(20.0, 5.0) {{ setFill(Color.DODGERBLUE); setLayoutX(136.0); setLayoutY(30.0); setRotate(10.0); setArcHeight(5.0); setArcWidth(2.0); setStroke(Color.BLACK); setStrokeLineCap(StrokeLineCap.ROUND); setStrokeType(strokeType); }},
                new Rectangle(14.0, 5.0) {{ setFill(Color.DODGERBLUE); setLayoutX(138.0); setLayoutY(34.0); setRotate(10.0); setArcHeight(5.0); setArcWidth(2.0); setStroke(Color.BLACK); setStrokeLineCap(StrokeLineCap.ROUND); setStrokeType(strokeType); }},
                new Rectangle(10.0, 16.0) {{ setFill(Color.DODGERBLUE); setLayoutX(133.0); setLayoutY(25.0); setRotate(10.0); setArcHeight(2.0); setArcWidth(10.0); setStroke(Color.BLACK); setStrokeLineCap(StrokeLineCap.ROUND); setStrokeType(strokeType); }}
        );


        return snapShot(group);
    }

    public Pane drawSubmarine(){
        Group submarineGroup = new Group();

// Tail rectangles and fins
        Rectangle tail1 = new Rectangle(234, 0, 31, 70);
        tail1.setStroke(Color.BLACK);

        Polygon tailFinTop = new Polygon(
                1, 11.14, 13, 31, 13, 11.14
        );
        tailFinTop.setLayoutX(221);
        tailFinTop.setLayoutY(39);
        tailFinTop.setStroke(Color.BLACK);

        Polygon tailFinBottom = new Polygon(
                1.71, 29.29, 13, 31, 12, 13
        );
        tailFinBottom.setLayoutX(222);
        tailFinBottom.setLayoutY(-13);
        tailFinBottom.setStroke(Color.BLACK);

// Colored tail
        Rectangle coloredTail = new Rectangle(235, 1, 29, 68);
        coloredTail.setFill(Color.web("#114677"));
        coloredTail.setStroke(Color.BLACK);

        Polygon coloredTopFin = new Polygon(
                1, 11.14, 13, 31, 13, 11.14
        );
        coloredTopFin.setLayoutX(222);
        coloredTopFin.setLayoutY(38);
        coloredTopFin.setFill(Color.web("#114677"));
        coloredTopFin.setStroke(Color.BLACK);

        Polygon coloredBottomFin = new Polygon(
                1.71, 29.29, 13, 31, 13, 11.14
        );
        coloredBottomFin.setLayoutX(222);
        coloredBottomFin.setLayoutY(-10);
        coloredBottomFin.setFill(Color.web("#114677"));
        coloredBottomFin.setStroke(Color.BLACK);

// Side pieces
        Rectangle leftPillar1 = new Rectangle(10, 37, 19, 30);
        leftPillar1.setFill(Color.DODGERBLUE);
        leftPillar1.setStroke(Color.BLACK);

        Rectangle leftPillar2 = new Rectangle(9, 3, 19, 30);
        leftPillar2.setFill(Color.DODGERBLUE);
        leftPillar2.setStroke(Color.BLACK);

// Main body
        Rectangle mainBody = new Rectangle(0, 10, 249, 50);
        mainBody.setArcWidth(100);
        mainBody.setArcHeight(80);
        mainBody.setFill(Color.DODGERBLUE);
        mainBody.setStroke(Color.BLACK);

        Rectangle innerBody = new Rectangle(52, 13, 179, 45);
        innerBody.setArcWidth(100);
        innerBody.setArcHeight(80);
        innerBody.setFill(Color.DODGERBLUE);
        innerBody.setStroke(Color.BLACK);

// Windows
        Rectangle window1 = new Rectangle(107, 21, 70, 29);
        window1.setArcWidth(30);
        window1.setArcHeight(50);
        window1.setFill(Color.web("#90e7ff"));
        window1.setStroke(Color.BLACK);
        window1.setStrokeWidth(0.0);

        Rectangle window2 = new Rectangle(126, 21, 78, 29);
        window2.setArcWidth(600);
        window2.setArcHeight(200);
        window2.setFill(Color.web("#90e7ff"));
        window2.setStroke(Color.BLACK);
        window2.setStrokeWidth(0.0);

        Rectangle windowCover = new Rectangle(205, 27, 56, 18);
        windowCover.setArcWidth(10);
        windowCover.setArcHeight(10);
        windowCover.setFill(Color.web("#67b8ff"));
        windowCover.setStroke(Color.BLACK);

// Periscope
        Rectangle scopeBase1 = new Rectangle(250, 5, 15, 18);
        scopeBase1.setStroke(Color.BLACK);

        Rectangle scopeColor1 = new Rectangle(251, 6, 14, 16);
        scopeColor1.setFill(Color.web("#102b54"));
        scopeColor1.setStroke(Color.BLACK);

        Rectangle scopeBase2 = new Rectangle(250, 47, 15, 18);
        scopeBase2.setStroke(Color.BLACK);

        Rectangle scopeColor2 = new Rectangle(251, 48, 14, 16);
        scopeColor2.setFill(Color.web("#102b54"));
        scopeColor2.setStroke(Color.BLACK);

// Exhaust/fins
        Rectangle fin1 = new Rectangle(212, 31, 30, 10);
        fin1.setArcWidth(10);
        fin1.setArcHeight(50);
        fin1.setFill(Color.web("#197086"));
        fin1.setStroke(Color.BLACK);
        fin1.setStrokeWidth(0.0);

        Rectangle fin2 = new Rectangle(217, 31, 41, 10);
        fin2.setArcWidth(500);
        fin2.setArcHeight(40);
        fin2.setFill(Color.web("#197086"));
        fin2.setStroke(Color.BLACK);
        fin2.setStrokeWidth(0.0);

// Bubble or special viewport
        Rectangle brightPanel = new Rectangle(141, 25, 42, 20);
        brightPanel.setArcWidth(20);
        brightPanel.setArcHeight(50);
        brightPanel.setFill(Color.web("#24b6db"));
        brightPanel.setStroke(Color.BLACK);

// Eyes / lights
        Circle light1 = new Circle(153, 35, 5);
        light1.setFill(Color.WHITE);
        light1.setStroke(Color.BLACK);
        light1.setStrokeWidth(0.5);

        Circle light2 = new Circle(164, 35, 4);
        light2.setFill(Color.WHITE);
        light2.setStroke(Color.BLACK);
        light2.setStrokeWidth(0.5);

// Random features / pipes
        Rectangle pipe1 = new Rectangle(69, 31, 10, 8);
        pipe1.setArcWidth(5);
        pipe1.setArcHeight(2);
        pipe1.setFill(Color.DODGERBLUE);
        pipe1.setStroke(Color.BLACK);

        Rectangle pipe2 = new Rectangle(74, 26, 22, 18);
        pipe2.setArcWidth(5);
        pipe2.setArcHeight(10);
        pipe2.setFill(Color.DODGERBLUE);
        pipe2.setStroke(Color.BLACK);

// Light blue portals
        Rectangle port1 = new Rectangle(40, 16, 9, 17);
        port1.setFill(Color.web("#b1d9ff"));
        port1.setStroke(Color.BLACK);
        port1.setStrokeWidth(0.5);

        Rectangle port2 = new Rectangle(40, 37, 9, 17);
        port2.setFill(Color.web("#b1d9ff"));
        port2.setStroke(Color.BLACK);
        port2.setStrokeWidth(0.5);

        Rectangle port3 = new Rectangle(28, 18, 9, 15);
        port3.setFill(Color.web("#b1d9ff"));
        port3.setStroke(Color.BLACK);
        port3.setStrokeWidth(0.5);

        Rectangle port4 = new Rectangle(28, 37, 9, 15);
        port4.setFill(Color.web("#b1d9ff"));
        port4.setStroke(Color.BLACK);
        port4.setStrokeWidth(0.5);

        Rectangle port5 = new Rectangle(16, 21, 9, 12);
        port5.setFill(Color.web("#b1d9ff"));
        port5.setStroke(Color.BLACK);
        port5.setStrokeWidth(0.5);

        Rectangle port6 = new Rectangle(16, 37, 9, 12);
        port6.setFill(Color.web("#b1d9ff"));
        port6.setStroke(Color.BLACK);
        port6.setStrokeWidth(0.5);

// Add all parts to the group
        submarineGroup.getChildren().addAll(
                tail1, tailFinTop, tailFinBottom, coloredTail, coloredTopFin, coloredBottomFin,
                leftPillar1, leftPillar2, mainBody, innerBody,
                window1, window2, windowCover,
                scopeBase1, scopeColor1, scopeBase2, scopeColor2,
                fin1, fin2, brightPanel,
                light1, light2, pipe1, pipe2,
                port1, port2, port3, port4, port5, port6
        );


        return snapShot(submarineGroup);
    }

    public Pane drawCarrier() {
        // Main container
        VBox carrierPane = new VBox();
        carrierPane.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        carrierPane.setAlignment(Pos.CENTER);
        carrierPane.setPadding(Insets.EMPTY);
        carrierPane.setSpacing(0);


        // Main group that contains all shapes
        Group mainGroup = new Group();

        // Create all the shapes from the FXML

        // Arcs at the top
        Arc arc1 = new Arc(-36, -6, 13, 13, 2, 180);
        arc1.setFill(Color.web("#154a7b"));
        arc1.setStroke(Color.BLACK);
        arc1.setStrokeType(StrokeType.INSIDE);
        arc1.setType(ArcType.ROUND);

        Arc arc2 = new Arc(-25, 15, 13, 13, 184, 180);
        arc2.setFill(Color.web("#154a7b"));
        arc2.setStroke(Color.BLACK);
        arc2.setStrokeType(StrokeType.INSIDE);
        arc2.setType(ArcType.ROUND);

        // Dark blue polygons (hull) with proper layout positions
        Polygon polygon1 = new Polygon(
                -73.99999237060547, -15.857147216796875,
                -73.99999237060547, -26.0,
                -100.71428680419922, -13.28570556640625
        );
        polygon1.setLayoutX(99.0);
        polygon1.setLayoutY(6.0);
        polygon1.setFill(Color.web("#082038"));
        polygon1.setStroke(Color.BLACK);
        polygon1.setStrokeType(StrokeType.INSIDE);

        Polygon polygon2 = new Polygon(
                -62.714317321777344, -8.0,
                -76.71428680419922, -8.0,
                -80.5714340209961, -39.857147216796875
        );
        polygon2.setLayoutX(99.0);
        polygon2.setLayoutY(42.0);
        polygon2.setFill(Color.web("#082038"));
        polygon2.setStroke(Color.BLACK);
        polygon2.setStrokeType(StrokeType.INSIDE);

        Polygon polygon3 = new Polygon(
                -18.99999237060547, 19.857147216796875,
                70.57141876220703, 13.142852783203125,
                66.9999771118164, -9.857147216796875,
                -18.99999237060547, -6.28570556640625
        );
        polygon3.setLayoutX(-48.0);
        polygon3.setLayoutY(0.0);
        polygon3.setFill(Color.web("#082038"));
        polygon3.setStroke(Color.BLACK);
        polygon3.setStrokeType(StrokeType.INSIDE);

        Rectangle rectangle1 = new Rectangle(25, -20, 55, 54);
        rectangle1.setFill(Color.web("#082038"));
        rectangle1.setStroke(Color.BLACK);
        rectangle1.setStrokeType(StrokeType.INSIDE);

        Polygon polygon4 = new Polygon(
                -72.99998474121094, -16.0,
                -72.99999237060547, -26.0,
                -29.999984741210938, -26.0
        );
        polygon4.setLayoutX(152.0);
        polygon4.setLayoutY(50.0);
        polygon4.setFill(Color.web("#082038"));
        polygon4.setStroke(Color.BLACK);
        polygon4.setStrokeType(StrokeType.INSIDE);

        Rectangle rectangle2 = new Rectangle(80, -20, 155, 45);
        rectangle2.setFill(Color.web("#082038"));
        rectangle2.setStroke(Color.BLACK);
        rectangle2.setStrokeType(StrokeType.INSIDE);

        Polygon polygon5 = new Polygon(
                -3.0, 32.0,
                70.71429443359375, 22.142852783203125,
                65.28570556640625, -18.463531494140625,
                -3.0, -13.0
        );
        polygon5.setLayoutX(236.0);
        polygon5.setLayoutY(-7.0);
        polygon5.setFill(Color.web("#082038"));
        polygon5.setStroke(Color.BLACK);
        polygon5.setStrokeType(StrokeType.INSIDE);

        // Light blue polygons (deck)
        Polygon polygon6 = new Polygon(
                -73.99999237060547, -15.857147216796875,
                -73.99999237060547, -26.0,
                -100.71428680419922, -13.28570556640625
        );
        polygon6.setLayoutX(98.0);
        polygon6.setLayoutY(8.0);
        polygon6.setFill(Color.DODGERBLUE);
        polygon6.setStroke(Color.BLACK);
        polygon6.setStrokeType(StrokeType.INSIDE);

        Polygon polygon7 = new Polygon(
                -67.42858123779297, -34.857147216796875,
                -73.71428680419922, -8.0,
                -76.7184066772461, -31.148345947265625
        );
        polygon7.setLayoutX(98.0);
        polygon7.setLayoutY(40.0);
        polygon7.setFill(Color.DODGERBLUE);
        polygon7.setStroke(Color.BLACK);
        polygon7.setStrokeType(StrokeType.INSIDE);


        Polygon polygon8 = new Polygon(
                -18.99999237060547, 17.28570556640625,
                70.57141876220703, 10.0,
                70.57141876220703, -9.5714111328125,
                -18.99999237060547, -5.857147216796875
        );
        polygon8.setLayoutX(-46.0);
        polygon8.setLayoutY(1.0);
        polygon8.setFill(Color.DODGERBLUE);
        polygon8.setStroke(Color.BLACK);
        polygon8.setStrokeType(StrokeType.INSIDE);

        Rectangle rectangle3 = new Rectangle(24, -18, 55, 50);
        rectangle3.setFill(Color.DODGERBLUE);
        rectangle3.setStroke(Color.BLACK);
        rectangle3.setStrokeType(StrokeType.INSIDE);

        Polygon polygon9 = new Polygon(
                -72.99999237060547, -16.0,
                -72.99999237060547, -26.0,
                -29.999984741210938, -26.0
        );
        polygon9.setLayoutX(150.0);
        polygon9.setLayoutY(48.0);
        polygon9.setFill(Color.DODGERBLUE);
        polygon9.setStroke(Color.BLACK);
        polygon9.setStrokeType(StrokeType.INSIDE);


        Rectangle rectangle4 = new Rectangle(77, -18, 155, 41);
        rectangle4.setFill(Color.DODGERBLUE);
        rectangle4.setStroke(Color.BLACK);
        rectangle4.setStrokeType(StrokeType.INSIDE);

        Polygon polygon10 = new Polygon(
                -3.0, 31.0,
                70.0, 21.4285888671875,
                65.71429443359375, -16.428558349609375,
                -3.0, -9.5714111328125
        );
        polygon10.setLayoutX(234.0);
        polygon10.setLayoutY(-8.0);
        polygon10.setFill(Color.DODGERBLUE);
        polygon10.setStroke(Color.BLACK);
        polygon10.setStrokeType(StrokeType.INSIDE);

        // Radar/sensor group
        Group radarGroup = new Group();
        radarGroup.setLayoutX(145);
        radarGroup.setLayoutY(-55);
        radarGroup.setRotate(83);
        radarGroup.setScaleX(0.7);
        radarGroup.setScaleY(2.4);

        Rectangle radarBase = new Rectangle(4, 0.142852783203125, 26, 117);
        radarBase.setFill(Color.web("#4d4c4c"));
        radarBase.setStroke(Color.BLACK);
        radarBase.setStrokeType(StrokeType.INSIDE);
        radarGroup.getChildren().add(radarBase);

        // Radar lines (orange and white)
        for (int i = 0; i < 12; i++) {
            Line radarLine = new Line(4.000007629394531, -56.0, 4.000007629394531, -63.142852783203125);
            radarLine.setLayoutX(13);
            radarLine.setLayoutY(65 + i * 9);
            radarLine.setStroke(i % 2 == 0 ? Color.web("#f5af00") : Color.WHITE);
            radarLine.setStrokeWidth(2);
            radarGroup.getChildren().add(radarLine);
        }

        Rectangle radarSide1 = new Rectangle(30, 0.142852783203125, 4, 117);
        radarSide1.setFill(Color.web("#ececec"));
        radarSide1.setStroke(Color.BLACK);
        radarSide1.setStrokeType(StrokeType.INSIDE);
        radarGroup.getChildren().add(radarSide1);

        Rectangle radarSide2 = new Rectangle(0, 0.142852783203125, 4, 117);
        radarSide2.setFill(Color.web("#ececec"));
        radarSide2.setStroke(Color.BLACK);
        radarSide2.setStrokeType(StrokeType.INSIDE);
        radarGroup.getChildren().add(radarSide2);

        // Radar side lines
        for (int i = 0; i < 10; i++) {
            Line sideLine1 = new Line(4.000007629394531, -56.0, 4.000007629394531, -63.142852783203125);
            sideLine1.setLayoutX(28);
            sideLine1.setLayoutY(172 - i * 12);
            sideLine1.setStroke(Color.web("#3b3b3b"));
            sideLine1.setStrokeWidth(2);
            radarGroup.getChildren().add(sideLine1);

            Line sideLine2 = new Line(4.000007629394531, -56.0, 4.000007629394531, -63.142852783203125);
            sideLine2.setLayoutX(-2);
            sideLine2.setLayoutY(172 - i * 12);
            sideLine2.setStroke(Color.web("#3b3b3b"));
            sideLine2.setStrokeWidth(2);
            radarGroup.getChildren().add(sideLine2);
        }


        // Create multiple groups of leaves (similar structures)
        Group[] leafGroups = {
                createMiniPlane(-63, -3, -3, "#156e2d", 1.0, 1.0),
                createMiniPlane(-47, -4, -3, "#156e2d", 1.0, 1.0),
                createMiniPlane(-56, 7, -3, "#156e2d", 1.0, 1.0),
                createMiniPlane(-38, 5, -4, "#156e2d", 1.0, 1.0),
                createMiniPlane(-30, -5, -4, "#156e2d", 1.0, 1.0),
                createMiniPlane(-20, 3, -5, "#156e2d", 1.0, 1.0),
                createMiniPlane(9, -11, -90, "#156e2d", 1.0, 1.0),
                createMiniPlane(11, 1, -90, "#156e2d", 1.0, 1.0),
                createMiniPlane(-9, -5, -4, "#156e2d", 1.0, 1.0),
                createMiniPlane(68, 10, -98, "#47a761", 1.3, 1.7),
                createMiniPlane(168, -3, -96, "#47a761", 1.3, 2.0),
                createMiniPlane(38, -16, 0, "#156e2d", 1.0, 1.0),
                createMiniPlane(27, -9, 0, "#156e2d", 1.0, 1.0),
                createMiniPlane(49, -8, 0, "#156e2d", 1.0, 1.0),
                createMiniPlane(62, -15, 0, "#156e2d", 1.0, 1.0),
                createMiniPlane(78, -15, 0, "#156e2d", 1.0, 1.0)
        };

        // Windows/circles
        Circle window1 = new Circle(235, 14, 6);
        window1.setFill(Color.web("#bae9ff"));
        window1.setStroke(Color.BLACK);
        window1.setStrokeType(StrokeType.INSIDE);

        Circle window2 = new Circle(249, 12, 5);
        window2.setFill(Color.web("#bae9ff"));
        window2.setStroke(Color.BLACK);
        window2.setStrokeType(StrokeType.INSIDE);

        // Lifeboats/groups
        Group[] lifeboatGroups = {
                createTurret(-49, -19, -125),
                createTurret(-33, -19, -45),
                createTurret(-21, 18, 36),
                createTurret(-38, 19, 135)
        };
        
        // Additional details
        Polygon detail1 = new Polygon(
                -68.28575134277344, -11.142852783203125,
                -72.42857360839844, -21.428558349609375,
                12.714248657226562, -21.428558349609375
        );
        detail1.setLayoutX(172.0);
        detail1.setLayoutY(7.0);
        detail1.setFill(Color.web("#126424"));
        detail1.setStroke(Color.BLACK);
        detail1.setStrokeType(StrokeType.INSIDE);

        Polygon detail2 = new Polygon(
                -19.71429443359375, -11.142852783203125,
                -100.99998474121094, -11.142852783203125,
                -19.71429443359375, -21.857147216796875
        );
        detail2.setLayoutX(244.0);
        detail2.setLayoutY(32.0);
        detail2.setFill(Color.web("#126424"));
        detail2.setStroke(Color.BLACK);
        detail2.setStrokeType(StrokeType.INSIDE);

        // Flag/rectangle at the back
        Rectangle flag = new Rectangle(264, 2, 28, 13);
        flag.setFill(Color.web("#ffc4d9"));
        flag.setStroke(Color.BLACK);
        flag.setStrokeType(StrokeType.INSIDE);
        flag.setArcWidth(5);
        flag.setArcHeight(5);
        flag.setRotate(-7);

        Line flagLine = new Line(3.42852783203125, -59.0, 3.42852783203125, -64.4285888671875);
        flagLine.setLayoutX(280);
        flagLine.setLayoutY(70);
        flagLine.setRotate(-6.5);
        flagLine.setStroke(Color.web("#4b4b4b"));
        flagLine.setStrokeWidth(0.8);

        Rectangle flagDetail1 = new Rectangle(276, 7, 9, 3);
        flagDetail1.setFill(Color.web("#b0b0b0"));
        flagDetail1.setStroke(Color.BLACK);
        flagDetail1.setStrokeType(StrokeType.INSIDE);
        flagDetail1.setArcWidth(5);
        flagDetail1.setArcHeight(5);
        flagDetail1.setRotate(-7);

        Rectangle flagDetail2 = new Rectangle(271, 6, 9, 6);
        flagDetail2.setFill(Color.web("#b0b0b0"));
        flagDetail2.setStroke(Color.BLACK);
        flagDetail2.setStrokeType(StrokeType.INSIDE);
        flagDetail2.setArcWidth(5);
        flagDetail2.setArcHeight(6);
        flagDetail2.setRotate(-7);

        Circle flagCircle = new Circle(276, 9, 1.5);
        flagCircle.setStroke(Color.BLACK);
        flagCircle.setStrokeType(StrokeType.INSIDE);

        Line flagLine1 = new Line(10.5714111328125, -52.28570556640625, -1.00006103515625, -65.42855834960938);
        flagLine1.setLayoutX(271);
        flagLine1.setLayoutY(68);
        flagLine1.setStroke(Color.web("#212121"));
        flagLine1.setStrokeWidth(0.8);

        Line flagLine2 = new Line(8.28570556640625, -65.85714721679688, -3.0, -52.28570556640625);
        flagLine2.setLayoutX(273);
        flagLine2.setLayoutY(68);
        flagLine2.setStroke(Color.web("#212121"));
        flagLine2.setStrokeWidth(0.8);

        // Add all shapes to main group
        mainGroup.getChildren().addAll(
                arc1, arc2, polygon1, polygon2, polygon3, rectangle1, polygon4, rectangle2, polygon5,
                polygon6, polygon7, polygon8, rectangle3, polygon9, rectangle4, polygon10,
                radarGroup, window1, window2, detail1, detail2, flag, flagLine, flagDetail1,
                flagDetail2, flagCircle, flagLine1, flagLine2
        );

        // Add leaf groups and lifeboat groups
        for (Group group : leafGroups) {
            mainGroup.getChildren().add(group);
        }

     
        for (Group group : lifeboatGroups) {
            mainGroup.getChildren().add(group);
        }

        return snapShot(mainGroup);
    }

    private Group createMiniPlane(double layoutX, double layoutY, double rotate, String color,
                                  double scaleX, double scaleY) {
        Group miniPlane = new Group();
        miniPlane.setLayoutX(layoutX);
        miniPlane.setLayoutY(layoutY);
        miniPlane.setRotate(rotate);
        miniPlane.setScaleX(scaleX);
        miniPlane.setScaleY(scaleY);

// First Polygon
        Polygon poly1 = new Polygon();
        poly1.getPoints().addAll(
                -43.14287567138672, 32.71429443359375,
                -36.99999237060547, 39.0,
                -30.57146453857422, 32.71429443359375
        );
        poly1.setLayoutX(43.0);
        poly1.setLayoutY(-30.0);
        poly1.setFill(Color.web(color));
        poly1.setStroke(Color.BLACK);
        poly1.setStrokeWidth(0.8);

        // Second Polygon
        Polygon poly2 = new Polygon();
        poly2.getPoints().addAll(
                -43.571434020996094, 32.71429443359375,
                -38.99999237060547, 37.5,
                -34.571434020996094, 32.71429443359375
        );
        poly2.setLayoutX(45.0);
        poly2.setLayoutY(-33.0);
        poly2.setFill(Color.web(color));
        poly2.setStroke(Color.BLACK);
        poly2.setStrokeWidth(0.8);

        // Rectangle
        Rectangle rect = new Rectangle(4.142875671386719, 0.28570556640625, 4.0, 9.0);
        rect.setArcWidth(3.0);
        rect.setArcHeight(10.0);
        rect.setFill(Color.web(color));
        rect.setStroke(Color.BLACK);
        rect.setStrokeWidth(0.8);

        miniPlane.getChildren().addAll(poly1, poly2, rect);
        return miniPlane;
    }

    // Helper method to create lifeboat groups
    private Group createTurret(double layoutX, double layoutY, double rotate) {
        Group group = new Group();
        group.setLayoutX(layoutX);
        group.setLayoutY(layoutY);
        group.setRotate(rotate);

        Rectangle boatPart1 = new Rectangle(2, 2, 8, 3);
        boatPart1.setFill(Color.DODGERBLUE);
        boatPart1.setStroke(Color.BLACK);
        boatPart1.setStrokeType(StrokeType.INSIDE);
        boatPart1.setStrokeWidth(0.5);
        boatPart1.setArcWidth(2);
        boatPart1.setArcHeight(1);
        boatPart1.setStrokeLineCap(StrokeLineCap.ROUND);

        Rectangle boatPart2 = new Rectangle(1, 4, 8, 3);
        boatPart2.setFill(Color.DODGERBLUE);
        boatPart2.setStroke(Color.BLACK);
        boatPart2.setStrokeType(StrokeType.INSIDE);
        boatPart2.setStrokeWidth(0.5);
        boatPart2.setArcWidth(2);
        boatPart2.setArcHeight(1);
        boatPart2.setStrokeLineCap(StrokeLineCap.ROUND);

        Rectangle boatPart3 = new Rectangle(0, 0, 5, 9);
        boatPart3.setFill(Color.DODGERBLUE);
        boatPart3.setStroke(Color.BLACK);
        boatPart3.setStrokeType(StrokeType.INSIDE);
        boatPart3.setStrokeWidth(0.5);
        boatPart3.setArcWidth(10);
        boatPart3.setArcHeight(2);
        boatPart3.setStrokeLineCap(StrokeLineCap.ROUND);

        group.getChildren().addAll(boatPart1, boatPart2, boatPart3);

        return group;
    }

    private VBox snapShot(Group group) {
        Pane wrapper = new Pane(group);
        wrapper.setScaleX(0.4);
        wrapper.setScaleY(0.4);

        // Snapshot to turn it into an image
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        WritableImage snapshot = wrapper.snapshot(params, null);
        ImageView view = new ImageView(snapshot);

        VBox vbox = new VBox();
        // Now this ImageView has the correct visual *and* layout size
        vbox.getChildren().add(view);
        vbox.setPrefWidth(Region.USE_COMPUTED_SIZE);
        vbox.setPrefHeight(Region.USE_COMPUTED_SIZE);
        vbox.setAlignment(Pos.CENTER);
        vbox.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        //vbox.setStyle("-fx-border-color: red; -fx-border-width: 2;");

        return vbox;
    }
}

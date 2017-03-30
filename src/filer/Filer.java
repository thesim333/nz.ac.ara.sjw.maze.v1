package filer;

import communal.IPoint;
import communal.Point;
import game.Game;
import game.ILoadable;
import game.ISavable;

import java.io.File;
import java.lang.reflect.Method;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;


/**
 * Created by Simon Winder on 27/03/2017.
 */
public class Filer implements ISaver, ILoader {
    private final String LEVEL_FILE = "levels.xml";
    private final String DEF_SAVE_START = "save";
    private final String DEF_SAVE_END = ".xml";
    private int currentLevel = -1;
    private final char SOMETHING = 'x';

    @Override
    public void save(ISavable game) {

    }

    @Override
    public void save(ISavable game, String fileName) {

    }

    @Override
    public void save(ISavable game, String fileName, String levelName) {

    }

    @Override
    public void loadNextLevel(ILoadable game) {
        this.loadLevel(game, this.currentLevel + 1);
    }

    @Override
    public void loadLevel(ILoadable game, int level) {

        try {
            Document doc = getXmlDoc(LEVEL_FILE);
            XPath xPath =  XPathFactory.newInstance().newXPath();

            String expression = "/mazes/maze";
            NodeList nodeList = (NodeList)xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
            this.currentLevel = level % nodeList.getLength();
            Node mazeNode = nodeList.item(this.currentLevel);
            Element mazeElement = (Element)mazeNode;

            //get name
            game.setName(mazeElement.getElementsByTagName("name").item(0).getTextContent());

            //get rows
            NodeList hRows = getWallsList(mazeElement, "horizontal");
            NodeList vRows = getWallsList(mazeElement, "vertical");

            game.createHorizontalWallArea(hRows.getLength(), hRows.item(0).getTextContent().length());
            game.createVerticalWallArea(vRows.getLength(), vRows.item(0).getTextContent().length());

            addWalls(game, "addWallLeft", vRows);
            addWalls(game, "addWallAbove", hRows);

            Element positions = (Element)mazeElement.getElementsByTagName("positions").item(0);
            //NodeList positions = mazeElement.getElementsByTagName("positions").item(0).getChildNodes();
            //get minotaur
            game.addMinotaur(this.getPosition(positions, "minotaur"));

            //get theseus
            game.addTheseus(this.getPosition(positions, "theseus"));

            //get exit
            game.addExit(this.getPosition(positions, "exit"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private NodeList getWallsList(Element maze, String type) {
        Node walls = maze.getElementsByTagName("walls").item(0);
        Element wallsE = ((Element)walls);
        Node hWalls = wallsE.getElementsByTagName(type).item(0);
        Element hWallsE = (Element)hWalls;
        return hWallsE.getElementsByTagName("row");
    }

    private IPoint getPosition(Element pos, String thing) {
        Element thingPos = (Element)pos.getElementsByTagName(thing).item(0);
        int row = Integer.parseInt(thingPos.getElementsByTagName("y").item(0).getTextContent());
        int col = Integer.parseInt(thingPos.getElementsByTagName("x").item(0).getTextContent());
        return new Point(row, col);
    }

    private void addWalls(ILoadable game, String wallMethod, NodeList wallRows) {
        for (int i = 0; i < wallRows.getLength(); i++) {
            String row = wallRows.item(i).getTextContent();
            for (int j = 0; j < row.length(); j++) {
                if (row.charAt(j) == this.SOMETHING) {
                    try {
                        Method c = Game.class.getDeclaredMethod(wallMethod, new Class[] {IPoint.class});
                        c.invoke(game, new Point(i, j));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println();
        }
    }

    private Document getXmlDoc(String file) {
        try {
            File inputFile = new File(file);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder;

            dBuilder = dbFactory.newDocumentBuilder();

            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            return doc;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void loadSave(ILoadable game, String fileName) {

    }
}

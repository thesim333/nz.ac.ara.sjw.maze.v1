package filer;

import com.sun.org.apache.xml.internal.serializer.OutputPropertiesFactory;
import communal.IPoint;
import communal.Point;
import communal.Wall;
import game.Game;
import game.ILoadable;
import game.ISavable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.*;


/**
 * Created by Simon Winder on 27/03/2017.
 */
public class Filer implements ISaver, ILoader {
    protected final String LEVEL_FILE = "levels.xml";
    protected final String SAVE_FILE = "saves.xml";
    protected int currentLevel = -1;
    protected final char SOMETHING = 'x';
    protected final char NOTHING = 'o';

    @Override
    public void save(ISavable game) {
        this.saveToXmlFile(game, this.SAVE_FILE, game.getLevelName());
    }

    protected void saveToXmlFile(ISavable game, String fileName, String level) {
        this.saveGame(game, this.haveFileWantDoc(fileName), level, fileName);
    }

    protected boolean fileExists(String file) {
        Path path = Paths.get(file);
        return (Files.isRegularFile(path));
    }

    protected Document createXmlFile() {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder;

            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            Element root = doc.createElement("mazes");
            doc.appendChild(root);
            return doc;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected Document haveFileWantDoc(String fileName) {
        if (this.fileExists(fileName)) {
            return this.getXmlDoc(fileName);
        }
        else {
            return this.createXmlFile();
        }
    }

    @Override
    public void save(ISavable game, String fileName) {
        this.saveToXmlFile(game, fileName, game.getLevelName());
    }

    @Override
    public void save(ISavable game, String fileName, String levelName) {
        this.saveToXmlFile(game, fileName, levelName);
    }

    protected void saveGame(ISavable game, Document doc, String levelName, String fileName) {
        Element rootElement = doc.getDocumentElement();
        Element mazeElement = doc.createElement("maze");
        NodeList nodes = doc.getElementsByTagName("maze");

        if (nodes.getLength() > 0) {
            nodes.item(0).getParentNode().insertBefore(mazeElement, nodes.item(0));
        }
        else {
            rootElement.appendChild(mazeElement);
        }

        Element nameElement = this.makeNode(doc, mazeElement, "name");
        this.addTextToNode(doc, nameElement, levelName);
        Element wallsElement = this.makeNode(doc, mazeElement, "walls");
        Element hWallsElement = this.makeNode(doc, wallsElement, "horizontal");
        Element vWallsElement = this.makeNode(doc, wallsElement, "vertical");

        this.addWallsNodes(doc, hWallsElement,"whatsAbove", game);
        this.addWallsNodes(doc, vWallsElement, "whatsLeft", game);

        Element positionsElement = this.makeNode(doc, mazeElement, "positions");
        this.addPositionNode(doc, positionsElement, "theseus", game.wheresTheseus());
        this.addPositionNode(doc, positionsElement, "minotaur", game.wheresMinotaur());
        this.addPositionNode(doc, positionsElement, "exit", game.wheresExit());

        this.saveXmlToFile(doc, fileName);
    }

    protected void saveXmlToFile(Document doc, String fileName) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new StringWriter());

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputPropertiesFactory.S_KEY_INDENT_AMOUNT, "4");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.transform(source, result);

            File file = new File(fileName);
            FileOutputStream fos = new FileOutputStream(file);

            String xmlString = result.getWriter().toString();
            fos.write(xmlString.getBytes());
            fos.flush();
            fos.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected Element makeNode(Document doc, Element parent, String child) {
        Element thisElement = doc.createElement(child);
        parent.appendChild(thisElement);
        return thisElement;
    }

    protected void addTextToNode(Document doc, Element parent, String text) {
        Text textNode = doc.createTextNode(text);
        parent.appendChild(textNode);
    }

    protected String makeWallText(String wallMethod, ISavable game, int row) {
        int cols = game.getWidthAcross();
        StringBuilder rowText = new StringBuilder();

        for (int c = 0; c < cols; c++) {
            try {
                Method m = Game.class.getDeclaredMethod(wallMethod, new Class[] {IPoint.class});

                if (m.invoke(game, new Point(row, c)) == Wall.SOMETHING) {
                    rowText.append(this.SOMETHING);
                }
                else {
                    rowText.append(this.NOTHING);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return rowText.toString();
    }

    protected void addWallsNodes(Document doc, Element parent, String wallMethod, ISavable game) {
        int rows = game.getDepthDown();

        for (int r = 0; r < rows; r++) {
            Element wallRow = this.makeNode(doc, parent, "row");
            String wallText = this.makeWallText(wallMethod, game, r);
            this.addTextToNode(doc, wallRow, wallText);
        }
    }

    protected void addPositionNode(Document doc, Element pos, String thing, IPoint p) {
        Element characterElement = this.makeNode(doc, pos, thing);
        Element xElement = this.makeNode(doc, characterElement, "x");
        this.addTextToNode(doc, xElement, String.valueOf(p.getCol()));
        Element yElement = this.makeNode(doc, characterElement, "y");
        this.addTextToNode(doc, yElement, String.valueOf(p.getRow()));
    }

    @Override
    public void loadNextLevel(ILoadable game) {
        this.loadLevel(game, ++this.currentLevel);
    }

    @Override
    public void loadLevel(ILoadable game, int level) {
        this.loadLevelOrLoadSaveByNumber(game, level, this.LEVEL_FILE);
    }

    protected void loadLevelOrLoadSaveByNumber(ILoadable game, int level, String fileName) {
        try {
            Document doc = getXmlDoc(fileName);
            XPath xPath =  XPathFactory.newInstance().newXPath();
            String expression = "/mazes/maze";
            NodeList nodeList = (NodeList)xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
            int levelToLoad = level % nodeList.getLength();
            Node mazeNode = nodeList.item(levelToLoad);
            Element mazeElement = (Element)mazeNode;

            //get name
            game.setName(mazeElement.getElementsByTagName("name").item(0).getTextContent());
            //get rows
            NodeList hRows = getWallsList(mazeElement, "horizontal");
            NodeList vRows = getWallsList(mazeElement, "vertical");

            game.setDepthDown(hRows.getLength());
            game.setWidthAcross(vRows.item(0).getTextContent().length());

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

    protected NodeList getWallsList(Element maze, String type) {
        Node walls = maze.getElementsByTagName("walls").item(0);
        Element wallsE = ((Element)walls);
        Node hWalls = wallsE.getElementsByTagName(type).item(0);
        Element hWallsE = (Element)hWalls;
        return hWallsE.getElementsByTagName("row");
    }

    protected IPoint getPosition(Element pos, String thing) {
        Element thingPos = (Element)pos.getElementsByTagName(thing).item(0);
        int row = Integer.parseInt(thingPos.getElementsByTagName("y").item(0).getTextContent());
        int col = Integer.parseInt(thingPos.getElementsByTagName("x").item(0).getTextContent());
        return new Point(row, col);
    }

    protected void addWalls(ILoadable game, String wallMethod, NodeList wallRows) {
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
        }
    }

    protected Document getXmlDoc(String file) {
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
        this.loadLevelOrLoadSaveByNumber(game, 0, fileName);
    }

    @Override
    public void loadSave(ILoadable game) {
        this.loadLevelOrLoadSaveByNumber(game, 0, this.SAVE_FILE);
    }
}

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

/**
 * Main class for the Deep Space Planetary System Analysis application.
 * This class handles the user interface and command processing.
 */
public class DeepSpacePlanetarySystem {
    private SpaceNode root;
    private Scanner scanner;

    /**
     * Constructor initializes the scanner for user input.
     */
    public DeepSpacePlanetarySystem() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Main method to start the application.
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        DeepSpacePlanetarySystem system = new DeepSpacePlanetarySystem();
        system.run();
    }

    /**
     * Runs the main application loop, processing user commands.
     */
    public void run() {
        boolean running = true;
        System.out.println("Deep Space Planetary System Analysis");
        System.out.println("Type 'help' for available commands");

        while (running) {
            System.out.print(">> ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                running = false;
                continue;
            }

            if (input.equalsIgnoreCase("help")) {
                printHelp();
                continue;
            }

            try {
                processCommand(input);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        scanner.close();
        System.out.println("Program terminated.");
    }

    /**
     * Prints the help menu with available commands.
     */
    private void printHelp() {
        System.out.println("Available commands:");
        System.out.println("- create planetSystem \"name\" \"temperature\" \"pressure\" \"humidity\" \"radiation\"");
        System.out.println("- addPlanet \"planetName\" \"parentName\" \"temperature\" \"pressure\" \"humidity\" \"radiation\"");
        System.out.println("- addSatellite \"satelliteName\" \"parentName\" \"temperature\" \"pressure\" \"humidity\" \"radiation\"");
        System.out.println("- findRadiationAnomalies \"threshold\"");
        System.out.println("- getPathTo \"nodeName\"");
        System.out.println("- printMissionReport [nodeName]");
        System.out.println("- help: Show this help menu");
        System.out.println("- exit: Exit the program");
    }

    /**
     * Processes user commands by parsing the input and calling appropriate methods.
     * @param input The command input from the user
     */
    private void processCommand(String input) {
        String[] parts = input.split(" ", 2);
        String command = parts[0].toLowerCase();
        String args = parts.length > 1 ? parts[1] : "";

        switch (command) {
            case "create":
                if (args.startsWith("planetSystem ")) {
                    createPlanetSystem(args.substring("planetSystem ".length()));
                } else {
                    System.out.println("Error: Invalid create command format");
                }
                break;
            case "addplanet":
                addPlanet(args);
                break;
            case "addsatellite":
                addSatellite(args);
                break;
            case "findradiationanomalies":
                findRadiationAnomalies(args);
                break;
            case "getpathto":
                getPathTo(args);
                break;
            case "printmissionreport":
                printMissionReport(args);
                break;
            default:
                System.out.println("Error: Unknown command. Type 'help' for available commands.");
        }
    }

    /**
     * Creates a new planetary system with a star as the root node.
     * @param args Arguments containing star details
     */
    private void createPlanetSystem(String args) {
        if (root != null) {
            System.out.println("Error: Planetary system already exists.");
            return;
        }

        String[] params = parseParameters(args);
        if (params.length != 5) {
            System.out.println("Error: Invalid number of parameters for create planetSystem.");
            return;
        }

        String name = params[0];
        try {
            double temperature = Double.parseDouble(params[1]);
            double pressure = Double.parseDouble(params[2]);
            double humidity = Double.parseDouble(params[3]);
            double radiation = Double.parseDouble(params[4]);

            // Validate parameters
            validateParameters(temperature, pressure, humidity, radiation);

            // Stars should not have humidity
            if (humidity != 0) {
                System.out.println("Error: Stars do not have humidity, it must be 0.");
                return;
            }

            SensorData sensorData = new SensorData(temperature, pressure, humidity, radiation);
            root = new SpaceNode(name, "Star", sensorData);
            System.out.println("Star created successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid number format in parameters.");
        }
    }

    /**
     * Adds a planet to the system.
     * @param args Arguments containing planet details
     */
    private void addPlanet(String args) {
        if (root == null) {
            System.out.println("Error: Planetary system does not exist. Create one first.");
            return;
        }

        String[] params = parseParameters(args);
        if (params.length != 6) {
            System.out.println("Error: Invalid number of parameters for addPlanet.");
            return;
        }

        String planetName = params[0];
        String parentName = params[1];
        
        try {
            double temperature = Double.parseDouble(params[2]);
            double pressure = Double.parseDouble(params[3]);
            double humidity = Double.parseDouble(params[4]);
            double radiation = Double.parseDouble(params[5]);

            // Validate parameters
            validateParameters(temperature, pressure, humidity, radiation);

            // Check if node with this name already exists
            if (findNode(root, planetName) != null) {
                System.out.println("Error: A node with this name already exists.");
                return;
            }

            // Find parent node
            SpaceNode parentNode = findNode(root, parentName);
            if (parentNode == null) {
                System.out.println("Error: Parent node not found.");
                return;
            }

            // Planets can now be children of other planets
            if (!parentNode.getType().equals("Star") && !parentNode.getType().equals("Planet")) {
                System.out.println("Error: Planets can only be added to a Star or Planet.");
                return;
            }
            
            // Check if parent already has a planet child
            boolean hasPlaneChild = false;
            for (SpaceNode child : parentNode.getChildren()) {
                if (child.getType().equals("Planet")) {
                    hasPlaneChild = true;
                    break;
                }
            }
            
            if (hasPlaneChild) {
                System.out.println("Error: Parent node already has a planet child. Only one planet per parent is allowed.");
                return;
            }

            SensorData sensorData = new SensorData(temperature, pressure, humidity, radiation);
            SpaceNode planetNode = new SpaceNode(planetName, "Planet", sensorData);
            parentNode.addChild(planetNode);
            System.out.println("Planet added successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid number format in parameters.");
        }
    }

    /**
     * Adds a satellite/moon to a planet.
     * @param args Arguments containing satellite details
     */
    private void addSatellite(String args) {
        if (root == null) {
            System.out.println("Error: Planetary system does not exist. Create one first.");
            return;
        }

        String[] params = parseParameters(args);
        if (params.length != 6) {
            System.out.println("Error: Invalid number of parameters for addSatellite.");
            return;
        }

        String satelliteName = params[0];
        String parentName = params[1];
        
        try {
            double temperature = Double.parseDouble(params[2]);
            double pressure = Double.parseDouble(params[3]);
            double humidity = Double.parseDouble(params[4]);
            double radiation = Double.parseDouble(params[5]);

            // Validate parameters
            validateParameters(temperature, pressure, humidity, radiation);

            // Check if node with this name already exists
            if (findNode(root, satelliteName) != null) {
                System.out.println("Error: A node with this name already exists.");
                return;
            }

            // Find parent node
            SpaceNode parentNode = findNode(root, parentName);
            if (parentNode == null) {
                System.out.println("Error: Parent node not found.");
                return;
            }

            // Satellites can only be added to a planet
            if (!parentNode.getType().equals("Planet")) {
                System.out.println("Error: Moons can only be added to a Planet.");
                return;
            }

            SensorData sensorData = new SensorData(temperature, pressure, humidity, radiation);
            SpaceNode satelliteNode = new SpaceNode(satelliteName, "Moon", sensorData);
            parentNode.addChild(satelliteNode);
            System.out.println("Satellite added successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid number format in parameters.");
        }
    }

    /**
     * Finds nodes with radiation values exceeding the given threshold.
     * @param args Arguments containing the threshold value
     */
    private void findRadiationAnomalies(String args) {
        if (root == null) {
            System.out.println("Error: Planetary system does not exist.");
            return;
        }

        try {
            double threshold = Double.parseDouble(args.trim());
            List<SpaceNode> anomalies = new ArrayList<>();
            findRadiationAnomaliesRecursive(root, threshold, anomalies);
            
            if (anomalies.isEmpty()) {
                System.out.println("No radiation anomalies found above threshold " + threshold + " Sieverts.");
            } else {
                for (SpaceNode node : anomalies) {
                    System.out.println(node.getName() + ": " + String.format("%.4f", node.getSensorData().getRadiation()) + " Sieverts");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid threshold value.");
        }
    }

    /**
     * Recursively traverses the tree to find radiation anomalies.
     * @param node Current node
     * @param threshold Radiation threshold
     * @param anomalies List to store found anomalies
     */
    private void findRadiationAnomaliesRecursive(SpaceNode node, double threshold, List<SpaceNode> anomalies) {
        if (node.getSensorData().getRadiation() > threshold) {
            anomalies.add(node);
        }
        
        for (SpaceNode child : node.getChildren()) {
            findRadiationAnomaliesRecursive(child, threshold, anomalies);
        }
    }

    /**
     * Gets the path from root to the specified node.
     * @param args Arguments containing the target node name
     */
    private void getPathTo(String args) {
        if (root == null) {
            System.out.println("Error: Planetary system does not exist.");
            return;
        }

        String nodeName = args.trim();
        Stack<String> path = new Stack<>();
        boolean found = getPathToRecursive(root, nodeName, path);
        
        if (!found) {
            System.out.println("Error: Node not found.");
            return;
        }
        
        // Print the path
        System.out.println("└── " + path.pop());
        StringBuilder indent = new StringBuilder("   ");
        
        while (!path.isEmpty()) {
            System.out.println(indent + "└── " + path.pop());
            indent.append("   ");
        }
    }

    /**
     * Recursively finds the path to the specified node.
     * @param node Current node
     * @param targetName Target node name
     * @param path Stack to store the path
     * @return True if path is found, false otherwise
     */
    private boolean getPathToRecursive(SpaceNode node, String targetName, Stack<String> path) {
        if (node.getName().equals(targetName)) {
            path.push(node.getName());
            return true;
        }
        
        for (SpaceNode child : node.getChildren()) {
            if (getPathToRecursive(child, targetName, path)) {
                path.push(node.getName());
                return true;
            }
        }
        
        return false;
    }

    /**
     * Prints a mission report for the entire system or a specific node.
     * @param args Arguments containing optional node name
     */
    private void printMissionReport(String args) {
        if (root == null) {
            System.out.println("Error: Planetary system does not exist.");
            return;
        }

        String nodeName = args.trim();
        if (nodeName.isEmpty()) {
            printMissionReportRecursive(root, "", true, true);
        } else {
            SpaceNode node = findNode(root, nodeName);
            if (node == null) {
                System.out.println("Error: Node not found.");
                return;
            }
            System.out.println(node.toString());
        }
    }

    /**
     * Recursively prints the mission report with proper tree formatting.
     * @param node Current node
     * @param prefix Current line prefix
     * @param isTail Whether node is the last child
     * @param isRoot Whether node is the root
     */
    private void printMissionReportRecursive(SpaceNode node, String prefix, boolean isTail, boolean isRoot) {
        // Print current node
        if (isRoot) {
            System.out.println("├─ " + node.toString());
        } else {
            System.out.println(prefix + (isTail ? "└─ " : "├─ ") + node.toString());
        }

        // Print children
        List<SpaceNode> children = node.getChildren();
        for (int i = 0; i < children.size(); i++) {
            boolean lastChild = (i == children.size() - 1);
            String newPrefix = prefix + (isRoot ? "│  " : (isTail ? "   " : "│  "));
            printMissionReportRecursive(children.get(i), newPrefix, lastChild, false);
        }
    }

    /**
     * Finds a node by name using DFS traversal.
     * @param node Current node
     * @param name Target node name
     * @return Found node or null if not found
     */
    private SpaceNode findNode(SpaceNode node, String name) {
        if (node.getName().equals(name)) {
            return node;
        }
        
        for (SpaceNode child : node.getChildren()) {
            SpaceNode result = findNode(child, name);
            if (result != null) {
                return result;
            }
        }
        
        return null;
    }

    /**
     * Parses command parameters, handling quoted strings properly.
     * @param args Command arguments string
     * @return Array of parsed parameters
     */
    private String[] parseParameters(String args) {
        List<String> params = new ArrayList<>();
        StringBuilder currentParam = new StringBuilder();
        boolean inQuotes = false;
        
        for (int i = 0; i < args.length(); i++) {
            char c = args.charAt(i);
            
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ' ' && !inQuotes) {
                if (currentParam.length() > 0) {
                    params.add(currentParam.toString());
                    currentParam = new StringBuilder();
                }
            } else {
                currentParam.append(c);
            }
        }
        
        if (currentParam.length() > 0) {
            params.add(currentParam.toString());
        }
        
        return params.toArray(new String[0]);
    }

    /**
     * Validates sensor data parameters.
     * @param temperature Temperature value
     * @param pressure Pressure value
     * @param humidity Humidity value
     * @param radiation Radiation value
     * @throws IllegalArgumentException If any parameter is invalid
     */
    private void validateParameters(double temperature, double pressure, double humidity, double radiation) {
        if (temperature < 0) {
            throw new IllegalArgumentException("Temperature cannot be negative.");
        }
        if (pressure < 0) {
            throw new IllegalArgumentException("Pressure cannot be negative.");
        }
        if (humidity < 0 || humidity > 100) {
            throw new IllegalArgumentException("Humidity must be between 0 and 100.");
        }
        if (radiation < 0) {
            throw new IllegalArgumentException("Radiation cannot be negative.");
        }
    }
}
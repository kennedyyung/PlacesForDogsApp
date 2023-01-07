package ui;

//import model.AllLists;

import model.EventLog;
import model.Event;
import model.Location;
import model.LocationList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

//Places for dogs application
public class PlacesForDogsApp extends JFrame {
    private ArrayList<LocationList> allLists;
    private static final String JSON_STORE = "./data/myFile.json";

    public static final Color LIGHT_BLUE = new Color(207, 228, 235);

    private JButton addLocationList;
    private JButton loadLists;
    private JButton saveLists;
    private JButton viewLocationLists;
    private JButton addALocation;
    private JButton editLocation;
    private JTextField listName;
    private JTextField nameTextField;
    private JTextField addressTextField;
    private JTextField cityTextField;
    private JTextField provinceTextField;
    private JTextField postalCodeTextField;
    private JTextField commentsTextField;
    private JComboBox ratingBox;
    JList<String> listAddPanel;
    private static ImageIcon oreo = new ImageIcon("OreoPicture.png");


    //EFFECTS: runs the places for dogs application
    public PlacesForDogsApp() {
        allLists = new ArrayList<>();
        super.setTitle("Places for Dogs Application");
        setVisible(true);
        setLayout(new BorderLayout());
        setSize(1100, 1000);
        add(newMainMenu(), BorderLayout.NORTH);
        add(menuButtons(), BorderLayout.SOUTH);
        setLocationListNamePanel();
        buttonListeners();
        setCloseOperations();
    }

    //MODIFIES: this
    //EFFECTS: sets the Close operation as well as adding a window listener to preform an action in console when
    // closing application
    private  void setCloseOperations() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        WindowListener windowExitListener = new WindowAdapter() {
            //@Override
            public void windowClosing(WindowEvent e) {
                printEventLog(EventLog.getInstance());
            }
        };
        this.addWindowListener(windowExitListener);
    }

    //REQUIRES: eventlog
    //MODIFIES: this
    //EFFECTS: prints out all events in the eventlog
    public void printEventLog(EventLog el) {
        for (Event next : el) {
            System.out.println(next);
        }
    }

    //MODIFIES: this
    //EFFECTS: a jpanel showing the introduction messages
    private JPanel newMainMenu() {
        JPanel organizer = new JPanel();
        organizer.setLayout(new BoxLayout(organizer, BoxLayout.Y_AXIS));
        organizer.setBorder(BorderFactory.createTitledBorder("Introduction"));
        String introduction = "Hi! Welcome to Places for dogs application! This application allows you "
                + "to keep track of different locations and notes about them where you can take your dog!";
        JLabel applicationIntro = new JLabel(introduction);
        String instructions = "To start, create a location list using the buttons below";
        JLabel applicationInstructions = new JLabel(instructions);
        organizer.add(applicationIntro);
        organizer.add(applicationInstructions);

        return organizer;

    }

    //MODIFIES: this
    //EFFECTS: Jpanel displaying the menu buttons that the user can choose from
    private JPanel menuButtons() {
        JPanel organizer = new JPanel();
        organizer.setBorder(BorderFactory.createTitledBorder("Menu buttons"));
        organizer.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.weightx = 1;
        g.weighty = 0.25;
        g.insets = new Insets(15, 0, 15, 0);
        g.gridwidth = 1;
        g.fill = 1;
        addLocationList = new JButton("Add a Location List");
        viewLocationLists = new JButton("View Location lists");
        saveLists = new JButton("Save Current Lists");
        loadLists = new JButton("Load Lists");
        addALocation = new JButton("Add Location");
        editLocation = new JButton("Edit a Location");
        buttonColors();
        organizer.add(addLocationList, g);
        organizer.add(viewLocationLists, g);
        organizer.add(saveLists, g);
        organizer.add(loadLists, g);
        organizer.add(addALocation, g);
        organizer.add(editLocation, g);
        return organizer;
    }

    //EFFECTS: sets button colours to light blue
    //MODIFIES: addLocationList, viewLocationLists, saveLists, loadLists, addALocation
    private void buttonColors() {
        addLocationList.setBackground(LIGHT_BLUE);
        viewLocationLists.setBackground(LIGHT_BLUE);
        saveLists.setBackground(LIGHT_BLUE);
        loadLists.setBackground(LIGHT_BLUE);
        addALocation.setBackground(LIGHT_BLUE);
        editLocation.setBackground(LIGHT_BLUE);
        //  setLocationListName.setBackground(LIGHT_BLUE);
    }

    //MODIFIES: addLocationList, viewLocationLists, saveLists, loadLists, addALocation
    //EFFECTS: creates action listeners for these buttons
    private void buttonListeners() {
        addLocationList.addActionListener(l -> setLocationListNamePanel());
        viewLocationLists.addActionListener(l -> viewLocationListsPanel(-1, 0));
        saveLists.addActionListener(l -> saveFile());
        loadLists.addActionListener(l -> loadFile());
        // setLocationListName.addActionListener(l -> setLocationListNamePanel());
        addALocation.addActionListener(l -> addALocationPanel());
        editLocation.addActionListener(l -> editLocationPanel(-1,0));
    }

    //MODIFIES: this
    //EFFECTS: Jpanel for the user to interact with and create a location list
    public void setLocationListNamePanel() {
        JPanel organizer = new JPanel();
        organizer.setBorder(BorderFactory.createTitledBorder("Add a LocationList"));
        JPanel organizer2 = new JPanel();
        JLabel setLocationListName = new JLabel("Enter List name here: ");
        listName = new JTextField(15);
        JButton setLocationListNameButton = new JButton("Set List Name");
        setLocationListNameButton.setBackground(LIGHT_BLUE);
        locationListNameButtonListener(setLocationListNameButton);

        JLabel userMessage = new JLabel("If no name is given, list name will be set to untitled");
        organizer2.add(setLocationListName);
        organizer2.add(listName);
        organizer2.add(setLocationListNameButton);
        organizer.add(organizer2, BorderLayout.NORTH);
        organizer.add(userMessage, BorderLayout.SOUTH);
        refreshGui(organizer);
        buttonListeners();
    }

    //MODIFIES: this
    //REQUIRES: setLocationListNameButton
    //EFFECTS: button action listener for the setting the location list name button
    private void locationListNameButtonListener(JButton setLocationListNameButton) {
        setLocationListNameButton.addActionListener(l -> setLocationListName());
    }

    //MODIFIES: locationList
    //EFFECTS: menthod to set the location list name to the user input, or set name as "Untitled List"
    // if no user input
    //Displays message for user to confirm the list of inputted name has been created
    private void setLocationListName() {
        JPanel organizer = new JPanel();
        LocationList locationList = new LocationList();
        organizer.setBorder(BorderFactory.createTitledBorder("Location List Name"));
        if (listName.getText().equals("")) {
            locationList.setLocationListName("Untitled List");
        } else {
            locationList.setLocationListName(listName.getText());
        }
        allLists.add(locationList);

        String message = "Location List name is now '" + locationList.getLocationListName() + "'";
        JLabel output = new JLabel(message);
        organizer.add(output);
        refreshGui(organizer);
        buttonListeners();
    }

    //MODIFIES: this
    //REQUIRES: locationList, locationNumber
    //EFFECTS: organizer for the panel that appears when viewing location lists
    public void viewLocationListsPanel(int locationList, int locationNumber) {
        JPanel organizer = new JPanel();
        organizer.setBorder(BorderFactory.createTitledBorder("View Location lists"));
        organizer.add(makeLocationLists(), BorderLayout.WEST);
        organizer.add(makeListOfLocations(locationList), BorderLayout.CENTER);
        organizer.add(makeLocationInfo(locationList, locationNumber), BorderLayout.EAST);
        refreshGui(organizer);
        buttonListeners();
    }

    //MODIFIES: this
    //EFFECTS: A Jlist which shows all the location lists created
    public JList<String> makeLocationLists() {
        DefaultListModel<String> model = new DefaultListModel<>();
        JList<String> list = new JList<>(model);
        list.setPreferredSize(new Dimension(300, 600));


        for (int i = 0; i < allLists.size(); i++) {
            model.addElement(allLists.get(i).getLocationListName());
        }

        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        MouseListener mouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedItem = list.getSelectedIndex();
                    System.out.println("click");
                    viewLocationListsPanel(selectedItem, -1);

                }
            }
        };
        list.addMouseListener(mouseListener);

        return list;
    }

    //REQUIRES: locationListNumber
    //MODIFIES: this
    //EFFECTS: Jlist which displays the list of Locations for the location list
    public JList<String> makeListOfLocations(int locationListNumber) {
        DefaultListModel<String> model = new DefaultListModel<>();
        JList<String> list = new JList<>(model);
        list.setPreferredSize(new Dimension(300, 600));

        if (locationListNumber == -1) {
            System.out.println("blank");
        } else {
            for (int i = 0; i < allLists.get(locationListNumber).getLocationList().size(); i++) {
                model.addElement(allLists.get(locationListNumber).getLocationList().get(i).getName());
            }
        }

        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        MouseListener mouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedItem = list.getSelectedIndex();
                    System.out.println("click");

                    viewLocationListsPanel(locationListNumber, selectedItem);

                }
            }
        };
        list.addMouseListener(mouseListener);

        return list;
    }

    //MODIFIES: this
    //REQUIRES: locationListNumber and locationNumber
    //EFFECTS: JPanel that displays the information for the location that the user selects
    public JPanel makeLocationInfo(int locationListNumber, int locationNumber) {
        JPanel organizer = new JPanel();
        JPanel locationInfo = new JPanel();

        locationInfo.setBackground(Color.WHITE);
        organizer.setPreferredSize(new Dimension(300, 600));
        organizer.setBackground(Color.WHITE);
        organizer.setLayout(new BoxLayout(organizer, BoxLayout.Y_AXIS));
        JLabel commentsIntro = new JLabel("Location comments: ");

        if (locationListNumber == -1 || locationNumber == -1) {
            System.out.println("blank");
        } else {

            //adding the different location information labels into one panel
            locationInfo.setLayout(new GridLayout(0, 1));
            locationInfo.add(name(locationListNumber, locationNumber));
            locationInfo.add(address(locationListNumber, locationNumber));
            locationInfo.add(postalCode(locationListNumber, locationNumber));
            locationInfo.add(city(locationListNumber, locationNumber));
            locationInfo.add(province(locationListNumber, locationNumber));
            locationInfo.add(rating(locationListNumber, locationNumber));
            locationInfo.add(commentsIntro);
            locationInfo.add(comments(locationListNumber, locationNumber));

            organizer.add(locationInfo);
            organizer.add(commentsAndRatingsButtons(locationListNumber, locationNumber));
        }

        return organizer;
    }

    private JLabel rating(int locationListNumber, int locationNumber) {
        JLabel hasRating = new JLabel("Location rating: " + allLists.get(locationListNumber).getLocationList()
                .get(locationNumber).getRating());
        JLabel noRating = new JLabel("Location rating: N/A");
        if (allLists.get(locationListNumber).getLocationList().get(locationNumber).getRating() == 20) {
            return noRating;
        } else {
            return hasRating;
        }
    }


    //MODIFIES: this
    //REQUIRES: locationListNumber and locationNumber
    //EFFECTS:Jlabel telling the user the location name of the selected location
    private JLabel name(int locationListNumber, int locationNumber) {
        JLabel name = new JLabel("Location name: " + allLists.get(locationListNumber).getLocationList()
                .get(locationNumber).getName());
        return name;
    }

    //MODIFIES: this
    //REQUIRES: locationListNumber and locationNumber
    //EFFECTS:Jlabel telling the user the location address of the selected location
    private JLabel address(int locationListNumber, int locationNumber) {
        JLabel address = new JLabel("Location address: " + allLists.get(locationListNumber).getLocationList()
                .get(locationNumber).getStreetName());
        return address;
    }

    //MODIFIES: this
    //REQUIRES: locationListNumber and locationNumber
    //EFFECTS:Jlabel telling the user the postal code name of the selected location
    private JLabel postalCode(int locationListNumber, int locationNumber) {
        JLabel postalCode = new JLabel("Location postal code: " + allLists.get(locationListNumber).getLocationList()
                .get(locationNumber).getPostalCode());
        return postalCode;
    }

    //MODIFIES: this
    //REQUIRES: locationListNumber and locationNumber
    //EFFECTS:Jlabel telling the user the location city of the selected location
    private JLabel city(int locationListNumber, int locationNumber) {
        JLabel city = new JLabel("Location city: " + allLists.get(locationListNumber).getLocationList()
                .get(locationNumber).getCity());
        return city;
    }

    //MODIFIES: this
    //REQUIRES: locationListNumber and locationNumber
    //EFFECTS:Jlabel telling the user the location province of the selected location
    private JLabel province(int locationListNumber, int locationNumber) {
        JLabel province = new JLabel("Location province: " + allLists.get(locationListNumber).getLocationList()
                .get(locationNumber).getProvince());
        return province;
    }

    //MODIFIES: this
    //REQUIRES: locationListNumber and locationNumber
    //EFFECTS:JList displaying the comments for the location
    private JList comments(int locationListNumber, int locationNumber) {
        DefaultListModel commentsModel;
        commentsModel = new DefaultListModel();

        //String commentsString [] = {};
        for (String comment : allLists.get(locationListNumber).getLocationList().get(locationNumber).getComments()) {
            commentsModel.addElement("    " + "-" + comment);
        }
        JList commentsList = new JList(commentsModel);
        return commentsList;
    }




    //MODIFIES: this
    //EFFECTS: Jpanel displaying the rating combobox, and the comments panel allowing the user to add a rating and
    //comment to the selected location
    public JPanel commentsAndRatingsButtons(int locationListNumber, int locationNumber) {
        JPanel organizer = new JPanel();
        organizer.setBackground(LIGHT_BLUE);

        JLabel description = new JLabel("Choose a rating and add a comment for the ");
        JLabel description2 = new JLabel("selected location");
        JPanel descriptions = new JPanel();
        descriptions.setBackground(LIGHT_BLUE);
        descriptions.setLayout(new BoxLayout(descriptions, BoxLayout.Y_AXIS));
        descriptions.add(description, BorderLayout.NORTH);
        descriptions.add(description2, BorderLayout.SOUTH);

        JPanel commentsPanel = new JPanel();
        commentsPanel.setLayout(new BoxLayout(commentsPanel, BoxLayout.Y_AXIS));
        JButton addRatingAndCommentButton = new JButton("Set rating and Comment");

        commentsPanel.add(commentsPanel(locationListNumber, locationNumber), BorderLayout.NORTH);

        organizer.add(descriptions, BorderLayout.NORTH);

        organizer.add(ratingPanel(locationListNumber, locationNumber), BorderLayout.CENTER);
        organizer.add(commentsPanel, BorderLayout.CENTER);
        organizer.add(addRatingAndCommentButton, BorderLayout.SOUTH);
        ratingAndCommentListener(addRatingAndCommentButton, locationListNumber, locationNumber);

        return organizer;
    }


    private JPanel ratingPanel(int locationListNumber, int locationNumber) {
        JPanel ratingPanel = new JPanel();
        ratingPanel.setBackground(LIGHT_BLUE);
        ratingPanel.setLayout(new BoxLayout(ratingPanel, BoxLayout.Y_AXIS));

        String[] ratingNumbers = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        ratingBox = new JComboBox(ratingNumbers);

        JButton setRatingButton = new JButton("Set Rating");
        ratingButtonListener(setRatingButton, locationListNumber, locationNumber);

        ratingPanel.add(ratingBox, BorderLayout.NORTH);
        ratingPanel.add(setRatingButton, BorderLayout.SOUTH);

        return ratingPanel;
    }

    private void ratingAndCommentListener(JButton addRatingAndCommentButton, int locationListNumber,
                                          int locationNumber) {
        addRatingAndCommentButton.addActionListener(l -> setRatingAndComment(locationListNumber, locationNumber));
    }

    private void setRatingAndComment(int locationListNumber, int locationNumber) {
        JPanel organizer = new JPanel();
        organizer.setBackground(LIGHT_BLUE);
        Integer ratingValue = Integer.valueOf(ratingBox.getSelectedItem().toString());
        allLists.get(locationListNumber).getLocationList().get(locationNumber).setRating(ratingValue);
        allLists.get(locationListNumber).getLocationList().get(locationNumber).getComments()
                .add(commentsTextField.getText());
        String message = "Comment and rating have been added";
        JLabel output = new JLabel(message);
        organizer.add(output);
        refreshGui(organizer);
        buttonListeners();
    }

    private void ratingButtonListener(JButton setRatingButton, int locationListNumber, int locationNumber) {
        setRatingButton.addActionListener(l -> setRating(locationListNumber, locationNumber));
    }

    private void setRating(int locationListNumber, int locationNumber) {
        JPanel organizer = new JPanel();
        Integer ratingValue = Integer.valueOf(ratingBox.getSelectedItem().toString());
        allLists.get(locationListNumber).getLocationList().get(locationNumber).setRating(ratingValue);

        String message = "Rating has been set";
        JLabel output = new JLabel(message);
        organizer.add(output);
        refreshGui(organizer);
        buttonListeners();
    }

    //MODIFIES: this
    //EFFECTS: Jpanel displaying the textfield to input the comment for the chosen location
    public JPanel commentsPanel(int locationListNumber, int locationNumber) {
        JPanel commentsPanel = new JPanel();
        commentsPanel.setBackground(LIGHT_BLUE);
        commentsPanel.setLayout(new BoxLayout(commentsPanel, BoxLayout.Y_AXIS));

        commentsTextField = new JTextField();
        JLabel commentLabel = new JLabel("Enter you're comment below");

        JButton setCommentsButton = new JButton("Add Comment");
        commentsButtonListener(setCommentsButton, locationListNumber, locationNumber);


        commentsPanel.add(commentLabel, BorderLayout.NORTH);
        commentsPanel.add(commentsTextField, BorderLayout.SOUTH);
        commentsPanel.add(setCommentsButton);


        return commentsPanel;
    }

    private void commentsButtonListener(JButton setCommentsButton, int locationListNumber, int locationNumber) {
        setCommentsButton.addActionListener(l -> setComments(locationListNumber, locationNumber));
    }

    private void setComments(int locationListNumber, int locationNumber) {
        JPanel organizer = new JPanel();
        allLists.get(locationListNumber).getLocationList().get(locationNumber).getComments()
                .add(commentsTextField.getText());
        String message = "Comment has been added";
        JLabel output = new JLabel(message);
        organizer.add(output);
        refreshGui(organizer);
        buttonListeners();
    }

    //MODIFIES: this
    //EFFECTS: JList that displays the location lists for the user to select from to add a comment to
    public JList<String> chooseLocationList() {
        DefaultListModel<String> model = new DefaultListModel<>();
        listAddPanel = new JList<>(model);
        listAddPanel.setPreferredSize(new Dimension(300, 600));

        for (int i = 0; i < allLists.size(); i++) {
            model.addElement(allLists.get(i).getLocationListName());
        }

        listAddPanel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listAddPanel.setLayoutOrientation(JList.VERTICAL);

        return listAddPanel;

    }

    //MODIFIES: this
    //EFFECTS: a method for organizing the layout of the added panels and labels for the panel when clicking the
    // "New Location" button
    public void addALocationPanel() {
        JPanel organizer = new JPanel();
        organizer.setBorder(BorderFactory.createTitledBorder("Add Location: "));

        JLabel intro = new JLabel("Please select the list you want to add a location to");

        JPanel userInput = new JPanel();
//        userInput.setLayout(new FlowLayout(FlowLayout.LEFT));
        userInput.setLayout(new BoxLayout(userInput, BoxLayout.Y_AXIS));
        userInput.add(namePanel());
        userInput.add(addressPanel());
        userInput.add(postalCodePanel());
        userInput.add(cityPanel());
        userInput.add(provincePanel());

        organizer.add(userInput, BorderLayout.WEST);

        organizer.add(chooseLocationList(), BorderLayout.EAST);
        //organizer.add(chooseListPanel(), BorderLayout.EAST);

        JButton setLocationInfoButton = new JButton("Set Location");
        userInput.add(setLocationInfoButton);

        setLocationInfoButton.setBackground(LIGHT_BLUE);
        locationInfoButtonListener(setLocationInfoButton);

        refreshGui(organizer);
        buttonListeners();


    }



    //MODIFIES: this
    //EFFECTS: JPanel including the textfield for the user to input the locations name
    private JPanel namePanel() {
        JPanel name = new JPanel();
        JLabel locationNameLabel = new JLabel("Input the location's name here");
        nameTextField = new JTextField(20);
        name.add(locationNameLabel);
        name.add(nameTextField);

        return name;

    }

    //MODIFIES: this
    //EFFECTS: JPanel including the textfield for the user to input the locations address
    private JPanel addressPanel() {
        JPanel address = new JPanel();
        JLabel addressLabel = new JLabel("Input the location's address here");
        addressTextField = new JTextField(20);
        address.add(addressLabel);
        address.add(addressTextField);

        return address;

    }

    //MODIFIES: this
    //EFFECTS: JPanel including the textfield for the user to input the locations postalcode
    private JPanel postalCodePanel() {
        JPanel postalCode = new JPanel();
        JLabel postalCodeLabel = new JLabel("Input the location's postal code here");
        postalCodeTextField = new JTextField(20);
        postalCode.add(postalCodeLabel);
        postalCode.add(postalCodeTextField);

        return postalCode;
    }

    //MODIFIES: this
    //EFFECTS: JPanel including the textfield for the user to input the locations city
    private JPanel cityPanel() {
        JPanel city = new JPanel();
        JLabel cityLabel = new JLabel("Input the location's city here");
        cityTextField = new JTextField(20);
        city.add(cityLabel);
        city.add(cityTextField);

        return city;

    }

    //MODIFIES: this
    //EFFECTS: JPanel including the textfield for the user to input the locations province
    private JPanel provincePanel() {
        JPanel province = new JPanel();
        JLabel provinceLabel = new JLabel("Input the location's province here");
        provinceTextField = new JTextField(20);
        province.add(provinceLabel);
        province.add(provinceTextField);

        return province;

    }

    //MODIFIES: setLocationInfoButton
    //EFFECTS: sets the actionlistener for the created setLocationInfoButton
    private void locationInfoButtonListener(JButton setLocationInfoButton) {
        setLocationInfoButton.addActionListener(l -> setLocationInfo());
    }

    //MODIFIES:  allLists.get(listAddPanel.getSelectedIndex()).getList()
    //EFFECTS: organizer for the panel that shows when the setLocationInfoButton is clicked
    //creates a new location and adds it to the selected location list
    private void setLocationInfo() {
        JPanel organizer = new JPanel();
        organizer.setBorder(BorderFactory.createTitledBorder("Locations"));
        String name = nameTextField.getText();
        String address = addressTextField.getText();
        String postalCode = postalCodeTextField.getText();
        String city = cityTextField.getText();
        String province = provinceTextField.getText();

        Location newLocation = new Location(name, address, postalCode, city, province);
        allLists.get(listAddPanel.getSelectedIndex()).addLocation(newLocation);

        String message = "Added " + name + " to " + allLists.get(listAddPanel.getSelectedIndex()).getLocationListName();
        JLabel msg = new JLabel(message);
        JLabel image = new JLabel();
        image.setIcon(oreo);

        //organizer.setLayout(new BoxLayout(organizer, BoxLayout.Y_AXIS));
        organizer.add(msg, BorderLayout.NORTH);
        organizer.add(image, BorderLayout.SOUTH);

        refreshGui(organizer);
        buttonListeners();

    }


    public void editLocationPanel(int locationList, int locationNumber) {
        JPanel organizer = new JPanel();
        organizer.setBorder(BorderFactory.createTitledBorder("Edit a Location: "));

        organizer.setLayout(new BoxLayout(organizer, BoxLayout.X_AXIS));
        JLabel intro = new JLabel("Please select the location list, then location you want to edit");

        JPanel info = new JPanel();
        info.add(makeLocationListsForEdit(), BorderLayout.WEST);
        info.add(makeListOfLocationsForEdit(locationList), BorderLayout.EAST);

        JPanel editing = new JPanel();
        editing.add(makeLocationInfoForEdit(locationList, locationNumber), BorderLayout.WEST);
        editing.add(changeLocationInfoPanel(locationList, locationNumber), BorderLayout.EAST);

//        organizer.add(intro, BorderLayout.NORTH);
        organizer.add(info);
        organizer.add(editing);

        refreshGui(organizer);
        buttonListeners();
    }




    public JList<String> makeLocationListsForEdit() {
        DefaultListModel<String> model = new DefaultListModel<>();
        JList<String> list = new JList<>(model);
        list.setPreferredSize(new Dimension(150, 600));


        for (int i = 0; i < allLists.size(); i++) {
            model.addElement(allLists.get(i).getLocationListName());
        }

        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        MouseListener mouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedItem = list.getSelectedIndex();
                    System.out.println("click");
                    editLocationPanel(selectedItem, -1);

                }
            }
        };
        list.addMouseListener(mouseListener);

        return list;
    }

    public JList<String> makeListOfLocationsForEdit(int locationListNumber) {
        DefaultListModel<String> model = new DefaultListModel<>();
        JList<String> list = new JList<>(model);
        list.setPreferredSize(new Dimension(150, 600));

        if (locationListNumber == -1) {
            System.out.println("blank");
        } else {
            for (int i = 0; i < allLists.get(locationListNumber).getLocationList().size(); i++) {
                model.addElement(allLists.get(locationListNumber).getLocationList().get(i).getName());
            }
        }

        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        MouseListener mouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedItem = list.getSelectedIndex();
                    System.out.println("click");

                    editLocationPanel(locationListNumber, selectedItem);

                }
            }
        };
        list.addMouseListener(mouseListener);

        return list;
    }

    public JPanel makeLocationInfoForEdit(int locationListNumber, int locationNumber) {
        JPanel organizer = new JPanel();
        JPanel locationInfo = new JPanel();

        locationInfo.setBackground(Color.WHITE);
        organizer.setPreferredSize(new Dimension(200, 300));
        organizer.setBackground(Color.WHITE);
        organizer.setLayout(new BoxLayout(organizer, BoxLayout.Y_AXIS));
        JLabel commentsIntro = new JLabel("Location comments: ");

        if (locationListNumber == -1 || locationNumber == -1) {
            System.out.println("blank");
        } else {

            //adding the different location information labels into one panel
            locationInfo.setLayout(new GridLayout(0, 1));
            locationInfo.add(name(locationListNumber, locationNumber));
            locationInfo.add(address(locationListNumber, locationNumber));
            locationInfo.add(postalCode(locationListNumber, locationNumber));
            locationInfo.add(city(locationListNumber, locationNumber));
            locationInfo.add(province(locationListNumber, locationNumber));
            locationInfo.add(rating(locationListNumber, locationNumber));
            locationInfo.add(commentsIntro);
            locationInfo.add(comments(locationListNumber, locationNumber));

            organizer.add(locationInfo);

        }

        return organizer;
    }


//
    public JPanel changeLocationInfoPanel(int locationListNumber, int locationNumber) {

        JPanel organizer = new JPanel();

        JPanel userInput = new JPanel();
        userInput.setLayout(new BoxLayout(userInput, BoxLayout.Y_AXIS));
        userInput.add(namePanel());
        userInput.add(addressPanel());
        userInput.add(postalCodePanel());
        userInput.add(cityPanel());
        userInput.add(provincePanel());

        JButton editLocationInfoButton = new JButton("Set Changes");
        editLocationInfoButton.setBackground(LIGHT_BLUE);

        editLocationInfoButtonListener(editLocationInfoButton, locationListNumber, locationNumber);

        organizer.setLayout(new BoxLayout(organizer, BoxLayout.Y_AXIS));

        organizer.add(userInput);
        organizer.add(editLocationInfoButton);
        return organizer;

    }

    private void editLocationInfoButtonListener(JButton editLocationInfoButton, int locationListNumber, int locationNumber) {
        editLocationInfoButton.addActionListener(l -> changeLocationInfo(locationListNumber, locationNumber));
    }

    private void changeLocationInfo(int locationListNumber, int locationNumber) {
        JPanel organizer = new JPanel();
        String name;
        String address;
        String postalCode;
        String city;
        String province;

        if (!nameTextField.getText().equals("")) {
            name = nameTextField.getText();
        } else {
            name =  allLists.get(locationListNumber).getLocationList().get(locationNumber).getName();
        }
        if (!addressTextField.getText().equals("")) {
            address = addressTextField.getText();
        } else {
            address =  allLists.get(locationListNumber).getLocationList().get(locationNumber).getStreetName();
        }

        if (!postalCodeTextField.getText().equals("")) {
            postalCode = postalCodeTextField.getText();
        } else {
            postalCode =  allLists.get(locationListNumber).getLocationList().get(locationNumber).getPostalCode();
        }

        if (!cityTextField.getText().equals("")) {
            city = cityTextField.getText();
        } else {
            city =  allLists.get(locationListNumber).getLocationList().get(locationNumber).getCity();
        }

        if (!provinceTextField.getText().equals("")) {
            province = provinceTextField.getText();
        } else {
            province =  allLists.get(locationListNumber).getLocationList().get(locationNumber).getProvince();
        }


        allLists.get(locationListNumber).getLocationList().get(locationNumber).setName(name);
        allLists.get(locationListNumber).getLocationList().get(locationNumber).setStreetName(address);
        allLists.get(locationListNumber).getLocationList().get(locationNumber).setPostalCode(postalCode);
        allLists.get(locationListNumber).getLocationList().get(locationNumber).setCity(city);
        allLists.get(locationListNumber).getLocationList().get(locationNumber).setProvince(province);


        String message = "location has been changed";
        JLabel msg = new JLabel(message);

        organizer.add(msg, BorderLayout.CENTER);

        refreshGui(organizer);
        buttonListeners();
    }

//
//    private void changeLocationInfoButton() {
//        JPanel organizer = new JPanel();
//        String name = nameTextField.getText();
//        String address = addressTextField.getText();
//        String postalCode = postalCodeTextField.getText();
//        String city = cityTextField.getText();
//        String province = provinceTextField.getText();
//
//        Location newLocation = new Location(name, address, postalCode, city, province);
//        allLists.get(listAddPanel.getSelectedIndex()).addLocation(newLocation);
//
//
//        refreshGui(organizer);
//        buttonListeners();
//
//    }












    public ArrayList<LocationList> getListOfLists() {

        return allLists;
    }

    public void setListOfLists(ArrayList<LocationList> listOfLists) {

        this.allLists = listOfLists;
    }


    //EFFECTS: loads previously saved file
    public void loadFile() {
        JsonReader jsonReader = new JsonReader(JSON_STORE);
        try {
            allLists = jsonReader.read();
            String confirmation = "File has been restored from " + JSON_STORE;
            JPanel organizer = new JPanel();
            organizer.add(new JLabel(confirmation));
            refreshGui(organizer);
            buttonListeners();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//

    //MODIFIES: this
    //EFFECTS: saves allLists to the file
    public void saveFile() {
        JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
        try {
            jsonWriter.open();
            jsonWriter.write(allLists);
            jsonWriter.close();
            String confirmation = "File has been successfully saved to " + JSON_STORE;
            JPanel organizer = new JPanel();
            organizer.setBorder(BorderFactory.createTitledBorder("Save Status"));
            organizer.add(new JLabel(confirmation));
            refreshGui(organizer);
            buttonListeners();

            System.out.println(confirmation);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //MODIFIES: this
    //EFFECTS: refreshes the GUI
    public void refreshGui(JPanel organizer) {
        getContentPane().removeAll();
        super.add(menuButtons(), BorderLayout.SOUTH);
        super.add(newMainMenu(), BorderLayout.NORTH);
        super.add(organizer, BorderLayout.CENTER);
        invalidate();
        validate();
    }

}



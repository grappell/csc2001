import javax.swing.*;
import java.awt.*;
import java.util.Comparator;

public class MainGUI extends JFrame {

    private JTextField idField;
    private JTextField titleField;
    private JTextField mentorField;
    private JTextField departmentField;
    private JTextField dateField;
    private JTextField timeField;
    private JTextField locationField;
    private JTextField maxField;
    private JTextArea outputArea;

    private LinkedList<Session> sessions = new LinkedList<>();

    public record FieldOutput(int sessionId, String title, String mentor, String department, String date, String time, String location, int maxParticipants) {}

    public MainGUI() {
        setTitle("Employee Mentorship and Inclusion Manager");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        createGUI();
        setVisible(true);
    }

    // DO NOT CHANGE THIS METHOD!
    // This method creates your GUI of the layout
    private void createGUI() {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(8,2,5,5));
        idField = new JTextField();
        titleField = new JTextField();
        mentorField = new JTextField();
        departmentField = new JTextField();
        dateField = new JTextField();
        timeField = new JTextField();
        locationField = new JTextField();
        maxField = new JTextField();
        inputPanel.add(new JLabel("Session ID"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Title"));
        inputPanel.add(titleField);
        inputPanel.add(new JLabel("Mentor"));
        inputPanel.add(mentorField);
        inputPanel.add(new JLabel("Department"));
        inputPanel.add(departmentField);
        inputPanel.add(new JLabel("Date"));
        inputPanel.add(dateField);
        inputPanel.add(new JLabel("Time"));
        inputPanel.add(timeField);
        inputPanel.add(new JLabel("Location"));
        inputPanel.add(locationField);
        inputPanel.add(new JLabel("Max Participants"));
        inputPanel.add(maxField);
        add(inputPanel, BorderLayout.NORTH);
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(outputArea);
        add(scroll, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Session");
        JButton displayButton = new JButton("Display");
        JButton searchButton = new JButton("Search");
        JButton removeButton = new JButton("Remove");
        JButton registerButton = new JButton("Register");
        JButton exitButton = new JButton("Exit");
        // add Buttons
        buttonPanel.add(addButton);
        buttonPanel.add(displayButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(registerButton);
        buttonPanel.add(exitButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Button Actions:
        // Clicking each button in the layout will invoke its corresponding functionality.
        addButton.addActionListener(e -> addSession());
        displayButton.addActionListener(e -> displaySessions());
        searchButton.addActionListener(e -> searchSession());
        removeButton.addActionListener(e -> removeSession());
        registerButton.addActionListener(e -> registerParticipant());
        exitButton.addActionListener(e -> System.exit(0));
    }

    // DO NOT CHANGE THIS METHOD!
    // It clears all fields in the layout
    private void clearFields() {
        idField.setText("");
        titleField.setText("");
        mentorField.setText("");
        departmentField.setText("");
        dateField.setText("");
        timeField.setText("");
        locationField.setText("");
        maxField.setText("");
        // Put the cursor back in the first field
        idField.requestFocus();
    }

    // Add a Session in the correct location first, last, or after based on sessionID
    private void addSession() {
        try {

            var session = new Session(getFields(true), "N/A");
            if(session.getSessionID() == 0 || sessions.getLength() == 0 || sessions.getFirst().getData().getSessionID() > session.getSessionID()) {
                sessions.addFirst(session);
                if(sessions.getLength() != 0) reorderSessions(session);
                outputArea.setText("Session Added Successfully\n");
                clearFields();
                return;
            }

            sessions.insert(session, Comparator.comparingInt(Session::getSessionID));

            reorderSessions(session);

            outputArea.setText("Session Added Successfully\n");
            clearFields();
        }
        catch(Exception e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
            outputArea.setText("Invalid input");
        }
    }
    
    // Display the information in the outputArea in GUI
    private void displaySessions() {

        if(sessions.getLength() == 0) {
            outputArea.setText("No active sessions");
            return;
        }

        StringBuilder bldr = new StringBuilder();
        sessions.stream().forEach(e -> {
            bldr.append(e);
            bldr.append("\n");
        });

        sessions.stream().forEach(System.out::println);

        outputArea.setText(bldr.toString());

    }

    // Search based on sessionID or mentor if the fields are not empty
    private void searchSession() {
        /* TODO: 1) Search by sessionID, if field is empty,
         print Session not found, in outputArea.
        2) Search by mentor if the Mentor field is empty,
         print No session found for mentor (mentor name) in outputArea
        3) If both sessionID and mentor are empty,
        print Please enter a Session ID or Mentor name in outputArea
        */
        
    }
    
    // delete the session
    private void removeSession() {

        var fields = getFields(false);
        var session = sessions.getIndexBySpecific(s -> s.getSessionID() == fields.sessionId);

        if(session == -1) {
            outputArea.setText("Unable to find a session with that ID");
            return;
        }

        var sessionS = sessions.at(session).getData();
        var removed = sessions.remove(sessionS);

        outputArea.setText("Removed session with ID: " + removed.getSessionID());
        clearFields();
        
    }

    // registerParticipants call the method in the LinkedList
    private void registerParticipant() {
        /* TODO: It must call the registerParticipant() method of the LinkedList,
        if result is True: print in outputArea, "Participant registered"
        otherwise print, "Registration failed"
        */
    }

    private FieldOutput getFields(boolean enforceNotNull) {
        return new FieldOutput(
            textParseInt(idField.getText(), enforceNotNull),
            titleField.getText(),
            mentorField.getText(),
            departmentField.getText(),
            dateField.getText(),
            timeField.getText(),
            locationField.getText(),
            textParseInt(maxField.getText(), enforceNotNull)
        );
    }

    private int textParseInt(String t, boolean enforceNotNull) {
        return !enforceNotNull && t.isEmpty() ? Integer.MIN_VALUE : Integer.parseInt(t);
    }

    private void reorderSessions(Session updated) {

        var idx = sessions.getIndex(updated);
        if(idx == -1) throw new IllegalArgumentException("Updated session is not in the list");

        var curr = sessions.at(idx);
        if (curr == null) throw new IllegalStateException("Index existed but no node was found");

        var occupied = curr.getData().getSessionID();

        while(curr.hasNext()) {
            var next = curr.getNext();

            if(next.getData().getSessionID() != occupied) break;

            occupied++;
            next.getData().setSessionID(occupied);

            curr = next;
        }

    }

    public static void main(String[] args) {
        new MainGUI();
    }
}

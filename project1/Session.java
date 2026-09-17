public class Session{

    private  int sessionID;
    private  final String topic, mentor, department;
    private  String date, time, location;
    private  final int maxParticipants;
    private  int currentParticipants = 0;

    public Session(int sessionId, String topic, String mentor, String department, String date, String time, String location, int maxParticipants){
        this.sessionID = sessionId;
        this.topic = topic;
        this.mentor = mentor;
        this.department = department;
        this.date = date;
        this.time = time;
        this.location = location;
        this.maxParticipants = maxParticipants;
    }

    public Session(int id) {
        this(id, "", "", "", "", "", "", 0);
    }

    public Session(MainGUI.FieldOutput out, String topic) {
        sessionID = out.sessionId();
        this.topic = topic;
        mentor = out.mentor();
        department = out.department();
        date = out.date();
        time = out.time();
        location = out.location();
        maxParticipants = out.maxParticipants();
    }

    // getters
    public int getSessionID(){ return this.sessionID; }
    public String getTopic() { return this.topic; }
    public String getDate() { return this.date; }
    public String getMentor() { return this.mentor; }
    public String getDepartment() { return this.department; }
    public String getTime() { return this.time; }
    public String getLocation() { return this.location; }
    public int getCurrentParticipants() { return currentParticipants; }
    public int getMaxParticipants() { return maxParticipants; }

    // setters
    public void setDate(String date) { this.date = date; }
    public void setTime(String time) { this.time = time; }
    public void setLocation(String location) { this.location = location; }
    public void setCurrentParticipants(int currentParticipants) {
        this.currentParticipants = currentParticipants;
    }
    public void setSessionID(int newId) { this.sessionID = newId; }

    @Override
    public String toString() {
        int par = this.maxParticipants-this.currentParticipants;
        return "Session{id: " + this.sessionID  +
                ", Topic: " + this.topic  +
                ", Mentor: " + this.mentor +
                ", Department: " + this.department +
                ", Date: " + this.date +
                ", Time: " + this.time +
                ", Location: " + this.location +
                ", Available seats: " + par +
                '}';
    }
}

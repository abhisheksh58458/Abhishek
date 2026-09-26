package resqbridge;

import java.util.*;

/** A relief camp shown on the map. x/y are fractions (0..1) of the map panel. */
class Camp {
    String name;
    int supplyPercent;
    double xFrac, yFrac;
    boolean hub;
    boolean operational;

    Camp(String name, int supplyPercent, double xFrac, double yFrac, boolean hub, boolean operational) {
        this.name = name;
        this.supplyPercent = supplyPercent;
        this.xFrac = xFrac;
        this.yFrac = yFrac;
        this.hub = hub;
        this.operational = operational;
    }
}

/** One dispatch log entry. */
class Dispatch {
    String item;
    String from;
    String to;
    String timeAgo;
    int units;

    Dispatch(String item, String from, String to, String timeAgo, int units) {
        this.item = item;
        this.from = from;
        this.to = to;
        this.timeAgo = timeAgo;
        this.units = units;
    }
}

/** A donor with a cumulative contribution amount, in rupees. */
class Donor {
    String name;
    long amount;

    Donor(String name, long amount) {
        this.name = name;
        this.amount = amount;
    }
}

/** A driver available for dispatch runs. */
class Driver {
    String name;
    String vehicle;
    boolean onDuty;
    String destination = "";
    boolean reached = false;
    int fuelPercent = 80;
    String maintenanceIssue = "";
    java.time.LocalDateTime shiftStart = null;

    Driver(String name, String vehicle, boolean onDuty) {
        this.name = name;
        this.vehicle = vehicle;
        this.onDuty = onDuty;
    }

    Driver(String name, String vehicle, boolean onDuty, String destination, boolean reached) {
        this.name = name;
        this.vehicle = vehicle;
        this.onDuty = onDuty;
        this.destination = destination;
        this.reached = reached;
    }

    double hoursOnShift() {
        if (shiftStart == null) return 0;
        return java.time.Duration.between(shiftStart, java.time.LocalDateTime.now()).toMinutes() / 60.0;
    }
}

/** A critical/operational alert. */
class AlertItem {
    String title;
    String detail;
    String severity; // "Critical" or "Warning"

    AlertItem(String title, String detail, String severity) {
        this.title = title;
        this.detail = detail;
        this.severity = severity;
    }
}

/** The signed-in administrator's profile, editable from the Settings page. */
class AdminProfile {
    String name;
    String email;
    String phone;
    String role;

    AdminProfile(String name, String email, String phone, String role) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }
}

/** A newly registered admin/responder account awaiting staff verification. */
class PendingAccount {
    String name;
    String email;
    String requestedRole;
    String dateRequested;

    PendingAccount(String name, String email, String requestedRole, String dateRequested) {
        this.name = name;
        this.email = email;
        this.requestedRole = requestedRole;
        this.dateRequested = dateRequested;
    }
}

/** One row of the activity audit log. */
class AuditLogEntry {
    String time;
    String description;

    AuditLogEntry(String time, String description) {
        this.time = time;
        this.description = description;
    }
}

/** Broadcast channel toggles and Common Alerting Protocol (CAP) template defaults. */
class CommunicationSettings {
    boolean pushEnabled = true;
    boolean smsEnabled = true;
    boolean emailEnabled = true;
    boolean voiceEnabled = false;
    String capSenderId = "IN-KL-RESQBRIDGE";
    String capCategory = "Safety";
    String capUrgency = "Immediate";
    String capSeverity = "Severe";
    String capCertainty = "Likely";
    int geoFenceRadiusKm = 15;
}

/** API connections and offline-sync behavior for field operations. */
class IntegrationSettings {
    String weatherApiKey = "";
    String mappingApiKey = "";
    String seismicApiKey = "";
    boolean weatherConnected = true;
    boolean mappingConnected = true;
    boolean seismicConnected = false;
    int bandwidthLimitMbps = 5;
    int syncIntervalMinutes = 15;
    boolean offlineQueueEnabled = true;
}

/** Security posture and system-wide preferences. */
class SecuritySettings {
    boolean mfaRequired = true;
    String mfaMethod = "Authenticator App";
    int dataRetentionDays = 180;
    String dataResidency = "India (Mumbai region)";
    boolean maintenanceMode = false;
    String platformNotice = "";
}

/** A login account. role is one of "Admin", "Driver", "Camp Employee". */
class LoginAccount {
    String username;
    String password;
    String displayName;
    String role;
    String linkedDriverName; // for role == "Driver"
    String linkedCampName;   // for role == "Camp Employee"

    LoginAccount(String username, String password, String displayName, String role,
                 String linkedDriverName, String linkedCampName) {
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.role = role;
        this.linkedDriverName = linkedDriverName;
        this.linkedCampName = linkedCampName;
    }
}

/** A chat message on a channel (e.g. "driver:Anil Kumar" or "camp:Thrissur Camp"). */
class ChatMessage {
    String channel;
    String fromName;
    String fromRole;
    String text;
    String time;

    ChatMessage(String channel, String fromName, String fromRole, String text, String time) {
        this.channel = channel;
        this.fromName = fromName;
        this.fromRole = fromRole;
        this.text = text;
        this.time = time;
    }
}

/** A hazard reported by a driver along a route. */
class HazardReport {
    String reporter;
    String location;
    String type;     // Flooding, Landslide, Blocked Road, Other
    String severity; // High, Medium, Low
    String time;

    HazardReport(String reporter, String location, String type, String severity, String time) {
        this.reporter = reporter;
        this.location = location;
        this.type = type;
        this.severity = severity;
        this.time = time;
    }
}

/** A known rest stop / fuel station / secure parking area. */
class SafeZone {
    String name;
    String type;
    double distanceKm;

    SafeZone(String name, String type, double distanceKm) {
        this.name = name;
        this.type = type;
        this.distanceKm = distanceKm;
    }
}

/** Shared equipment (forklifts, hand trucks, pallets, ...) at camps. */
class EquipmentItem {
    String name;
    int total;
    int inUse;

    EquipmentItem(String name, int total, int inUse) {
        this.name = name;
        this.total = total;
        this.inUse = inUse;
    }
}

/** One line of a delivery manifest. */
class ManifestItem {
    String name;
    int qty;
    String priority; // "Critical", "Perishable", "Standard"

    ManifestItem(String name, int qty, String priority) {
        this.name = name;
        this.qty = qty;
        this.priority = priority;
    }
}

/** A single truck run from an origin to a destination camp, tracked end-to-end. */
class DeliveryRun {
    String driverName;
    String fromLocation;
    String toCamp;
    List<ManifestItem> items;
    String status; // Loading, En Route, Arrived at Gate, Unloading, Completed
    int etaMinutes;
    String checkInTime = "";
    String checkOutTime = "";
    boolean proofSigned = false;
    String proofNote = "";
    List<String> discrepancies = new ArrayList<>();
    String storageAllocation = "";
    String offloadTeam = "";

    DeliveryRun(String driverName, String fromLocation, String toCamp, List<ManifestItem> items,
                String status, int etaMinutes) {
        this.driverName = driverName;
        this.fromLocation = fromLocation;
        this.toCamp = toCamp;
        this.items = items;
        this.status = status;
        this.etaMinutes = etaMinutes;
    }

    boolean hasCriticalOrPerishable() {
        for (ManifestItem m : items) {
            if ("Critical".equals(m.priority) || "Perishable".equals(m.priority)) return true;
        }
        return false;
    }
}

interface DataListener {
    void onDataChanged();
}

/**
 * Single in-memory source of truth for the whole app. Every panel registers
 * as a listener and repaints/rebuilds itself whenever the data changes, so
 * edits made in one place (e.g. the Camps page) are reflected everywhere
 * (e.g. the Dashboard map and stat cards).
 */
class DataStore {
    final List<Camp> camps = new ArrayList<>();
    final List<Dispatch> dispatches = new ArrayList<>();
    final List<Donor> donors = new ArrayList<>();
    final List<Driver> drivers = new ArrayList<>();
    final List<AlertItem> alerts = new ArrayList<>();
    final LinkedHashMap<String, Integer> inventory = new LinkedHashMap<>();

    int totalSuppliesDispatched = 1245;
    int totalCampsInNetwork = 14;

    // --- Admin / settings state ---
    AdminProfile adminProfile = new AdminProfile("Arjun Menon", "arjun.menon@resqbridge.org", "+91 98470 12345", "Super Admin");
    final List<String> roleNames = Arrays.asList("Admin", "Field Rescue Team", "Volunteer", "Citizen");
    final List<String> permissionNames = Arrays.asList(
            "View Dashboard", "Edit Relief Camps", "Manage Donors & Inventory",
            "Broadcast Alerts", "Manage Users & Roles");
    final LinkedHashMap<String, Set<String>> rolePermissions = new LinkedHashMap<>();
    final List<PendingAccount> pendingAccounts = new ArrayList<>();
    final List<AuditLogEntry> auditLog = new ArrayList<>();
    final CommunicationSettings commsSettings = new CommunicationSettings();
    final IntegrationSettings integrationSettings = new IntegrationSettings();
    final List<String> resourceCategories = new ArrayList<>();
    final SecuritySettings securitySettings = new SecuritySettings();

    // --- Role accounts, driver ops, and camp ops state ---
    final List<LoginAccount> accounts = new ArrayList<>();
    final List<ChatMessage> chatMessages = new ArrayList<>();
    final List<HazardReport> hazardReports = new ArrayList<>();
    final List<SafeZone> safeZones = new ArrayList<>();
    final List<EquipmentItem> equipment = new ArrayList<>();
    final List<DeliveryRun> deliveryRuns = new ArrayList<>();
    int gateMaxCapacity = 3;
    boolean holdIncomingTrucks = false;

    private final List<DataListener> listeners = new ArrayList<>();

    DataStore() {
        camps.add(new Camp("Kasaragod Camp", 75, 0.28, 0.10, false, true));
        camps.add(new Camp("Kannur Camp", 62, 0.36, 0.22, false, true));
        camps.add(new Camp("Kozhikode Camp", 80, 0.44, 0.34, false, true));
        camps.add(new Camp("Thrissur Camp", 50, 0.50, 0.46, false, true));
        camps.add(new Camp("Kochi Hub", 55, 0.46, 0.58, true, true));
        camps.add(new Camp("Kollam Camp", 40, 0.40, 0.74, false, true));
        camps.add(new Camp("Thiruvananthapuram Camp", 70, 0.48, 0.88, false, true));

        dispatches.add(new Dispatch("Medical Supplies", "Kochi Hub", "Thrissur Camp", "2 mins ago", 40));
        dispatches.add(new Dispatch("Food Supplies", "Kozhikode Camp", "Kannur Camp", "15 mins ago", 120));
        dispatches.add(new Dispatch("Water Bottles", "Kollam Camp", "Kochi Hub", "32 mins ago", 300));
        dispatches.add(new Dispatch("Tents & Blankets", "Trivandrum Camp", "Kollam Camp", "45 mins ago", 60));

        donors.add(new Donor("Kerala Foundation", 2500000));
        donors.add(new Donor("Helping Hands NGO", 1575000));
        donors.add(new Donor("People of Kerala", 1230000));
        donors.add(new Donor("United Volunteers", 845000));

        drivers.add(new Driver("Anil Kumar", "Truck - KL07 A 1234", true, "Thrissur Camp", false));
        drivers.add(new Driver("Rajesh Menon", "Van - KL01 C 5521", true, "Kannur Camp", true));
        drivers.add(new Driver("Sunitha Nair", "Truck - KL09 B 7788", true, "Kochi Hub", false));
        drivers.add(new Driver("Vishnu Prasad", "Van - KL04 D 3312", false, "", false));
        drivers.add(new Driver("Deepa Thomas", "Truck - KL11 A 9090", true, "Kollam Camp", true));

        alerts.add(new AlertItem("Low water supply", "Kollam Camp water stock below 40%", "Critical"));
        alerts.add(new AlertItem("Route delay", "Kozhikode - Kannur route congested", "Warning"));

        inventory.put("Medical", 68);
        inventory.put("Food", 82);
        inventory.put("Water", 74);
        inventory.put("Shelter", 87);

        rolePermissions.put("Admin", new LinkedHashSet<>(permissionNames));
        rolePermissions.put("Field Rescue Team", new LinkedHashSet<>(Arrays.asList(
                "View Dashboard", "Edit Relief Camps")));
        rolePermissions.put("Volunteer", new LinkedHashSet<>(Arrays.asList(
                "View Dashboard", "Manage Donors & Inventory")));
        rolePermissions.put("Citizen", new LinkedHashSet<>(Arrays.asList("View Dashboard")));

        pendingAccounts.add(new PendingAccount("Meera Pillai", "meera.pillai@example.com", "Field Rescue Team", "Today"));
        pendingAccounts.add(new PendingAccount("Joseph Varkey", "joseph.v@example.com", "Volunteer", "Yesterday"));

        auditLog.add(new AuditLogEntry(nowTime(), "System started and default data loaded."));

        resourceCategories.add("Emergency Vehicles");
        resourceCategories.add("Medical Supplies");
        resourceCategories.add("Shelter Capacity");
        resourceCategories.add("Food & Water");

        accounts.add(new LoginAccount("admin", "admin123", "Arjun Menon", "Admin", null, null));
        accounts.add(new LoginAccount("driver1", "driver123", "Anil Kumar", "Driver", "Anil Kumar", null));
        accounts.add(new LoginAccount("camp1", "camp123", "Priya Nair", "Camp Employee", null, "Thrissur Camp"));

        safeZones.add(new SafeZone("Aluva Rest Stop", "Rest Stop \u00B7 Food", 12));
        safeZones.add(new SafeZone("Edappally Secure Parking", "Secure Parking", 20));
        safeZones.add(new SafeZone("Perumbavoor Fuel Station", "Fuel Station", 8));
        safeZones.add(new SafeZone("Angamaly Police Checkpoint", "Safe Zone \u00B7 Checkpoint", 15));

        equipment.add(new EquipmentItem("Forklift", 2, 1));
        equipment.add(new EquipmentItem("Hand Truck", 6, 2));
        equipment.add(new EquipmentItem("Pallets", 40, 15));

        deliveryRuns.add(new DeliveryRun("Anil Kumar", "Kochi Hub", "Thrissur Camp",
                new ArrayList<>(Arrays.asList(
                        new ManifestItem("Medical Kits", 40, "Critical"),
                        new ManifestItem("Rice Bags", 100, "Standard"))),
                "En Route", 25));
        deliveryRuns.add(new DeliveryRun("Rajesh Menon", "Kozhikode Camp", "Kannur Camp",
                new ArrayList<>(Arrays.asList(
                        new ManifestItem("Food Packets", 120, "Standard"))),
                "Loading", 60));
        deliveryRuns.add(new DeliveryRun("Deepa Thomas", "Kollam Camp", "Kochi Hub",
                new ArrayList<>(Arrays.asList(
                        new ManifestItem("Water Cans", 300, "Standard"),
                        new ManifestItem("Baby Formula", 20, "Critical"))),
                "Arrived at Gate", 0));

        chatMessages.add(new ChatMessage("driver:Anil Kumar", "Dispatch", "Admin",
                "Route to Thrissur Camp is clear as of this morning. Drive safe.", nowTime()));
        chatMessages.add(new ChatMessage("camp:Thrissur Camp", "Dispatch", "Admin",
                "Truck from Kochi Hub is loaded with medical kits, priority unload.", nowTime()));

        drivers.get(0).shiftStart = java.time.LocalDateTime.now().minusHours(3);
    }

    static String nowTime() {
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        return String.format("%02d:%02d:%02d", now.getHour(), now.getMinute(), now.getSecond());
    }

    void addAudit(String description) {
        auditLog.add(0, new AuditLogEntry(nowTime(), description));
        fireChanged();
    }

    int activeCamps() {
        int n = 0;
        for (Camp c : camps) if (c.operational) n++;
        return n;
    }

    int activeDrivers() {
        int n = 0;
        for (Driver d : drivers) if (d.onDuty) n++;
        return n;
    }

    int overallStockPercent() {
        if (inventory.isEmpty()) return 0;
        int sum = 0;
        for (int v : inventory.values()) sum += v;
        return Math.round(sum / (float) inventory.size());
    }

    int gateCurrentCount() {
        int n = 0;
        for (DeliveryRun r : deliveryRuns) {
            if ("Arrived at Gate".equals(r.status) || "Unloading".equals(r.status)) n++;
        }
        return n;
    }

    Driver findDriver(String name) {
        if (name == null) return null;
        for (Driver d : drivers) if (d.name.equals(name)) return d;
        return null;
    }

    void addListener(DataListener l) { listeners.add(l); }

    void removeListener(DataListener l) { listeners.remove(l); }

    void fireChanged() {
        for (DataListener l : new ArrayList<>(listeners)) l.onDataChanged();
    }
}

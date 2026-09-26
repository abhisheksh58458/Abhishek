# ResQBridge — Disaster Relief Logistics System (Java Swing)

A desktop recreation of the ResQBridge dashboard, built with plain Java Swing
(no external libraries) so it compiles and runs anywhere a JDK is installed.

## Requirements
- JDK 11 or newer (tested against the language features used; any recent
  JDK — 11, 17, 21 — will work).

## Build

From the `resqbridge` folder (the one containing `src/`):

```bash
mkdir -p out
javac -d out src/resqbridge/*.java
```

## Run

```bash
java -cp out resqbridge.LoginFrame
```

A sign-in window opens first (see the demo accounts table below). After
signing in, a 1440x900 main window opens showing the role-appropriate
console.

## Sign-in and role-based access

The app now opens on a **Sign In** screen (`LoginFrame`). Only three demo
accounts exist, one per role, and each role sees a completely different set
of features after signing in:

| Username | Password  | Role          | Access |
|----------|-----------|---------------|--------|
| `admin`  | `admin123`| Admin         | Everything — Dashboard, Relief Camps, Donors, Supply Inventory, Dispatch, Drivers, Alerts, Messages, Reports, Settings |
| `driver1`| `driver123`| Driver        | **Driver Console** only |
| `camp1`  | `camp123` | Camp Employee | **Camp Console** only |

Logging out (top-right "Logout" button, visible to every role) returns to
the sign-in screen; all data stays in memory for the rest of that run.

## Driver Console (role: Driver)

Everything from the supply-driver feature list, condensed into one scrollable
console:

- **Offline-ready navigation** — the full network map (same map component as
  the admin dashboard), usable without depending on a live signal.
- **Live hazard reporting** — "Report Hazard" logs flooding/landslide/blocked
  road reports with severity; active hazards show a "dynamic rerouting
  suggested" banner visible to the driver.
- **Digital cargo manifest** — assigned delivery runs with item, quantity and
  priority (Critical / Perishable / Standard called out in color); a status
  ladder (Loading → En Route → Arrived at Gate → Unloading → Completed) the
  driver advances themselves; a **Confirm Proof of Delivery** action once
  complete, which posts to the admin's dispatch log automatically (chain of
  custody).
- **SOS emergency button** — one click (with a confirmation) raises a
  Critical alert visible on the Admin Alerts page with the driver's name and
  route.
- **Coordinator chat & check-in pings** — a live two-way chat with dispatch
  (shared with the Admin "Messages" page), plus a one-tap check-in ping.
- **Vehicle & driver welfare** — fuel gauge, a maintenance-issue log, a
  shift start/stop timer with an automatic fatigue warning past 8 hours, and
  a Safe Zone Locator (rest stops, fuel stations, secure parking).

## Camp Console (role: Camp Employee)

Everything from the camp-employee feature list:

- **Live ETA dashboard & pre-arrival manifests** — every truck inbound to
  this camp, with status, ETA (a "simulate 5 min passing" button advances
  the clock), and its full manifest; incoming loads that match the camp's
  current shortage (lowest inventory category) are flagged
  "★ MATCHES CAMP SHORTAGE" (priority matching).
- **Digital check-in / check-out** — Check In at the gate, mark Unloading,
  then Check Out, which timestamps both ends and — the first time either the
  driver or the camp confirms it — posts the delivery to the admin dispatch
  log (so it's never double-counted).
- **Discrepancy reporting** — a quick-tap dialog (damaged goods / missing
  items / unexpected extras / other) logged against that specific run.
- **Storage allocation guide** — a "Suggest" button proposes cold storage,
  priority warehouse, or general storage based on the load's contents, with
  an editable field for camp staff to override.
- **Offload team coordinator** — assign a volunteer/team name to a truck.
- **Equipment allocation** — allocate/release forklifts, hand trucks and
  pallets, with an "available / total" count.
- **Driver welfare management** — if the arriving driver has been on shift
  8+ hours, a warning prompts camp staff to offer food, water and rest.
- **Gate capacity tracker** — shows bays in use vs. capacity, an
  accept/hold-incoming-trucks toggle, and an editable bay capacity; holding
  is enforced against new check-ins.
- **Instant dispute resolution** — the same live chat channel the admin
  "Messages" page uses for that camp, for resolving inventory/paperwork
  issues on the spot.

## What's interactive

- **Sidebar navigation** — switches between Dashboard, Relief Camps, Donors,
  Supply Inventory, Dispatch, Drivers, Alerts, Reports and Settings.
- **Map markers** — click any camp marker (or the Kochi hub) to open an edit
  dialog and change its supply level / operational status. The map, the
  "Active Relief Camps" stat card, and the Relief Camps page all update
  together, since they share one in-memory `DataStore`.
- **Recent Dispatches** — "+ Add" opens a form to log a new dispatch; it
  appears at the top of the list and increases "Total Supplies Dispatched".
- **Supply Inventory** — each category (Medical, Food, Water, Shelter) has a
  spinner; changing it redraws the donut chart and recalculates the overall
  stock percentage live.
- **Top Donors** — "+ Add" opens a form to add a new donor, and each row has
  a delete (✕) button (with a confirmation prompt) to remove unwanted donors.
  The list re-sorts by amount automatically.
- **Drivers page** — "+ Add Driver" opens a form (name, vehicle, optional
  destination, on/off duty). Each driver row shows whether they're
  **En route** or have **Reached** their destination, with a button to
  toggle that, an on/off-duty toggle, and a **Remove** button (with
  confirmation) to take them off the roster. The "Active Drivers" stat
  updates immediately.
- **Alerts page** — "Resolve" removes an alert and updates the "Critical
  Alerts" count on the dashboard.
- **Relief Camps page** — a slider per camp for quick bulk editing, plus an
  operational checkbox.
- **Settings page** — a full admin console:
  - **Admin profile** — edit the signed-in admin's name, email and phone.
  - **Users & Roles** — a role-based access control matrix (Admin / Field
    Rescue Team / Volunteer / Citizen × permissions) with checkboxes;
    a Staff Verification queue to approve/reject new accounts; and a live
    Activity Audit Log that records every meaningful action taken anywhere
    in the app (camp edits, dispatches, driver/donor changes, settings
    changes, etc.) for post-incident review.
  - **Alerts & Communication** — toggle broadcast channels (push, SMS,
    email, emergency voice), configure a Common Alerting Protocol (CAP)
    template (sender ID, category, urgency, severity, certainty), and set
    the default geo-fencing radius for hazard warnings.
  - **Integrations & Data** — manage API keys/connection status for a
    weather feed (NOAA), mapping tools, and seismic data (USGS); configure
    default resource-inventory categories (add/remove); and set offline
    sync bandwidth limits and sync interval for low-connectivity field use.
  - **Security & System** — enforce MFA/OTP for elevated accounts, set data
    retention period and data residency, and toggle system-wide maintenance
    mode with a publishable safety notice.

## Project structure

```
src/resqbridge/
  Theme.java              Color palette and fonts
  UiUtils.java             RoundedPanel + hand-drawn AppIcon pictograms
  DataModels.java          All data models (Camp, Dispatch, Donor, Driver,
                           AlertItem, AdminProfile, PendingAccount,
                           AuditLogEntry, CommunicationSettings,
                           IntegrationSettings, SecuritySettings,
                           LoginAccount, ChatMessage, HazardReport, SafeZone,
                           EquipmentItem, ManifestItem, DeliveryRun) + DataStore
  LoginFrame.java          Sign-in screen (main entry point)
  HeaderPanel.java         Top bar (logo, live indicator, signed-in user, logout)
  SidebarPanel.java        Left navigation (role-specific item list)
  StatCardPanel.java       Reusable stat card (top-right column)
  MapPanel.java            Kerala relief network map + routes + markers
  EditCampDialog.java      Edit a camp's supply level
  DispatchCardPanel.java   Recent Dispatches card + Add Dispatch dialog
  InventoryCardPanel.java  Supply Inventory donut chart + editable legend
  DonorsCardPanel.java     Top Donors card + Add/Delete Donor
  DashboardPanel.java      Assembles the admin dashboard layout
  CampsPanel.java          Full Relief Camps management page (Admin)
  DriversPanel.java        Drivers roster page: add/remove/en-route/reached (Admin)
  AlertsPanel.java         Alerts page (Admin)
  ChatPanel.java           Reusable driver/camp <-> dispatch chat widget
  AdminMessagesPanel.java  Admin's "Messages" page (all chat channels)
  DriverConsolePanel.java  Full Driver role console (see feature list above)
  CampConsolePanel.java    Full Camp Employee role console (see feature list above)
  SimplePagePanel.java     Placeholder page (Reports)
  SettingsPanel.java       Full admin settings console (profile, users &
                           roles, alerts & comms, integrations & data,
                           security & system)
  ResQBridgeApp.java       Main app window: header + role-specific sidebar/pages
```

## Notes

- This was written and reviewed by hand in a sandbox without a JDK available
  to compile-test it (no `javac` on the box, and network access to install
  one is blocked), so please run the build step above and let me know if you
  hit any compiler error — happy to fix it immediately.
- All icons are drawn in code (`AppIcon`), so there are no external image
  assets or network calls; the whole app is self-contained.
- Passwords are stored and compared in plain text in memory for this demo —
  fine for a local desktop prototype, but not how you'd want to do
  authentication in anything that leaves your machine.

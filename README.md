# 2002-Assignment

## Class Descriptions

### `java.lang.Object`
The base class from which all Java classes are derived.

---

### `src.Appointment`
Handles appointment-related functionality, including scheduling, patient-doctor mappings, and appointment status.

---

### `src.HospitalApp`
The main entry point of the hospital management system. Provides menus and operations for various roles, including patients, staff, and administrators.

---

### `src.MedicineInv`
Manages the inventory of medicines, including stock levels and low-stock alerts.

---

### `src.PatientInv`
Maintains a list of patients and their associated records.

---

### `src.PrescriptedMed`
Handles information related to medications prescribed to patients during appointments.

---

### `src.RestockForm`
Manages requests for restocking low medicine inventory items.

---

### `src.Schedule`
Manages staff schedules, including doctor availability and time slots.

---

### `src.StaffInv`
Handles the list and management of hospital staff, including doctors, pharmacists, and administrators.

---

### `src.User`
The base class for all users of the system. Provides shared functionality for all user types, including login and password management.

#### Subclasses:
- **`src.Patient`**: Represents a patient, managing personal details and appointment records.
- **`src.Staff`**: Represents hospital staff with additional management responsibilities.

---

### `src.Staff` (Subclass of `src.User`)
A specialized user type representing hospital staff.

#### Subclasses:
- **`src.Administrator`**: Provides administrative functionalities, including managing staff, inventory, and schedules.
- **`src.Pharmacist`**: Manages pharmacy-related tasks, including fulfilling medication orders and requesting restocks.

---

## Overview
This package is designed for a comprehensive hospital management system. It supports functionalities for patients, staff, and administrators, ensuring seamless operation through object-oriented design principles.

---

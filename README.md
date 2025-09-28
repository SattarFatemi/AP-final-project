# AP-Final-Project (Educational Management System)

An implementation of a university educational management system. The application supports dashboards and features for professors, students, and educational assistants. It handles tasks such as user profiles, grade lists, weekly planning, course registration, and course / student / class management.
Written in Java, this was the second semester project for the Advanced Programming course.

---

## Table of Contents

1. [Features](#features)
2. [Technologies & Architecture](#technologies--architecture)
3. [Usage](#usage)

   * User Roles & Capabilities
   * Workflow Examples
---

## Features

* **Multi-role support**: separate dashboards / views for students, professors, and assistants
* **Profile management**: users can view and (if allowed) update profile data
* **Course registration & schedule**: students can browse, register, drop courses; weekly plan display
* **Grade management**: grades list, grade entry by instructors, viewing grades
* **Class / Student management**: assistants & professors can manage class rosters, oversee enrolled students
* **Administrative operations**: processes like course offering, allocation, etc.
* **Persistent data**: backing store (e.g. files, database, as implemented) for user/course/grade records
* **GUI / UI elements**: (if present) windows/forms for interaction

---

## Technologies & Architecture

* Language: **Java**
* Build tool: **Gradle** (wrapper included)
* Project layout: follows standard `src/main/java` convention
* (Optional) UI framework: e.g., Swing or JavaFX (depending on your implementation)
* Data persistence: (depending on your code) file-based storage, serialization, or embedded database
* Modular components: separation of domain / model, controllers, and UI layers

---

## Usage

### User Roles & Capabilities

* **Student**

  * Register / drop courses
  * View weekly schedule
  * Check grades
  * View / edit profile

* **Professor**

  * View classes they’re teaching
  * Enter or update grades
  * Manage class rosters
  * View student info

* **Educational Assistant / Admin**

  * Oversee student/course assignments
  * Manage users, assign courses, approve registration
  * General system maintenance

### Workflow Examples

* **Student workflow**: login → browse offered courses → register → view schedule → check grades
* **Professor workflow**: login → see classes → select class → view students → assign grades
* **Assistant workflow**: login → manage courses / enrollments → assist professors / students

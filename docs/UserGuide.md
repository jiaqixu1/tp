---
layout: page
title: User Guide
---

TaskForge is a **desktop app for managing contacts, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, TaskForge can get your contact management tasks done faster than traditional GUI apps.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

1. Download the latest `taskforge.jar` file from [here](https://github.com/AY2526S2-CS2103T-W09-4/tp/releases).

1. Create a new folder in your computer that you want to use as the **taskforge folder**.
This **taskforge folder** will be used by TaskForge to store its data and configuration files.

1. Copy `taskforge.jar` file from your **Downloads folder** to the newly created **taskforge folder**.

1. Take note of the **path** to your newly created **taskforge folder**. You will need to use that path to change your directory before running the app in the next step. e.g. if you created the folder in your Desktop.
    <br><br>For Windows, the path should be:
    ```
    C:\User\YourName\Desktop\taskforge
    ```
    For Mac and Linux, the path should be:
    ```
    /User/YourName/Desktop/taskforge
    ```

1. Open a command terminal <br>
   **Windows users:** You can open Command Prompt by searching for `cmd` in the Start menu. <br>
   **Mac users:** You can open Terminal by searching for `Terminal` in Spotlight Search (Cmd + Space). <br>
   **Linux users:** You can open Terminal by searching for `Terminal` in your applications.

1. Type the following commands in your terminal and press enter to change the directory:
    ```
    cd <path_to_your_taskforge_folder>
    ```
    Then type the following command and press enter to run TaskForge on your computer:
    ```
    java -jar taskforge.jar
    ```
    <br>A GUI similar to the below should appear in a few seconds. Note that the app will contain some sample data.<br>
       ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g., typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all persons.

   * `add -n John Doe -p 98765432 -e johnd@example.com` : Adds a person named `John Doe` to TaskForge.

   * `delete 3` : Deletes the third person shown in the current list.

   * `clear` : Deletes all entries (persons, projects, and tasks) from TaskForge.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

This section is split into 4 subsections:
- [General](#general): Commands that are not specific to any particular feature.
- [Person](#person): Commands related to managing persons in TaskForge.
- [Project](#project): Commands related to managing projects in TaskForge.
- [Task](#task): Commands related to managing tasks in TaskForge.

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* All command and subcommand words are case-sensitive and must be entered in lowercase.<br>
  e.g.`task add` is valid while `Task Add` and `task Add` are not.

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add -n NAME`, `NAME` is a parameter which can be used as `add -n John Doe`.

* Items in square brackets are optional.<br>
  e.g `-n NAME [-d TASK]` can be used as `-n John Doe -d task1` or as `-n John Doe`.

* Items with `…`​ after square brackets can be used multiple times including zero times.<br>
  e.g. `[-d TASK]…​` can be used as ` ` (i.e. 0 times), `-d task1`, `-d task2 -d task3` etc.

* Items inside curly brackets must be provided at least once and may be repeated.<br>
  e.g. `{-i PROJECT_INDEX}` can be used as `-i 1` or `-i 1 -i 2` (it cannot be empty).

* Parameters with prefixes can be in any order.<br>
  e.g. if the command specifies `-n NAME -p PHONE_NUMBER`, `-p PHONE_NUMBER -n NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

--------------------------------------------------------------------------------------------------------------------

### General

There are 5 general commands that deals with clearing all entries, undoing the previous command, redoing the previous command, viewing help, and exiting the program.
- [clear](#clearing-all-entries--clear)
- [undo](#undoing-previous-command--undo)
- [redo](#redoing-previous-command--redo)
- [help](#viewing-help--help)
- [exit](#exiting-the-program--exit)

#### Clearing all entries : `clear`

Clears all entries (persons, projects, and tasks) from TaskForge.

Format: `clear`

#### Undoing previous command : `undo`

Reverts the last change made in TaskForge.

Format: `undo`

#### Redoing previous command : `redo`

Reapplies the last undone change, effectively canceling the undo.

Format: `redo`

#### Viewing help : `help`

Shows a message explaining all the commands and contains a button to open the UserGuide.

![help message](images/helpMessage.png)

Format: `help`

#### Exiting the program : `exit`

Exits the program.

Format: `exit`

[↑ Back to Features](#features)

--------------------------------------------------------------------------------------------------------------------

### Person
There are 5 basic person commands that deal with adding, deleting, editing, listing, and finding persons in TaskForge.
- [add](#adding-a-person-add)
- [delete](#deleting-a-person--delete)
- [edit](#editing-a-person--edit)
- [list](#listing-all-persons--list)
- [find](#locating-persons-by-multiple-fields-find)

#### Adding a person: `add`

Adds a person to TaskForge.

Format: `add -n NAME -p PHONE_NUMBER -e EMAIL [-d TASK]…​ [-l PROJECT]…​`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A person can have any number of tasks and projects (including 0)
A person must have valid unique phone number and email.
</div>

**Input constraints:**
* **Name**
    * Can contain only alphanumeric characters and spaces.
    * Cannot be blank.
    * Duplicate names are allowed.

* **Phone number**
    * Must contain numbers only.
    * Must be at least 3 digits long.
    * Must be unique across all persons.

* **Email**
    * Must be in the format `local-part@domain`.
    * The local-part may contain only alphanumeric characters and the special characters `+`, `_`, `.`, `-`.
    * The local-part must not start or end with a special character.
    * The domain consists of domain labels separated by periods.
    * The domain must end with a label that is at least 2 characters long.
    * Each domain label must start and end with an alphanumeric character.
    * Each domain label may contain hyphens, but only between alphanumeric characters.
    * Must be unique across all persons.

Examples:
* `add -n John Doe -p 98765432 -e johnd@example.com`
* `add -n Betsy Crowe -d newTask2 -e betsycrowe@example.com -p 1234567 -d newTask1`

#### Deleting a person : `delete`

Deletes the specified person from TaskForge.

Format: `delete PERSON_INDEX`

* Deletes the person at the specified `PERSON_INDEX`.
* `PERSON_INDEX` to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in TaskForge.
* `find -n Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

#### Editing a person : `edit`

Edits an existing person in TaskForge.

Format: `edit PERSON_INDEX [-n NAME] [-p PHONE] [-e EMAIL]`

* Edits the person at the specified `PERSON_INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
> [!NOTE]
> Project and task assignments are managed by the `project assign/unassign` and `task assign/unassign` commands.

Examples:
*  `edit 1 -p 91234567 -e johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 -n Betsy Crower` Edits the name of the 2nd person to be `Betsy Crower`.

#### Listing all persons : `list`

Shows a list of all persons in TaskForge.

Format: `list`

#### Locating persons by multiple fields: `find`

Finds persons whose fields (name, phone, email, tasks, projects) match the given keywords.
> [!IMPORTANT]
> This command will update to show the filtered list of persons that match the search criteria. To restore the original list of all persons, use the `list` command.

Format: `find [-n NAME_KEYWORDS] [-p PHONE_KEYWORDS] [-e EMAIL_KEYWORDS] [-d TASK_KEYWORDS] [-l PROJECT_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* For a specific field, persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `find -n Hans Bo` will return `Hans Gruber`, `Bo Yang`
* When multiple fields are specified, only persons matching ALL specified fields will be returned (i.e. `AND` search).
  e.g. `find -n Alice -p 91234567` will return only persons named Alice AND with the phone number 91234567.
* Only full words will be matched e.g. `Han` will not match `Hans`.
* If no one is found by the keywords searched, the message will display "0 persons. listed".

Examples:
* `find -n John` returns `john` and `John Doe`
* `find -n alex david` returns `Alex Yeoh`, `David Li`
* `find -n Alice -p 91234567` returns any person named `Alice` whose phone number is `91234567`.
* `find -d "Task 1" -l ProjectA` returns any person who has a task containing "Task 1" and belongs to project `ProjectA`.

[↑ Back to Features](#features)

--------------------------------------------------------------------------------------------------------------------

### Project

There are 4 basic project commands and 3 project management commands. Basic commands deal with adding, deleting, listing, and finding projects.
While project management commands deal with assigning/unassigning projects to/from persons and viewing project members.

Basic commands:
- [add](#adding-a-project--project-add)
- [delete](#deleting-a-project--project-delete)
- [list](#viewing-all-projects--project-list)
- [find](#finding-projects-by-name--project-find)

Project management commands:
- [assign](#assigning-a-project--project-assign)
- [unassign](#unassigning-a-project--project-unassign)
- [members](#viewing-project-members--project-members)

#### Adding a project : `project add`

Adds a new project to TaskForge into the project list.

Format: `project add PROJECT_TITLE`

* Adds a new project with the specified project title.
* `PROJECT_TITLE` must contain letters, numbers, and spaces only, and it should be between 1 to 64 characters.
* Duplicate project title are not allowed.
* Project titles are automatically normalized to title case, where for each word(separated by space) in the project name, the first letter is capitalized and the remaining letters are converted to lowercase.

Examples:
* `project add web app` adds a new project named `Web App`.
* `project add MobileApp` adds a new project named `Mobileapp`.

#### Deleting a project : `project delete`

Deletes a project by its index in the displayed project list.

Format: `project delete PROJECT_INDEX`

* Deletes the project at the specified `PROJECT_INDEX`.
* `PROJECT_INDEX` refers to the project index displayed in `project list.`
* `PROJECT_INDEX` **must be a positive integer** `1, 2, 3, ...`

Example:
* `project delete 2` deletes the 2nd project in the list.

#### Viewing all projects : `project list`

Displays all projects currently available in TaskForge.

Format: `project list`


#### Finding projects by name : `project find`

Finds projects whose names contain any of the given keywords.

Format: `project find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g. `alpha` will match `Alpha`
* The search is performed on project names only.
* Projects matching at least one keyword will be shown (i.e. `OR` search). e.g. `web app` will return `Web Mobile`, `App building`
* The result is shown as plain text in the result display and filters the project list.
* Partial words will be matched e.g. `bui` will match `build`

Examples:
* `project find alpha` returns `Alpha` and `Alpha station`
* `project find app build` returns `Mobile App` and `Grand Building`

#### Assigning a project : `project assign`

Assigns a project to a person

Format: `project assign PERSON_INDEX {-i PROJECT_INDEX}`

* Assigns project(s) from `project list` to a person.
* `PERSON_INDEX` refers to the person index displayed in `list`
* `PROJECT_INDEX` refers to the project index displayed in `project list`
* `PERSON_INDEX` and `PROJECT_INDEX` **must be a positive integer** `1, 2, 3, ...`
* The same project cannot be assigned twice to the same person (no duplicates)
* To assign multiple projects in one command, repeat the `-i` prefix.

Example:
* `project assign 1 -i 2` assigns the 2nd project in `project list` to the 1st person in the `list`
* `project assign 2 -i 1 -i 3` assigns multiple projects to the 2nd person in the `list`

#### Unassigning a project : `project unassign`

Unassigns a project from a person

Format: `project unassign PERSON_INDEX {-i PROJECT_INDEX_FROM_PERSON}`

* Unassigns project(s) from the person at the specified `INDEX`.
* `PROJECT_INDEX_FROM_PERSON` refers to the project index of the person's project list.
* `PERSON_INDEX` refers to the person index displayed in `list`
* `PERSON_INDEX` and `PROJECT_INDEX_FROM_PERSON` **must be positive integers** `1, 2, 3, ...`
* To unassign multiple projects in one command, repeat the `-i` prefix.

Examples:
* `project unassign 1 -i 2` unassigns the 2nd project from the 1st person in the `list`
* `project unassign 3 -i 1 -i 4` unassigns the 1st and 4th projects from the 3rd person in the `list`

#### Viewing project members : `project members`

Displays all persons assigned to a project.

Format: `project members PROJECT_INDEX`

* Shows all persons who are assigned to the specified project.
* `PROJECT_INDEX` refers to the project index displayed in `project list`.
* `PROJECT_INDEX` **must be a positive integer** `1, 2, 3, ...`
* The result lists all members associated with the project.
* If no persons are assigned to the project, an empty result or message is shown.

[↑ Back to Features](#features)

--------------------------------------------------------------------------------------------------------------------

### Task
There are 4 basic task commands and 6 task management commands. Basic commands deal with adding, deleting, editing, and finding tasks. 
While task management commands deal with assigning/unassigning tasks to/from persons, marking/unmarking tasks as done, and listing tasks by project/person.

Basic commands:
- [add](#adding-a-task-to-a-project--task-add)
- [delete](#deleting-a-task-from-a-project--task-delete)
- [edit](#editing-a-task-assigned-to-a-person--task-edit)
- [find](#finding-tasks-by-keyword--task-find)

Task management commands:
- [assign](#assigning-a-task--task-assign)
- [unassign](#unassigning-a-task--task-unassign)
- [mark](#marking-a-task-as-done--task-mark)
- [unmark](#unmarking-a-task-as-done--task-unmark)
- [list](#listing-all-tasks-in-a-project--task-list)

#### Adding a task to a project : `task add`

Adds task(s) to a project in the project list.

Format: `task add PROJECT_INDEX {-n TASK_NAME}`

* Adds new task(s) with the specified `TASK_NAME` to the project at the specified `PROJECT_INDEX`.
* `PROJECT_INDEX` refers to the project index displayed in `project list`.
* `PROJECT_INDEX` **must be a positive integer** `1, 2, 3, ...`
* `TASK_NAME` must be alphanumeric (only letters, numbers, and spaces), between 1 to 64 characters.
* Duplicate tasks within the same project are not allowed.
* Task names are case-sensitive. For example, `TASK` and `task` are treated as different task names.
* To add multiple tasks to the same project in one command, repeat the `-n` prefix.

Examples:
* `task add 1 -n Write documentation` adds a new task named `Write documentation` to the 1st project.
* `task add 2 -n Design UI -n Implement backend` adds multiple new tasks to the 2nd project.

#### Deleting a task from a project : `task delete`

Deletes a task from a project in the project list.

Format: `task delete PROJECT_INDEX {-i TASK_INDEX_FROM_PROJECT}`

* Deletes task(s) from the project at the specified `PROJECT_INDEX`.
* `TASK_INDEX_FROM_PROJECT` refers to the task ID shown in the specified project.
* `PROJECT_INDEX` and `TASK_INDEX_FROM_PROJECT` **must be positive integers** `1, 2, 3, ...`
* To delete multiple tasks from the same project in one command, repeat the `-i` prefix.

Examples:
* `task delete 1 -i 2` deletes the 2nd task from the 1st project
* `task delete 2 -i 1 -i 3` deletes the 1st and 3rd tasks from the 2nd project

#### Editing a task assigned to a person : `task edit`

Edits the name of a task assigned to a person.
> [!IMPORTANT]
> This will also update the task name in the project list and for all other persons who are assigned that task.

Format: `task edit PERSON_INDEX -i TASK_INDEX_FROM_PERSON -n NEW_TASK_NAME`

* Edits the task at `TASK_INDEX_FROM_PERSON` from person with index `PERSON_INDEX`.
* `TASK_INDEX_FROM_PERSON` refers to the task ID shown for the specified person.
* `TASK_INDEX_FROM_PERSON` **must be a positive integer** `1, 2, 3, ...`
* `NEW_TASK_NAME` must follow the same naming constraints as `TASK_NAME` in `task add`.

Example:
* `task edit 1 -i 2 -n Prepare sprint report` renames the 2nd task from 1st person to `Prepare sprint report`

#### Finding tasks by keyword : `task find`

Finds tasks whose names contain any of the given keywords across all projects.

Format: `task find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g. `report` will match `Write report`.
* The search checks all task lists from every project.
* A matching result is shown as `taskName - projectName` in the dialog box.
* Tasks matching at least one keyword will be shown (i.e. `OR` search).
* Project list will filter projects that contain the matching tasks.
* Partial words will be matched e.g. `bui` will match `build`

Examples:
* `task find report` returns `report` and `report bugs`
* `task find bug ui` returns `fix bug`, `fix ui`, and `guide clients`

#### Assigning a task : `task assign`

Assigns one or more tasks to a person.

Format: `task assign PERSON_INDEX {-pi PROJECT_INDEX -i TASK_INDEX_FROM_PROJECT}`

* Adds task(s) to the person at the specified `PERSON_INDEX`.
* `PERSON_INDEX` refers to the person index displayed in `list`.
* `PROJECT_INDEX` refers to the project index displayed in `project list`.
* `TASK_INDEX_FROM_PROJECT` refers to the task ID shown in the specified project.
* `PERSON_INDEX`, `PROJECT_INDEX`, and `TASK_INDEX_FROM_PROJECT` **must be a positive integer** `1, 2, 3, ...`
* To assign multiple tasks to a person in one command, repeat each -pi and -i pair with the corresponding indexes.

Examples:
* `task assign 1 -pi 1 -i 2` assigns the 2nd task in the 1st project to the 1st person.
* `task assign 2 -pi 1 -i 3 -pi 2 -i 1` assigns multiple tasks to the 2nd person.

#### Unassigning a task : `task unassign`

Unassigns one or more tasks from a person by task index.

Format: `task unassign PERSON_INDEX {-i TASK_INDEX_FROM_PERSON}`

* Deletes task(s) from the person at the specified `PERSON_INDEX`.
* `TASK_INDEX_FROM_PERSON` refers to the task ID shown for the specified person.
* `PERSON_INDEX` and `TASK_INDEX_FROM_PERSON` **must be positive integers** `1, 2, 3, ...`
* To unassign multiple tasks from a person in one command, repeat the `-i` prefix.

Examples:
* `task unassign 1 -i 2` unassigns the 2nd task from the 1st person
* `task unassign 3 -i 1 -i 4` unassigns multiple tasks from the 3rd person

#### Marking a task as done : `task mark`

Marks a task as done for an existing person.

Format: `task mark PERSON_INDEX TASK_INDEX_FROM_PERSON`

* Marks the task identified by `TASK_INDEX_FROM_PERSON` as done for the person identified by `PERSON_INDEX`.
* `PERSON_INDEX` refers to the person index displayed in `list`.
* `TASK_INDEX_FROM_PERSON` refers to the task ID shown for the specified person.
* `PERSON_INDEX` and `TASK_INDEX_FROM_PERSON` **must be positive integers** `1, 2, 3, ...`

Example:
* `task mark 1 1` marks the 1st task of the 1st person as done.

#### Unmarking a task as done : `task unmark`

Unmarks a task as done for an existing person.

Format: `task unmark PERSON_INDEX TASK_INDEX_FROM_PERSON`

* Unmarks the task identified by `TASK_INDEX_FROM_PERSON` as done for the person identified by `PERSON_INDEX`.
* `PERSON_INDEX` refers to the person index displayed in `list`.
* `TASK_INDEX_FROM_PERSON` refers to the task ID shown for the specified person.
* `PERSON_INDEX` and `TASK_INDEX_FROM_PERSON` **must be positive integers** `1, 2, 3, ...`

Example:
* `task unmark 1 1` marks the 1st task of the 1st person as not done.

#### Listing all tasks in a project : `task list`

Lists all tasks that belong to the specified project.

Format: `task list PROJECT_INDEX`

* Lists task(s) from the `PROJECT_INDEX`.
* `PROJECT_INDEX` refers to index in global project list.

Examples:
* `task list 1`

#### Viewing all tasks of a person : `task view`

Displays all tasks assigned to a person.

Format: `task view PERSON_INDEX`

* Shows all tasks assigned to the person at the specified `PERSON_INDEX`.
* `PERSON_INDEX` refers to the person index displayed in `list`.
* `PERSON_INDEX` **must be a positive integer** `1, 2, 3, ...`
* If the person has no tasks, a message will be shown.

Example:
* `task view 1`

[↑ Back to Features](#features)

--------------------------------------------------------------------------------------------------------------------

### Saving the data

TaskForge data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

TaskForge data are saved automatically as a JSON file `[JAR file location]/data/TaskForge.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, TaskForge will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause TaskForge to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous TaskForge home folder.

--------------------------------------------------------------------------------------------------------------------

## Command summary
This summary is split into 4 main sections:
- [Person commands](#person-commands)
- [Project commands](#project-commands)
- [Task commands](#task-commands)
- [General commands](#general-commands)

---

### Person Commands

Action | Format | Example
--------|--------|--------
**[Add](#adding-a-person-add)** | `add -n NAME -p PHONE_NUMBER -e EMAIL [-d TASK]… [-l PROJECT]…` | `add -n James Ho -p 22224444 -e jamesho@example.com -l ProjectX -d TaskY`
**[Delete](#deleting-a-person--delete)** | `delete PERSON_INDEX` | `delete 3`
**[Edit](#editing-a-person--edit)** | `edit PERSON_INDEX [-n NAME] [-p PHONE] [-e EMAIL]` | `edit 1 -n James Ho -p 22224444 -e jamesho@example.com`
**[List](#listing-all-persons--list)** | `list` |
**[Find](#locating-persons-by-multiple-fields-find)** | `find KEYWORD [MORE_KEYWORDS]` | `find James Jake`

---

### Project Commands

#### Basic

Action | Format | Example
--------|--------|--------
**[Add](#adding-a-project--project-add)** | `project add PROJECT_TITLE` | `project add WebApp`
**[Delete](#deleting-a-project--project-delete)** | `project delete PROJECT_INDEX` | `project delete 1`
**[List](#viewing-all-projects--project-list)** | `project list` |
**[Find](#finding-projects-by-name--project-find)** | `project find KEYWORD [MORE_KEYWORDS]` | `project find Alpha Web`

#### Management

Action | Format | Example
--------|--------|--------
**[Assign](#assigning-a-project--project-assign)** | `project assign PERSON_INDEX {-i PROJECT_INDEX}` | `project assign 1 -i 2`
**[Unassign](#unassigning-a-project--project-unassign)** | `project unassign PERSON_INDEX {-i PROJECT_INDEX_FROM_PERSON}` | `project unassign 2 -i 1`
**[List members](#viewing-project-members--project-members)** | `project members PROJECT_INDEX` | `project members 1`

---

### Task Commands

#### Basic

Action | Format | Example
--------|--------|--------
**[Add](#adding-a-task-to-a-project--task-add)** | `task add PROJECT_INDEX {-n TASK_NAME}` | `task add 1 -n Write report`
**[Delete](#deleting-a-task-from-a-project--task-delete)** | `task delete PROJECT_INDEX {-i TASK_INDEX_FROM_PROJECT}` | `task delete 1 -i 2`
**[Edit](#editing-a-task-assigned-to-a-person--task-edit)** | `task edit PERSON_INDEX -i TASK_INDEX_FROM_PERSON -n NEW_TASK_NAME` | `task edit 1 -i 1 -n Prepare sprint report`
**[Find](#finding-tasks-by-keyword--task-find)** | `task find KEYWORD [MORE_KEYWORDS]` | `task find report bug`

#### Management

Action | Format | Example
--------|--------|--------
**[Assign](#assigning-a-task--task-assign)** | `task assign PERSON_INDEX {-pi PROJECT_INDEX -i TASK_INDEX_FROM_PROJECT}` | `task assign 1 -pi 1 -i 2`
**[Unassign](#unassigning-a-task--task-unassign)** | `task unassign PERSON_INDEX {-i TASK_INDEX_FROM_PERSON}` | `task unassign 2 -i 1`
**[Mark](#marking-a-task-as-done--task-mark)** | `task mark PERSON_INDEX TASK_INDEX_FROM_PERSON` | `task mark 1 1`
**[Unmark](#unmarking-a-task-as-done--task-unmark)** | `task unmark PERSON_INDEX TASK_INDEX_FROM_PERSON` | `task unmark 1 1`
**[List by project](#listing-all-tasks-in-a-project--task-list)** | `task list PROJECT_INDEX` | `task list 1`

---

### General Commands

Action | Format
--------|--------
**[Clear](#clearing-all-entries--clear)** | `clear`
**[Undo](#undoing-previous-command--undo)** | `undo`
**[Redo](#redoing-previous-command--redo)** | `redo`
**[Help](#viewing-help--help)** | `help`
**[Exit](#exiting-the-program--exit)** | `exit`

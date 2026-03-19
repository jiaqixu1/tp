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

1. Download the latest `.jar` file from [here](https://github.com/AY2526S2-CS2103T-W09-4/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your TaskForge.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar taskforge.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe` to TaskForge.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a person: `add`

Adds a person to TaskForge.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A person can have any number of tags (including 0)
</div>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal`

### Listing all persons : `list`

Shows a list of all persons in TaskForge.

Format: `list`

### Editing a person : `edit`

Edits an existing person in TaskForge.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
    specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Deleting a person : `delete`

Deletes the specified person from TaskForge.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in TaskForge.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

### Managing projects

#### Adding a project : `project add`

Adds a new project to TaskForge into the project list.

Format: `project add PROJECT_NAME`

* Adds a new project with the specified project name.
* Project name must be alphanumeric (only letters and numbers), between 1 to 64 characters.
* Duplicate projects are not allowed.

Examples:
* `project add WebApp` adds a new project named `WebApp`.
* `project add MobileApp` adds a new project named `MobileApp`.

#### Viewing all projects : `project list`

Displays all projects currently available in TaskForge.

Format: `project list`

Example:
* `project list` returns a list of all existing project

#### Deleting a project : `project delete`

Deletes a project by its index in the displayed project list.

Format: `project delete INDEX`

* Deletes the project at the specified `INDEX`.
* The index refers to the index number shown by `project list`.
* The index **must be a positive integer** `1, 2, 3, ...`

Example:
* `project list` followed by `project delete 2` deletes the 2nd project in the list.

#### Assigning a project : `project assign`

Assigns a project to a person

Format: `project assign PERSON_INDEX -nPROJECT_NAME`

* Assigns project(s) from `project list` to a person.
* Project should exist in `project list` before being assigned to a person.
* `PERSON_INDEX` refers to the person index that's displayed on `list`
* `PERSON_INDEX` **must be a positive integer** `1, 2, 3, ...`
* The same project cannot be assigned twice to the same person (no duplicates)
* To assign multiple projects in one command, repeat the `-n` prefix.

Example:
* `project assign 1 -nWebApp` checks whether the project named WebApp exists, then assigns the project to the 1st person in the `list`
* `project assign 2 -nWebApp -nMobileApp` assigns multiple projects to the 2nd person in the `list`

#### Unassigning a project : `project unassign`

Unassigns a project from a person

Format: `project unassign PERSON_INDEX -i PROJECT_INDEX`

* Unassigns project(s) from the person at the specified `INDEX`.
* `PROJECT_INDEX` refers to the project numbering shown for that person in the app.
* `PROJECT_INDEX` does NOT refer to the project index that's displayed on `project list`.
* `PERSON_INDEX` refers to the person index that's displayed on `list`
* Both person `PERSON_INDEX` and `PROJECT_INDEX` **must be positive integers** `1, 2, 3, ...`
* To unassign multiple projects in one command, repeat the `-i` prefix.

Examples:
* `project delete 1 -i 2` deletes 2nd project from the 1st person in the `list`
* `project delete 3 -i 1 -i 4` deletes 1st and 4th project from the 1st person in the `list`

### Managing tasks

#### Adding a task : `task add`

Adds one or more tasks to a person.

Format: `task add INDEX -n TASK_NAME`

* Adds task(s) to the person at the specified `INDEX`.
* The person index refers to the index number shown in the displayed person list.
* The person index **must be a positive integer** `1, 2, 3, ...`
* To add multiple tasks in one command, repeat the `-n` prefix.

Examples:
* `task add 1 -n Write report`
* `task add 2 -n Prepare slides -n Rehearse demo`

#### Deleting a task : `task delete`

Deletes one or more tasks from a person by task index.

Format: `task delete INDEX -i TASK_INDEX`

* Deletes task(s) from the person at the specified `INDEX`.
* `TASK_INDEX` refers to the task numbering shown for that person in the app.
* Both person `INDEX` and `TASK_INDEX` **must be positive integers** `1, 2, 3, ...`
* To delete multiple tasks in one command, repeat the `-i` prefix.

Examples:
* `task delete 1 -i 2`
* `task delete 3 -i 1 -i 4`

#### Viewing all tasks of a person : `task view`

Displays all tasks assigned to a person.

Format: `task view INDEX`

* Shows all tasks assigned to the person at the specified `INDEX`.
* The person index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** `1, 2, 3, ...`
* If the person has no tasks, a message will be shown.

Examples:
* `task view 1`
* `task view 2`

### Clearing all entries : `clear`

Clears all entries from TaskForge.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

TaskForge data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

TaskForge data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

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

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Add** | `add -n NAME -p PHONE_NUMBER -e EMAIL [-l PROJECT_NAME] [-d TASK_NAME]…​` <br> e.g., `add -n James Ho -p 22224444 -e jamesho@example.com`
**Add Project** | `project add PROJECT_NAME`<br> e.g., `project add WebApp`
**Add Task** | `task add INDEX -n TASK_NAME`<br> e.g., `task add 1 -n Draft proposal`
**Assign Project** | `project assign INDEX -n PROJECT_NAME`<br> e.g., `project assign 1 -n WebApp`
**Clear** | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Delete Project** | `project delete INDEX`<br> e.g., `project delete 1`
**Delete Task** | `task delete INDEX -i TASK_INDEX`<br> e.g., `task delete 2 -i 1`
**View Tasks** | `task view INDEX`
**Edit** | edit [-n NAME] [-p PHONE_NUMBER] [-e EMAIL] [-l PROJECT_NAME] [-d TASK_NAME]…​` <br> e.g., `edit -n James Ho -p 22224444 -e jamesho@example.com`
**Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List** | `list`
**Unassign Project** | `project unassign INDEX -i LOCAL_PROJECT_INDEX`<br> e.g., `project delete 2 -i 1`
**View Projects** | `project list`
**Help** | `help`

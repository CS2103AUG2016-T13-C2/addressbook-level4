# Test Script

* [Quick Start](#quick-start)
* [Description](#description)
* [Testing Commands](#Testing Commands)
* [Command Summary](#command-summary)

## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
1. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
2. To open the SampleData, type **`open`** and press <kbd>Enter</kbd>. Then choose the saved location of SampleData, which should end with *src\test\data\ManualTesting\SampleData.xml*
3. Some example commands you can try:
   * **`list`** : lists all activities
   * **`add`**` CS2103 T7A1 d/6 Oct 2016 p/2 r/5 Oct 2016 1800 t/teamC2` : 
     adds an activity named `CS2103 T7A1` to the Lifekeeper.
   * **`delete`**` 3` : deletes the 3rd activity shown in the current list
   * **`exit`** : exits the app
4. Refer to the [Description](#description) section below for details of the LifeKeeper.<br>


## Description

0. Each card in the bottom left panel is a task added to the LifeKeeper.
1. The LifeKeeper supports three types of entries:
* `Activity`
* `Task`
* `Event`
2. Activity is a floating task which must have a valid `NAME` and optional `[REMINDER]`.<br>
3. Task is a task which must have a valid `NAME` and either `[DUEDATE]` or [PRIORITY]`. `[REMINDER]` can also be entered together with the rest.<br>
4. Event is a task which must have a valid `NAME` and `[STARTTIME]` and optional `[ENDTIME]`. `[REMINDER]` can also be entered together with the rest.<br>
5. All task types can have optional `[TAG]`, each task accepts multiple tags. `Tags` are shown with `[]`. <br>
6. Colours of the background of the cards indicate the status of the task. 
* `Green: Task or Activity that has been marked as done, or an Event that has passed. A text 'Completed' or 'Event Over' is shown to notify user of the status of the task/event`
* `Yellow: Task with a deadline within three days. A text `Task Deadline Approaching` is also shown to notify user of the approaching deadline.`
* `Red: Task with a duedate that has passed and yet not marked as done. A text `Task Overdue` is also shown to reminder user of the overdue task.`
* `Blue`: Events that are ongoing. This is defined as an event with a start time before the current time, and an end time after the current time. A text `Event Ongoing` is shown to notify the user of the event status.
* `White: The default colour indicating all other types of tasks.`
7. <img src="src/main/resources/images/priority3.png" width="15"> shown on the right side of the card indicates the important or priority of the task. There are four levels of priority 0, 1, 2, 3 which are indicated by the number of exclamation marks shown. Priority is only applicable to task but not activity or event. 

## Loading saved data
* Method 1
1. Go to File > Open.
2. Navigate to the folder src > test > data > ManualTesting.
3. Choose the file SampleData.xml and click 'Open'.

* Method 2
1. Type open src/test/data/ManualTesting/SampleData.xml to load the sample data.

## Testing Commands

#### Testing Command: `help`

> Help window pops up
 
#### Testing Command: `add activity`

> A new activity is added to the top of the list with name `Activity` and `Reminder: -`.

#### Testing Command: `add buy food r/12-12-2016 1800 t/Family`

> A new activity is added to the top of the list with name `buy food`, tag `[Family]` and reminder `Reminder: Mon, Dec 12, 2016 6:00PM`.
> For all tasks with reminder, the reminder will automatically pop up at the scheduled time when the app is open.

#### Testing Command: `add do IE 3101 Tutorial d/today p/3 t/IE3101`

> A new task is added to the top of the list with name `do IE 3101 Tutorial`, duedate ` Due on (Today's Date) 11:59PM`, tag `[IE3101]` and <img src="src/main/resources/images/priority3.png" width="15">
> The colour of the background of the card will also turn yellow with the text `Task Deadline Approaching` shown on the right of the card.

#### Testing Command: `add IE3100 Assignment 4 d/wed 1800`

> A new task is added to the top of the list with name `IE3100 Assignment 4`, duedate ` Due on (upcoming Wednesday's date) 6:00P` and `Reminder: -`

#### Testing Command: `add IE3100 Assignment 4 p/2`

> A new task is added to the top of the list with name `IE3100 Assignment 4`, `Reminder: -` and  <img src="src/main/resources/images/priority3.png" width="15">

#### Testing Command: `add CS2103 Tutorial s/thu 1300`

> A new event is added to the top of the list with name `CS2103 Tutorial` and `From (upcoming Thursday's date) 1:00PM to 2:00PM`.

#### Testing Command: `add IE2140 Lab s/11-11-2016 1300 e/11-11-2016 1500 r/10-11-2016 2200`

> A new event is added to the top of the list with name `IE2140 Lab`, `From Fri, Nov 11, 2016 1:00PM to 3:00PM` and `Remind: Thu, Nov 10, 2016 10:00PM`.

#### Testing Command: `add go to Grandma House r/every sat 1300`

> A new activity is added to the top of the list with name `go to Grandma House` and `Reminder: Every Saturday, 1:00PM`.

#### Testing Command: `add CS2103 Lecture s/every fri 1400 e/1600`

> A new recurring Event is added to the top of the list with name `CS2103 Lecture` and `Every Friday 2:00PM to 4:00PM`.

#### Testing Command: 








































Adds an activity to Lifekeeper<br>
Format: `add ACTIVITY_NAME {[d/DUEDATE] [p/PRIORITY_LEVEL]}{[s/START_TIME] [e/END_TIME]} [r/REMINDER] [t/TAG]...` 

> Activities can have only either `[d/DUEDATE] [p/PRIORITY_LEVEL]` or `[s/START_TIME] [e/END_TIME]` 
> Activities can have any number of tags (including 0)

`DUEDATE` accepts `Date Time` format input and variable inputs namely:
* `tomorrow [TIME]`
* `today [TIME]`

`PRIORITY_LEVEL` has to be an integer equal or larger than 1, with `1` being the top priority.

`START_TIME` accepts `Date Time` format input and variable inputs namely:
* `tomorrow [TIME]`
* `today [TIME]`

`END_TIME` accepts `Date Time` format input and variable inputs namely:
* `tomorrow [TIME]`
* `today [TIME]`


`REMINDER` accepts `Date Time` format input and variable inputs namely:
* `tomorrow`
* `today`
* `[TIME] before` sets reminder at the specified time before the `DUEDATE`. e.g. `0015 before` for a reminder 15 minutes before the `DUEDATE`.

Examples: 
* `add Grocery Shopping`
* `add Assignment 1 d/Tomorrow p/1 r/Today 2000`
* `add Project Report d/Tomorrow 1700 t/school`
* `add CS2103 T7A1 d/6 Oct 2016 p/2 r/5 Oct 2016 1800 t/teamC2`
* `add Lunch s/1200 e/1300`
* `add Executive Meeting s/tomorrow 0900 e/tomorrow 1200`
* `add Concert s/tomorrow 1800 e/tomorrow 2000 t/Leisure`

<!-- @@author A0131813R -->
#### Listing activities : `list`
Shows a list of activites in Lifekeeper, if any.<br>
Format: `list [TYPE]`


> If TYPE is not given, all the activites in Lifekeeper will be listed
> If TYPE is given only activities of that type will be listed

`TYPE` accepts the following `activities`, `events` or `tasks`

Examples:
* `list event`

> shows all events in the list.


#### Find by tag: `findtag `
Shows a list of all entries with the tags in LifeKeeper.<br>
Format: `findtag KEYWORD`

> All the entries with tags in Lifekeeper will be listed

<!-- @@author A0125097A -->

#### Finding activities by name or tag(s): `find`
Finding all activities containing the queried keyword in their name
* Finds activities whose names contain any of the given keywords.<br>
* Format: `find KEYWORD [MORE_KEYWORDS]`

> * The search is not case sensitive. e.g `study` will match `Study`
> * The order of the keywords matters. e.g. `Assignment Due` will not match `Due Assignment`
> * Words containing the keywords will be matched e.g. `Exam` will match `Exams`
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `Shopping` will match `Clothes Shopping`

Examples: 
* `find activities Homework Assignment`<br>
  Returns Any activities with words containing `Homework`, `homework`, `Assignment`, or `assignment` in their names.

<!-- @@author A0125680H -->
Finding all activities containing a certain tag    
* Finds activities which has tags of given keywords attached to it.<br>    
* Format: `find tags KEYWORD [MORE_KEYWORDS]`   
    
> * The search is not case sensitive.     
> * Only full words will be matched.    
> * Only tags matching the EXACT keyword will be returned.    
    
Examples:   
* `find CS2103`    
  Returns Any activities containing the tag `CS2103` or `cs2103` but not `CS2103T` or `CS2103 Project`.    

#### Deleting an activity: `delete`
Deletes the selected activity from Lifekeeper. Irreversible.<br>
Format: `delete INDEX`

> Deletes the activity with `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  

Examples: 
* `list`<br>
  `delete 1`<br>
  Deletes the 1st activity in the Lifekeeper activity list.
* `find Dinner`<br>
  `delete 2`<br>
  Deletes the 2nd activity listed in the results of the `find` command.

#### Marking an activity as done: `done`
Marks the activity as completed.<br>
Format: `done INDEX`

> Marks the activity with `INDEX` as completed.
  The index refers to the index number shown in the most recent listing.<br>
  

Examples: 
* `list`<br>
  `done 1`<br>
  Marks the 1st activity in the Lifekeeper activity list as completed.
* `find Admin`<br>
  `done 2`<br>
  Selects the 2nd activity in the results of the `find` command and then marks it as completed.

<!-- @@author A0125680H -->
#### Editing an activity: `edit`
Edits the selected activity from Lifekeeper.<br>
Format: `edit INDEX [n/ACTIVITY_NAME] {[d/DUEDATE] [p/PRIORITY_LEVEL]}{[s/START_TIME] [e/END_TIME]} [r/REMINDER] [t/TAG]...` 

> Edits the activity that was previously selected with `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  

Examples: 
* `list`<br>
  `edit 2 n/assignment 5 r/today`<br>
  Edit the selected the 2nd activity in the list by changing its name to `assignment 5` and reminder to `today`.
* `find CS2103 Assignment`<br>
  `edit 1 d/15-06-2017`<br>
  Selects the 1st activity in the results of the `find` command and then change the DUEDATE to `15/06/2017`.

#### Undoing an action : `undo`
Reverts the action that was previously executed.<br>
Format: `undo`

#### Clearing all entries : `clear`
Clears all entries from Lifekeeper.<br>
Format: `clear`  

#### Exiting the program : `exit`
Exits the program.<br>
Format: `exit`  

#### Saving the data 
Lifekeeper data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with 
       the file that contains the data of your previous Lifekeeper folder.
       
## Command Summary

Command | Format  
-------- | :-------- 
Add | `add ACTIVITY_NAME {[d/DUEDATE] [p/PRIORITY_LEVEL]}  {[s/START_TIME] [e/END_TIME]} [r/REMINDER] [t/TAG]...` 
Edit | `edit INDEX [n/ACTIVITY_NAME] {[d/DUEDATE] [p/PRIORITY_LEVEL]} {[s/START_TIME] [e/END_TIME]} [r/REMINDER] [t/TAG]...` 
Clear | `clear`
Delete | `delete INDEX`
Find | `find KEYWORD [MORE_KEYWORDS]`
List | `list`
Find Tags | `findtag`
Done | `done INDEX`
Undo | `undo`
Help | `help`
Exit | `exit`
# Pre-work - *ToDoApp*

ToDoApp is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: Nilay Upadhyay

Time spent: 8-9 hours spent in total

## User Stories

The following **required** functionality is completed:

- [X] User can **successfully add and remove items** from the todo list
- [X] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list.
- [X] User can **persist todo items** and retrieve them properly on app restart

The following **optional** features are implemented:

- [X] Persist the todo items into SQLite instead of a text file
- [X] Improve style of the todo items in the list 
- [X] Add support for completion due dates for todo items 
- [X] Use a DialogFragment instead of new Activity for editing items
- [X] Add support for selecting the priority of each todo item (and display in listview item)
- [X] Tweak the style improving the UI / UX, play with colors, images or backgrounds

The following **additional** features are implemented:

[*] Use a ActionMode to delete the list items with support of multiple selection

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='http://i.imgur.com/41zLKQH.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Project Analysis

As part of your pre-work submission, please reflect on the app and answer the following questions below:

**Question 1:** "What are your reactions to the Android app development platform so far? Compare and contrast Android's approach to layouts and user interfaces in past platforms you've used."

**Answer:** I have used Unity game engine before for game development and its good for a 3d rendering. Android native Ui is ideal for complex user interfaces

**Question 2:** "Take a moment to reflect on the `ArrayAdapter` used in your pre-work. How would you describe an adapter in this context and what is its function in Android? Why do you think the adapter is important? Explain the purpose of the `convertView` in the `getView` method of the `ArrayAdapter`."

**Answer:** The adapter is a object which makes a view for a particular position in listitem and returns the view. The LayoutInflater takes layout XML-files and creates different View-objects from its contents. Convertview is used for a resuing the views which can not be seen on the screen.when a View is scrolled so that is no longer visible, it can be used for one of the new Views appearing.

## License

    Copyright [yyyy] [name of copyright owner]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
